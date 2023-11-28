package ru.bachelors.project.nechto.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.bachelors.project.nechto.dto.MovieDto;
import ru.bachelors.project.nechto.dto.UserDto;
import ru.bachelors.project.nechto.exceptions.RoomNotFoundException;
import ru.bachelors.project.nechto.models.Director;
import ru.bachelors.project.nechto.models.Genre;
import ru.bachelors.project.nechto.models.Movie;
import ru.bachelors.project.nechto.models.Room;
import ru.bachelors.project.nechto.repositories.GenreRepository;
import ru.bachelors.project.nechto.repositories.RoomRepository;

@Service
@Slf4j
public class RoomService {
    RoomRepository roomRepository;
    GenreRepository genreRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, GenreRepository genreRepository) {
        this.roomRepository = roomRepository;
        this.genreRepository = genreRepository;
    }

    public String createRoom(UserDto leader) {
        String sessionId = generateUniqueSessionId();
        Optional<Room> optionalRoom = roomRepository.findById(sessionId);
        if(optionalRoom.isPresent()) {
            log.info("Room already exist: {}", sessionId);
            return optionalRoom.get().getSessionId();
        }
        Room room = new Room(sessionId, leader.userId());
        roomRepository.save(room);
        log.info("Created room, sessionId = " + sessionId);
        return sessionId;
    }

    public void deleteRoom(String sessionId) {
        roomRepository.deleteById(sessionId);
    }

    public Room getRoomBySessionId(String sessionId) {
        Optional<Room> optionalRoom = roomRepository.findById(sessionId);
        if (optionalRoom.isEmpty()) {
            log.error("Комната не найдена, sessionId = " + sessionId);
            throw new RoomNotFoundException();
        }
        return optionalRoom.get();
    }

    public boolean joinRoom(String sessionId, UserDto participant) {
        try {
            Room room = getRoomBySessionId(sessionId);
            log.info("Участник " + participant.userId() + " присоединился к комнате " + sessionId);
            room.setParticipant(participant.userId());
            roomRepository.save(room);
            return true;
        } catch (RoomNotFoundException ignored) {
            log.error("Не удалось присоединиться к комнате, sessionId = " + sessionId);
            return false;
        }
    }

    //    {
    //      "genres": ["genre1", "genre2"]
    //    }
    // пример json строки filters
    public List<MovieDto> getMoviesByFilters(String sessionId) {
        Room room = getRoomBySessionId(sessionId);
        List<Movie> filteredMovies = room.getMovies();

        List<MovieDto> filteredMoviesDto = new ArrayList<>();
        for (Movie movie: filteredMovies) {
            filteredMoviesDto.add(new MovieDto(
                    movie.getMovieId(),
                    movie.getName(),
                    movie.getDescription(),
                    movie.getScore(),
                    movie.getPoster(),
                    movie.getGenres().stream().map(Genre::getName).toArray(String[]::new),
                    movie.getDirectors().stream().map(Director::getName).toArray(String[]::new)));
        }

        return filteredMoviesDto;
    }

    public void saveFiltredMovies(Room room, String filters) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(filters);

            // Получение списка строк
            JSONArray stringListJson = (JSONArray) jsonObject.get("genres");

            // Преобразование JSONArray в List<String>
            List<String> genreList = new ArrayList<>();
            for (Object value : stringListJson) {
                genreList.add((String) value);
            }

            List<Movie> filteredMovies = new ArrayList<>();
            for (String genre : genreList) {
                filteredMovies.addAll(genreRepository.findByName(genre).getMovies());
            }
            Collections.shuffle(filteredMovies);
//            filteredMovies = filteredMovies.stream().limit(30).toList();
            room.setMovies(filteredMovies.subList(0, 30));
            roomRepository.save(room);

        } catch (ParseException e) {
            log.info("Ошибка при сохранении фильмов: " + filters);
            e.printStackTrace();
        }
    }

    private static String generateUniqueSessionId() {
        UUID uuid = UUID.randomUUID();
        String shortId = Long.toString(uuid.getMostSignificantBits(), Character.MAX_RADIX);
        return shortId.substring(0, Math.min(shortId.length(), 8));
    }
}
