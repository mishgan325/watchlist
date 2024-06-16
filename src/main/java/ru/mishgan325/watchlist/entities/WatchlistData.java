package ru.mishgan325.watchlist.entities;

import java.util.List;

public class WatchlistData {
    private Title watchingNow;
    private List<Title> watchlist;

    public Title getWatchingNow() {
        return watchingNow;
    }

    public void setWatchingNow(Title watchingNow) {
        this.watchingNow = watchingNow;
    }

    public List<Title> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Title> watchlist) {
        this.watchlist = watchlist;
    }
}