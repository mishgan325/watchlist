package ru.mishgan325.watchlist.entities;

public class Title {
    private String title;
    private String description;
    private String previewUrl;
    private String titleUrl;
    private String genres;
    private int priority = 5;

    public Title(String title, String description, String genres, String previewUrl, String titleUrl) {
        this.title = title;
        this.description = description;
        this.previewUrl = previewUrl;
        this.titleUrl = titleUrl;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public String getGenres() {
        return genres;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
