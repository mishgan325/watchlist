package ru.mishgan325.watchlist.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.mishgan325.watchlist.entities.Title;

import java.io.IOException;
import java.util.ArrayList;

public class MovieScraper {

    private static final String SEARCH_URL = "https://www.imdb.com/find?q=";

    public static ArrayList<Title> searchMovie(String movieName) throws IOException {
        ArrayList<Title> result = new ArrayList<>();
        String searchURL = SEARCH_URL + movieName.replace(" ", "+");
        Document doc = Jsoup.connect(searchURL).get();
        Elements movieElements = doc.select("section[data-testid='find-results-section-title'] li.ipc-metadata-list-summary-item");

        for (Element movieElement : movieElements) {
            String title = movieElement.select("a.ipc-metadata-list-summary-item__t").text();
            String movieURL = "https://www.imdb.com" + movieElement.select("a.ipc-metadata-list-summary-item__t").attr("href");
            movieURL = removeRef(movieURL);


            result.add(getMovieDetails(movieURL));
        }

        return result;
    }

    public static String removeRef(String url) {
        int index = url.indexOf("?");
        if (index != -1) {
            return url.substring(0, index);
        }
        return url;
    }

    public static Title getMovieDetails(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        // Получение названия
        String title = doc.select("h1[data-testid='hero__pageTitle'] span[data-testid='hero__primary-text']").text();

        // Получение полного описания
        Element descriptionElement = doc.selectFirst("span[data-testid='plot-xl']");
        String description;
        if (descriptionElement != null) {
            description = descriptionElement.text().trim();
        } else {
            description = "Description not found.";
        }

        // Получение жанров
        Elements genreElements = doc.select("div.ipc-chip-list__scroller a.ipc-chip span.ipc-chip__text");
        StringBuilder genres = new StringBuilder();
        for (Element genreElement : genreElements) {
            genres.append(genreElement.text()).append(", ");
        }
        if (genres.length() > 0) {
            genres.setLength(genres.length() - 2); // Удаляем лишнюю запятую и пробел
        }

        // Получение превью URL
        String previewUrl = doc.selectFirst("img.ipc-image").attr("src");

        // Возвращение информации в виде объекта Title
        return new Title(title, description, genres.toString(), previewUrl, url);
    }
}
