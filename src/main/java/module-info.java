module ru.mishgan325.watchlist {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;


    opens ru.mishgan325.watchlist to javafx.fxml;
    exports ru.mishgan325.watchlist;
}