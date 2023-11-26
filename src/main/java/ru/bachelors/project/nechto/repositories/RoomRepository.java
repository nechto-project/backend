package ru.bachelors.project.nechto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.bachelors.project.nechto.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

}
