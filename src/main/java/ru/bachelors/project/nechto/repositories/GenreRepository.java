package ru.bachelors.project.nechto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bachelors.project.nechto.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
