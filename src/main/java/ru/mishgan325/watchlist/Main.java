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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private ListView<MovieScraper.Movie> listView;
    private TextField searchField;
    private TextArea detailsArea;
    private static final String WATCHLIST_FILE = "watchlist.txt";

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
            protected void updateItem(MovieScraper.Movie item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(item.getImageUrl(), 50, 75, true, true));
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
            ArrayList<MovieScraper.Movie> movies = MovieScraper.searchMovie(movieName);
            listView.getItems().clear();
            listView.getItems().addAll(movies);
        } catch (IOException e) {
            showAlert("Error", "Failed to search movies");
            e.printStackTrace();
        }
    }

    private void showDetails(MovieScraper.Movie movie) {
        if (movie == null) {
            return;
        }

        try {
            String details = MovieScraper.getMovieDetails(movie.getUrl());
            detailsArea.setText(details);
        } catch (IOException e) {
            showAlert("Error", "Failed to get movie details");
            e.printStackTrace();
        }
    }

    private void addToWatchlist() {
        MovieScraper.Movie movie = listView.getSelectionModel().getSelectedItem();
        if (movie == null) {
            showAlert("Error", "Please select a movie to add to watchlist");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(WATCHLIST_FILE, true))) {
            writer.write(movie.getTitle() + "|" + movie.getUrl() + "|" + movie.getImageUrl());
            writer.newLine();
            showAlert("Success", "Movie added to watchlist");
        } catch (IOException e) {
            showAlert("Error", "Failed to add movie to watchlist");
            e.printStackTrace();
        }
    }

    private void openWatchlist() {
        Stage watchlistStage = new Stage();
        watchlistStage.setTitle("Watchlist");

        ListView<MovieScraper.Movie> watchlistView = new ListView<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(WATCHLIST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String title = parts[0];
                    String url = parts[1];
                    String imageUrl = parts[2];
                    watchlistView.getItems().add(new MovieScraper.Movie(title, url, imageUrl));
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load watchlist");
            e.printStackTrace();
        }

        watchlistView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();

            @Override
            protected void updateItem(MovieScraper.Movie item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(item.getImageUrl(), 50, 75, true, true));
                    label.setText(item.getTitle());
                    HBox hBox = new HBox(imageView, label);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
        });

        Button removeButton = new Button("Remove Selected");
        removeButton.setOnAction(e -> {
            MovieScraper.Movie selectedMovie = watchlistView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                watchlistView.getItems().remove(selectedMovie);
                saveWatchlist(watchlistView.getItems());
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(watchlistView, removeButton);

        Scene scene = new Scene(layout, 400, 500);
        watchlistStage.setScene(scene);
        watchlistStage.show();
    }

    private void saveWatchlist(List<MovieScraper.Movie> watchlist) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(WATCHLIST_FILE))) {
            for (MovieScraper.Movie movie : watchlist) {
                writer.write(movie.getTitle() + "|" + movie.getUrl() + "|" + movie.getImageUrl());
                writer.newLine();
            }
            showAlert("Success", "Watchlist updated");
        } catch (IOException e) {
            showAlert("Error", "Failed to save watchlist");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}