package ru.mishgan325.watchlist;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.mishgan325.watchlist.views.SearchView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie smart watchlist");

        SearchView searchView = new SearchView(primaryStage);
        Scene scene = new Scene(searchView.getView(), 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
