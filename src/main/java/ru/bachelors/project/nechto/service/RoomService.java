package ru.bachelors.project.nechto.service;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.bachelors.project.nechto.dto.AnswerDto;
import ru.bachelors.project.nechto.dto.IsMatch;
import ru.bachelors.project.nechto.dto.MovieDto;
import ru.bachelors.project.nechto.exceptions.RoomNotFoundException;
import ru.bachelors.project.nechto.models.*;
import ru.bachelors.project.nechto.repositories.GenreRepository;
import ru.bachelors.project.nechto.repositories.MovieRepository;
import ru.bachelors.project.nechto.repositories.RoomRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;


    public String createRoom(String[] genres) {
        log.info("жанры" + genres.length);
        String sessionId = generateUniqueSessionId();
        Optional<Room> optionalRoom = roomRepository.findById(sessionId);
        if(optionalRoom.isPresent()) {
            log.info("Room already exist: {}", sessionId);
            return optionalRoom.get().getSessionId();
        }

        List<Movie> filteredMovies = genreRepository.findByName(genres[0]).getMovies();
        for (String genre : genres) {
            filteredMovies.retainAll(genreRepository.findByName(genre).getMovies());
        }
        Collections.shuffle(filteredMovies);
        Room room = new Room(sessionId, filteredMovies.size() >= 30 ? filteredMovies.subList(0, 30) : filteredMovies);
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

    public boolean joinRoom(String sessionId) {
        try {
            Room room = getRoomBySessionId(sessionId);
            if(room.isJoin()) {
                log.info("К " + sessionId + " комнате уже подключен пользователь");
                return false;
            }
            log.info("Участник присоединился к комнате " + sessionId);
            room.setJoin(true);
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
    public List<MovieDto> getMoviesByFilters(String sessionId) throws Exception {
        Room room = getRoomBySessionId(sessionId);
        List<Movie> filteredMovies = room.getMovies();
        if (filteredMovies.isEmpty()) {
            throw new Exception();
        }
        List<MovieDto> filteredMoviesDto = new ArrayList<>();
        for (Movie movie: filteredMovies) {
            filteredMoviesDto.add(createMovieDto(movie));
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

    public void setDecision(String sessionId, boolean isLeader, AnswerDto answer) {
        try {
            Room room = getRoomBySessionId(sessionId);
            log.info("Установака ответа" + (isLeader ? "лидера " : "друга ") + answer.answer() + " " + answer.idMovie());
            ArrayList<Answer> answers = room.getAnswers(isLeader);
            answers.add(new Answer(answer));
            room.setAnswer(answers, isLeader);
            roomRepository.save(room);
        }
        catch (Exception e)
        {
            throw e;
        }


    }

    public IsMatch isMatch(String sessionId) {
        try {
            Room room = getRoomBySessionId(sessionId);
            ArrayList<Answer> leaderAnswers = room.getAnswers(true);
            ArrayList<Answer> participantAnswers = room.getAnswers(false);
            leaderAnswers.retainAll(participantAnswers);
            if(!leaderAnswers.isEmpty()) {
                log.info("Произошел Match id фильма = " + leaderAnswers.get(0));
                int idMovie = leaderAnswers.get(0).getIdMovie();
                Optional<Movie> optionalMovie = movieRepository.findById(Integer.toUnsignedLong(idMovie));
                Movie movie;
                if (optionalMovie.isPresent()) {
                    movie = optionalMovie.get();
                    return new IsMatch(true, createMovieDto(movie));
                }
                else {
                    throw new RuntimeException();
                }
            }
            else {
                int countOfMovies = room.getMovies().size();
                if (leaderAnswers.size() == countOfMovies && participantAnswers.size() == countOfMovies) {
                    return new IsMatch(true, null);
                }
                return new IsMatch(false, null);
            }
        }
        catch (Exception e) {
            throw e;
        }
    }

    public MovieDto createMovieDto(Movie movie) {
        return(new MovieDto(
                movie.getMovieId(),
                movie.getName(),
                movie.getDescription(),
                movie.getScore(),
                movie.getPoster(),
                movie.getGenres().stream().map(Genre::getName).toArray(String[]::new),
                movie.getDirectors().stream().map(Director::getName).toArray(String[]::new)));
    }

    public Boolean isJoin(String sessionId) {
        try {
            Room room = getRoomBySessionId(sessionId);
            return room.isJoin();
        }
        catch (Exception e) {
            throw e;
        }
    }
}
