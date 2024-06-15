package ru.mishgan325.watchlist.views;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ru.mishgan325.watchlist.entities.Title;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RandomTitleDialog extends Dialog<ButtonType> {
    private final ButtonType watchButton;
    private final ButtonType postponeButton;

    public RandomTitleDialog(Title title) {
        setTitle("Random Title");

        Label titleLabel = new Label(title.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(title.getPreviewUrl(), 380, 600, true, true));

        // Creating a hyperlink that opens the title's URL
        Hyperlink openLink = new Hyperlink("Open Link");
        openLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(title.getTitleUrl()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER); // Center alignment
        content.getChildren().addAll(titleLabel, imageView, openLink);
        getDialogPane().setContent(content);

        watchButton = new ButtonType("Watch");
        postponeButton = new ButtonType("Postpone");

        getDialogPane().getButtonTypes().addAll(watchButton, postponeButton);

        setResultConverter(buttonType -> buttonType);
    }

    public ButtonType getWatchButtonType() {
        return watchButton;
    }

    public ButtonType getPostponeButtonType() {
        return postponeButton;
    }
}
