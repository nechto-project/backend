package ru.bachelors.project.nechto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bachelors.project.nechto.models.Director;

@Repository
public interface DirectorRepository  extends JpaRepository<Director, Integer> {
    Director findByName(String name);
}
