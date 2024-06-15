package ru.mishgan325.watchlist.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.mishgan325.watchlist.entities.Title;
import ru.mishgan325.watchlist.utils.JsonHandler;
import ru.mishgan325.watchlist.utils.ImdbScraper;
import ru.mishgan325.watchlist.utils.TitleShuffler;
import ru.mishgan325.watchlist.views.RandomTitleDialog;
import ru.mishgan325.watchlist.views.SearchView;
import ru.mishgan325.watchlist.views.WatchlistView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
            List<Title> titles = ImdbScraper.searchMovie(movieName);
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

        TextArea details = view.getDetailsArea();
        details.setText("Title: " + title.getTitle() +
                "\nDescription: " + title.getDescription() +
                "\nGenres: " + title.getGenres());
        details.setWrapText(true);
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

    public void showRandomTitleDialog() {
        List<Title> watchlist = JsonHandler.loadWatchlist();
        if (watchlist.isEmpty()) {
            showAlert("Error", "No titles available");
            return;
        }

        Title randomTitle = TitleShuffler.getRandomTitle(watchlist);
        RandomTitleDialog dialog = new RandomTitleDialog(randomTitle);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(dialog.getWatchButtonType())) {
                System.out.println("Будешь смотреть");
            } else if (buttonType.equals(dialog.getPostponeButtonType())) {
                System.out.println("Не будешь смотреть");
            }
        });
    }
}
