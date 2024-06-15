package ru.mishgan325.watchlist.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.mishgan325.watchlist.controllers.WatchlistController;
import ru.mishgan325.watchlist.entities.Title;

public class WatchlistView {

    private Stage stage;
    private ListView<Title> listView;
    private Button deleteButton;
    private Button openLinkButton;

    public WatchlistView() {
        stage = new Stage();
        stage.setTitle("Watchlist");

        listView = new ListView<>();
        deleteButton = new Button("Delete");
        openLinkButton = new Button("Open Link");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(listView, new HBox(10, deleteButton, openLinkButton));

        Scene scene = new Scene(layout, 500, 600);
        stage.setScene(scene);

        WatchlistController controller = new WatchlistController(this);
        controller.loadWatchlist();

        deleteButton.setOnAction(e -> controller.deleteSelectedTitle());
        openLinkButton.setOnAction(e -> controller.openSelectedTitleLink());
    }

    public Stage getStage() {
        return stage;
    }

    public ListView<Title> getListView() {
        return listView;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getOpenLinkButton() {
        return openLinkButton;
    }
}
