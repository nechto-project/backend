package ru.bachelors.project.nechto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bachelors.project.nechto.models.Movie;
import ru.bachelors.project.nechto.repositories.MovieRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie getOrCreateMovie (Movie movie) {
        Movie currentMovie = movieRepository.findByName(movie.getName().toString());
        if(currentMovie == null) {
            log.info("Create movie: {}", movie);
            Movie savedMovie = new Movie();
            savedMovie.setName(movie.getName());
            savedMovie.setDescription(movie.getDescription());
            savedMovie.setScore(movie.getScore());
            savedMovie.setPoster(movie.getPoster());
            savedMovie.getDirectors().addAll(movie.getDirectors());
            savedMovie.getGenres().addAll(movie.getGenres());
            movieRepository.save(savedMovie);
            return movie;
        }
        log.info("Current movie already exist: {}", currentMovie);
        return currentMovie;
    }
}
