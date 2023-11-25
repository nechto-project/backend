package ru.bachelors.project.nechto.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.bachelors.project.nechto.models.Director;
import ru.bachelors.project.nechto.models.Genre;
import ru.bachelors.project.nechto.models.Movie;
import ru.bachelors.project.nechto.service.DirectorService;
import ru.bachelors.project.nechto.service.GenreService;
import ru.bachelors.project.nechto.service.MovieService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdatingMovieComponent {

    private final GenreService genreService;
    private final MovieService movieService;
    private final DirectorService directorService;
    private final RestTemplate restTemplate;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateNewMovie() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime previousDayDateTime = currentDateTime.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedPreviousDay = previousDayDateTime.format(formatter);
        String formattedCurrentDay = currentDateTime.format(formatter);
        log.info("update movies" + formattedPreviousDay + "-" + formattedCurrentDay);
        String urlFilms = "https://api.kinopoisk.dev/v1.4/movie?page=1" +
                "&limit=250" +
                "&selectFields=name" +
                "&selectFields=rating" +
                "&selectFields=genres" +
                "&selectFields=poster" +
                "&selectFields=shortDescription" +
                "&selectFields=persons&persons.enProfession=director" +
                "&sortField=rating.kp" +
                "&sortType=-1" +
                "&notNullFields=name" +
                "&notNullFields=poster.url" +
                "&notNullFields=rating.kp" +
                "&type=movie" +
                "&createdAt=" + formattedPreviousDay + "-" + formattedCurrentDay;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "46FKKWM-MX149FV-HZNKJ52-K2RAQ21");
        headers.set("accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseFilms = restTemplate.exchange(urlFilms, HttpMethod.GET, entity, String.class);
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseFilms.getBody());
            JSONArray docs = (JSONArray) json.get("docs");
            for (Object obj : docs) {
                JSONObject movie = (JSONObject) obj;
                JSONObject rating = (JSONObject) movie.get("rating");
                double score;
                try {
                    score = (double) rating.get("kp");
                } catch (Exception e) {
                    Long num = (Long) rating.get("kp");
                    score = num.doubleValue();
                }
                String name = (String) movie.get("name");
                JSONObject posters = (JSONObject) movie.get("poster");
                String poster = (String) posters.get("url");
                String description = (String) movie.get("shortDescription");
                JSONArray genres = (JSONArray) movie.get("genres");
                List<Genre> genreList = new ArrayList<>();
                for (Object object : genres) {
                    JSONObject genreJSON = (JSONObject) object;
                    String genre = (String) genreJSON.get("name");
                    genreList.add(genreService.findGenreByName(genre));
                }
                JSONArray persons = (JSONArray) movie.get("persons");
                List<Director> directorList = new ArrayList<>();
                for (Object object : persons) {
                    JSONObject personJSON = (JSONObject) object;
                    if (personJSON.get("profession").equals("режиссеры")) {
                        String director = (String) personJSON.get("name");
                        directorList.add(directorService.getOrCreateDirector(director));
                    }
                }
                movieService.getOrCreateMovie(new Movie(name, description, score, poster, genreList, directorList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
