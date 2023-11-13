package ru.bachelors.project.nechto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bachelors.project.nechto.models.Movie;

public interface MovieGenreRepository extends JpaRepository<Movie, Integer> {
}