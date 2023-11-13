package ru.bachelors.project.nechto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bachelors.project.nechto.models.Director;

public interface DirectorRepository  extends JpaRepository<Director, Integer> {
}
