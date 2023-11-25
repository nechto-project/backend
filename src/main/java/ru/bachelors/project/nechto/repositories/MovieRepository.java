package ru.bachelors.project.nechto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bachelors.project.nechto.models.Movie;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByName(String name);
}
