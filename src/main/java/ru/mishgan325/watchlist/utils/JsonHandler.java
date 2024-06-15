package ru.mishgan325.watchlist.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.mishgan325.watchlist.entities.Title;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    private static final String WATCHLIST_FILE = "watchlist.json";

    public static void saveWatchlist(List<Title> watchlist) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(WATCHLIST_FILE)) {
            gson.toJson(watchlist, writer);
            System.out.println("Watchlist saved to JSON file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Title> loadWatchlist() {
        File file = new File(WATCHLIST_FILE);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>(); // Return an empty list if the file doesn't exist or is empty
        }

        Gson gson = new Gson();
        List<Title> watchlist = new ArrayList<>();
        try (Reader reader = new FileReader(WATCHLIST_FILE)) {
            Type type = new TypeToken<List<Title>>() {}.getType();
            watchlist = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return watchlist;
    }
}
