package ru.bachelors.project.nechto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bachelors.project.nechto.models.Genre;
import ru.bachelors.project.nechto.repositories.GenreRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    public void createGenre(Genre genre) {
        if (genreRepository.findByName(genre.getName()) != null) {
            log.info("Genre already exist: {}", genre);
            return;
        }
        log.info("Create genre: {}", genre);
        genreRepository.save(genre);
    }

    public List<Genre> findAllGenres() {
        return List.of(
                new Genre(
                        1L, "аниме", Collections.emptyList()
                ),
                new Genre(
                        6L, "детектив", Collections.emptyList()
                ),
                new Genre(
                        7L, "детский", Collections.emptyList()
                ),
                new Genre(
                        10L, "драма", Collections.emptyList()
                ),
                new Genre(
                        13L, "комедия", Collections.emptyList()
                ),
                new Genre(
                        17L, "мелодрама", Collections.emptyList()
                ),
                new Genre(
                        22L, "приключения", Collections.emptyList()
                ),
                new Genre(
                        24L, "семейный", Collections.emptyList()
                ),
                new Genre(
                        25L, "спорт", Collections.emptyList()
                ),
                new Genre(
                        27L, "триллер", Collections.emptyList()
                ),
                new Genre(
                        28L, "ужасы", Collections.emptyList()
                ),
                new Genre(
                        29L, "фантастика", Collections.emptyList()
                )
        );
    }

    public Genre findGenreByName(String name) {
        return genreRepository.findByName(name);
    }
}
