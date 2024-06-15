package ru.mishgan325.watchlist.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import ru.mishgan325.watchlist.entities.Title;
import ru.mishgan325.watchlist.utils.JsonHandler;
import ru.mishgan325.watchlist.views.WatchlistView;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class WatchlistController {

    private WatchlistView view;

    public WatchlistController(WatchlistView view) {
        this.view = view;
    }

    public void loadWatchlist() {
        List<Title> watchlist = JsonHandler.loadWatchlist();
        view.getListView().getItems().addAll(watchlist);

        view.getListView().setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label titleLabel = new Label();
            private final Label descriptionLabel = new Label();

            @Override
            protected void updateItem(Title item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    setStyle("-fx-border-color: #b6b6b6; -fx-border-width: 0 0 0.5px 0;");

                    imageView.setImage(new Image(item.getPreviewUrl(), 50, 75, true, true));

                    VBox vBox = new VBox();

                    titleLabel.setText(item.getTitle());
                    titleLabel.setStyle("-fx-font-weight: bold;");
                    titleLabel.setMaxWidth(385);

                    descriptionLabel.setText(item.getDescription());
                    descriptionLabel.setMaxWidth(385);
                    descriptionLabel.setWrapText(true);
                    descriptionLabel.setStyle("-fx-font-style: italic;");

                    vBox.getChildren().addAll(titleLabel, descriptionLabel);

                    HBox hBox = new HBox(imageView, vBox);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
        });
    }

    public void deleteSelectedTitle() {
        Title selectedTitle = view.getListView().getSelectionModel().getSelectedItem();
        if (selectedTitle != null) {
            view.getListView().getItems().remove(selectedTitle);
            List<Title> watchlist = JsonHandler.loadWatchlist();
            watchlist.remove(selectedTitle);
            JsonHandler.saveWatchlist(watchlist);
        } else {
            showAlert("Error", "Please select a title to delete.");
        }
    }

    public void openSelectedTitleLink() {
        Title selectedTitle = view.getListView().getSelectionModel().getSelectedItem();
        if (selectedTitle != null) {
            try {
                Desktop.getDesktop().browse(new URI(selectedTitle.getTitleUrl()));
            } catch (IOException | IllegalArgumentException e) {
                showAlert("Error", "Failed to open the link.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Please select a title to open.");
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
