package ru.mishgan325.watchlist.utils;

import ru.mishgan325.watchlist.entities.Title;
import ru.mishgan325.watchlist.entities.WatchlistData;

import java.util.List;

public class TitleListHelper {

    public static void remove(Title title) {
        WatchlistData watchlistData = JsonHelper.loadWatchlist();
        List<Title> watchlist = watchlistData.getWatchlist();
        watchlist.remove(title);
        JsonHelper.saveWatchlist(watchlist);
    }

    public static void add(Title title) {
        WatchlistData watchlistData = JsonHelper.loadWatchlist();
        List<Title> watchlist = watchlistData.getWatchlist();
        watchlist.add(title);
        JsonHelper.saveWatchlist(watchlist);
    }

}
