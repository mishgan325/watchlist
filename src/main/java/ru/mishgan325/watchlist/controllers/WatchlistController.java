package ru.mishgan325.watchlist.controllers;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import ru.mishgan325.watchlist.entities.Title;
import ru.mishgan325.watchlist.utils.JsonHandler;
import ru.mishgan325.watchlist.views.WatchlistView;

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
    }
}
