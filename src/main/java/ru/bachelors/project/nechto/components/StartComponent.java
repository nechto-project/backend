//package ru.bachelors.project.nechto.components;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import ru.bachelors.project.nechto.models.Director;
//import ru.bachelors.project.nechto.models.Genre;
//import ru.bachelors.project.nechto.models.Movie;
//import ru.bachelors.project.nechto.service.DirectorService;
//import ru.bachelors.project.nechto.service.GenreService;
//import ru.bachelors.project.nechto.service.MovieService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class StartComponent implements ApplicationRunner {
//
//    private final GenreService genreService;
//    private final MovieService movieService;
//    private final DirectorService directorService;
//    private final RestTemplate restTemplate;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        for (int i = 1; i <= 20; i++) {
//            String urlFilms = "https://api.kinopoisk.dev/v1.4/movie?page=" + i +
//                    "&limit=250" +
//                    "&selectFields=name" +
//                    "&selectFields=rating" +
//                    "&selectFields=genres" +
//                    "&selectFields=poster" +
//                    "&selectFields=shortDescription" +
//                    "&selectFields=persons&persons.enProfession=director" +
//                    "&sortField=rating.kp" +
//                    "&sortType=-1" +
//                    "&notNullFields=name" +
//                    "&notNullFields=poster.url" +
//                    "&notNullFields=rating.kp" +
//                    "&type=movie";
//            String urlGenres = "https://api.kinopoisk.dev/v1/movie/possible-values-by-field?field=genres.name";
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("X-API-KEY", "46FKKWM-MX149FV-HZNKJ52-K2RAQ21");
//            headers.set("accept", "application/json");
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//            ResponseEntity<String> responseFilms = restTemplate.exchange(urlFilms, HttpMethod.GET, entity, String.class);
//            ResponseEntity<String> responseGenres = restTemplate.exchange(urlGenres, HttpMethod.GET, entity, String.class);
//            try {
//                JSONParser parser = new JSONParser();
//                JSONObject json = (JSONObject) parser.parse(responseFilms.getBody());
//                JSONArray docs = (JSONArray) json.get("docs");
//                for (Object obj : docs) {
//                    JSONObject movie = (JSONObject) obj;
//                    JSONObject rating = (JSONObject) movie.get("rating");
//                    double score;
//                    try {
//                        score = (double) rating.get("kp");
//                    } catch (Exception e) {
//                        Long num = (Long) rating.get("kp");
//                        score = num.doubleValue();
//                    }
//                    String name = (String) movie.get("name");
//                    JSONObject posters = (JSONObject) movie.get("poster");
//                    String poster = (String) posters.get("url");
//                    String description = (String) movie.get("shortDescription");
//                    JSONArray genres = (JSONArray) movie.get("genres");
//                    List<Genre> genreList = new ArrayList<>();
//                    for (Object object : genres) {
//                        JSONObject genreJSON = (JSONObject) object;
//                        String genre = (String) genreJSON.get("name");
//                        genreList.add(genreService.findGenreByName(genre));
//                    }
//                    JSONArray persons = (JSONArray) movie.get("persons");
//                    List<Director> directorList = new ArrayList<>();
//                    for (Object object : persons) {
//                        JSONObject personJSON = (JSONObject) object;
//                        if (personJSON.get("profession").equals("режиссеры")) {
//                            String director = (String) personJSON.get("name");
//                            directorList.add(directorService.getOrCreateDirector(director));
//                        }
//                    }
//                    movieService.getOrCreateMovie(new Movie(name, description, score, poster, genreList, directorList));
////            JSONParser parser = new JSONParser();
////            JSONArray genres = (JSONArray) parser.parse(responseGenres.getBody());
////            for(Object obj : genres){
////                JSONObject genre = (JSONObject) obj;
////                String name = (String) genre.get("name");
////                genreService.createGenre(new Genre(name));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//}
