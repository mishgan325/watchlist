package ru.mishgan325.watchlist.controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.mishgan325.watchlist.entities.Title;
import ru.mishgan325.watchlist.utils.JsonHandler;
import ru.mishgan325.watchlist.utils.MovieScraper;
import ru.mishgan325.watchlist.views.SearchView;
import ru.mishgan325.watchlist.views.WatchlistView;

import java.io.IOException;
import java.util.List;

public class SearchController {

    private SearchView view;
    private Stage primaryStage;

    public SearchController(SearchView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
    }

    public void searchMovies() {
        String movieName = view.getSearchField().getText();
        if (movieName.isEmpty()) {
            showAlert("Error", "Please enter a movie name");
            return;
        }

        try {
            List<Title> titles = MovieScraper.searchMovie(movieName);
            view.getListView().getItems().clear();
            view.getListView().getItems().addAll(titles);
        } catch (IOException e) {
            showAlert("Error", "Failed to search movies");
            e.printStackTrace();
        }
    }

    public void showDetails(Title title) {
        if (title == null) {
            return;
        }

        try {
            Title detailedTitle = MovieScraper.getMovieDetails(title.getTitleUrl());
            view.getDetailsArea().setText("Title: " + detailedTitle.getTitle() +
                    "\nDescription: " + detailedTitle.getDescription() +
                    "\nGenres: " + detailedTitle.getGenres());
        } catch (IOException e) {
            showAlert("Error", "Failed to get movie details");
            e.printStackTrace();
        }
    }

    public void addToWatchlist() {
        Title title = view.getListView().getSelectionModel().getSelectedItem();
        if (title == null) {
            showAlert("Error", "Please select a movie to add to watchlist");
            return;
        }

        List<Title> watchlist = JsonHandler.loadWatchlist();
        watchlist.add(title);
        JsonHandler.saveWatchlist(watchlist);
        showAlert("Success", "Movie added to watchlist");
    }

    public void openWatchlist() {
        WatchlistView watchlistView = new WatchlistView();
        watchlistView.getStage().show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
