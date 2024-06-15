package ru.mishgan325.watchlist.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import ru.mishgan325.watchlist.entities.Title;
import ru.mishgan325.watchlist.controllers.SearchController;

public class SearchView {

    private VBox view;
    private TextField searchField;
    private ListView<Title> listView;
    private TextArea detailsArea;
    private Button searchButton;
    private Button addToWatchlistButton;
    private Button openWatchlistButton;

    public SearchView(Stage primaryStage) {
        searchField = new TextField();
        searchField.setPromptText("Enter movie name");

        searchButton = new Button("Search");
        addToWatchlistButton = new Button("Add to Watchlist");
        openWatchlistButton = new Button("Open Watchlist");

        listView = new ListView<>();
        detailsArea = new TextArea();
        detailsArea.setEditable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(searchField, searchButton, listView, detailsArea, addToWatchlistButton, openWatchlistButton);
        view = layout;

        SearchController controller = new SearchController(this, primaryStage);
        searchButton.setOnAction(e -> controller.searchMovies());
        addToWatchlistButton.setOnAction(e -> controller.addToWatchlist());
        openWatchlistButton.setOnAction(e -> controller.openWatchlist());

        listView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label titleLabel = new Label();
            private final Label genreLabel = new Label();


            @Override
            protected void updateItem(Title item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setStyle("-fx-border-color: #b6b6b6; -fx-border-width: 0 0 0.5px 0;");


                    imageView.setImage(new Image(item.getPreviewUrl(), 50, 75, true, true));
                    titleLabel.setText(item.getTitle());
                    titleLabel.setStyle("-fx-font-weight: bold;");

                    VBox vBox = new VBox();

                    genreLabel.setText(item.getGenres());
                    genreLabel.setStyle("-fx-underline: true;");

                    vBox.getChildren().addAll(titleLabel, genreLabel);

                    HBox hBox = new HBox(imageView, vBox);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> controller.showDetails(newValue));
    }

    public VBox getView() {
        return view;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public ListView<Title> getListView() {
        return listView;
    }

    public TextArea getDetailsArea() {
        return detailsArea;
    }
}
