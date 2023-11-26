package ru.bachelors.project.nechto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bachelors.project.nechto.models.Genre;
import ru.bachelors.project.nechto.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    public void createGenre(Genre genre) {
        if(genreRepository.findByName(genre.getName()) != null) {
            log.info("Genre already exist: {}", genre);
            return;
        }
        log.info("Create genre: {}", genre);
        genreRepository.save(genre);
    }

    public Genre findGenreByName(String name) {
        return genreRepository.findByName(name);
    }
}
