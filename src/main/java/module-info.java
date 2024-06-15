module ru.mishgan325.watchlist {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.jsoup;
    requires java.desktop;

    exports ru.mishgan325.watchlist;
    exports ru.mishgan325.watchlist.views;
    exports ru.mishgan325.watchlist.controllers;
    exports ru.mishgan325.watchlist.entities;
    exports ru.mishgan325.watchlist.utils;

    opens ru.mishgan325.watchlist.entities to com.google.gson;
}
