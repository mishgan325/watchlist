package ru.mishgan325.watchlist;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private ListView<Title> listView;
    private TextField searchField;
    private TextArea detailsArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie Searcher");

        searchField = new TextField();
        searchField.setPromptText("Enter movie name");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchMovies());

        listView = new ListView<>();
        listView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();

            @Override
            protected void updateItem(Title item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(item.getPreviewUrl(), 50, 75, true, true));
                    label.setText(item.getTitle());
                    HBox hBox = new HBox(imageView, label);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDetails(newValue));

        detailsArea = new TextArea();
        detailsArea.setEditable(false);

        Button addToWatchlistButton = new Button("Add to Watchlist");
        addToWatchlistButton.setOnAction(e -> addToWatchlist());

        Button openWatchlistButton = new Button("Open Watchlist");
        openWatchlistButton.setOnAction(e -> openWatchlist());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(searchField, searchButton, listView, detailsArea, addToWatchlistButton, openWatchlistButton);

        Scene scene = new Scene(layout, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void searchMovies() {
        String movieName = searchField.getText();
        if (movieName.isEmpty()) {
            showAlert("Error", "Please enter a movie name");
            return;
        }

        try {
            List<Title> titles = MovieScraper.searchMovie(movieName);
            listView.getItems().clear();
            listView.getItems().addAll(titles);
        } catch (IOException e) {
            showAlert("Error", "Failed to search movies");
            e.printStackTrace();
        }
    }

    private void showDetails(Title title) {
        if (title == null) {
            return;
        }

        try {
            Title detailedTitle = MovieScraper.getMovieDetails(title.getTitleUrl());
            detailsArea.setText("Title: " + detailedTitle.getTitle() +
                    "\nDescription: " + detailedTitle.getDescription() +
                    "\nGenres: " + detailedTitle.getGenres());
        } catch (IOException e) {
            showAlert("Error", "Failed to get movie details");
            e.printStackTrace();
        }
    }

    private void addToWatchlist() {
        Title title = listView.getSelectionModel().getSelectedItem();
        if (title == null) {
            showAlert("Error", "Please select a movie to add to watchlist");
            return;
        }

        List<Title> watchlist = JsonHandler.loadWatchlist();
        watchlist.add(title);
        JsonHandler.saveWatchlist(watchlist);
        showAlert("Success", "Movie added to watchlist");
    }

    private void openWatchlist() {
        Stage watchlistStage = new Stage();
        watchlistStage.setTitle("Watchlist");

        ListView<Title> watchlistView = new ListView<>();
        List<Title> watchlist = JsonHandler.loadWatchlist();
        watchlistView.getItems().addAll(watchlist);

        watchlistView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();

            @Override
            protected void updateItem(Title item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(item.getPreviewUrl(), 50, 75, true, true));
                    label.setText(item.getTitle());
                    HBox hBox = new HBox(imageView, label);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(watchlistView);

        Scene scene = new Scene(layout, 500, 600);
        watchlistStage.setScene(scene);
        watchlistStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
