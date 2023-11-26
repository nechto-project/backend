package ru.bachelors.project.nechto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bachelors.project.nechto.models.Director;
import ru.bachelors.project.nechto.repositories.DirectorRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorService {
    private final DirectorRepository directorRepository;

    public Director getOrCreateDirector (String director) {
        Director currentDirector = directorRepository.findByName(director);
        if(currentDirector == null) {
            log.info("Create director: {}", director);
            directorRepository.save(new Director(director));
            currentDirector = directorRepository.findByName(director);
            return currentDirector;
        }
        log.info("Current director already exist: {}", currentDirector);
        return currentDirector;
    }
}
