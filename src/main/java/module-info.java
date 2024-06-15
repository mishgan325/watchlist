module ru.mishgan325.watchlist {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires com.google.gson;


    opens ru.mishgan325.watchlist to javafx.fxml, com.google.gson;
    exports ru.mishgan325.watchlist;
}