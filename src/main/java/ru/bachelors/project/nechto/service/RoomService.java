package ru.bachelors.project.nechto.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.bachelors.project.nechto.dto.Movie;
import ru.bachelors.project.nechto.dto.Room;
import ru.bachelors.project.nechto.dto.User;
import ru.bachelors.project.nechto.exceptions.RoomNotFoundException;

@Service
@Slf4j
public class RoomService {
    private final Map<String, Room> rooms = new HashMap<>(); // временно, пока нет бд; (sessionId, Room)

    public String createRoom(User leader) {
        String sessionId = generateUniqueSessionId();
        Room room = new Room(sessionId, leader);
        rooms.put(sessionId, room);
        log.info("Создана комната, sessionId = " + sessionId);
        return sessionId;
    }

    public boolean deleteRoom(String sessionId) {
        if (rooms.remove(sessionId) != null) {
            log.info("Удалена комната, sessionId = " + sessionId);
            return true;
        }
        log.error("Не удалось удалить комнату, sessionId = " + sessionId);
        throw new RoomNotFoundException();
    }

    public Room getRoomBySessionId(String sessionId) {
        Room room = rooms.get(sessionId);
        if (room == null) {
            throw new RoomNotFoundException();
        }
        return room;
    }

    public boolean joinRoom(String sessionId, User participant) {
        Room room = rooms.get(sessionId);
        if (room != null) {
            log.info("Участник " + participant.userId() + " присоединился к комнате " + sessionId);
            room.setParticipant(participant);
            return true;
        } else {
            log.error("Не удалось присоединиться к комнате, sessionId = " + sessionId);
            throw new RoomNotFoundException();
        }
    }

    public List<Movie> getMoviesByFilters(String filters) {
        // мок, пока нет базы. Нужно добавить проверку, что в room.getSuggestedMovies() нет дубликатов
        log.info("Получение из базы списка фильмов по фильтру");
        List<Movie> movies = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Movie movie = new Movie(
                    "movieName" + i,
                    "genre" + i,
                    "movieDirector" + i
            );
            movies.add(movie);
        }
        return movies;
    }

    private static String generateUniqueSessionId() {
        UUID uuid = UUID.randomUUID();
        String shortId = Long.toString(uuid.getMostSignificantBits(), Character.MAX_RADIX);
        return shortId.substring(0, Math.min(shortId.length(), 8));
    }
}
