package ru.bachelors.project.nechto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.bachelors.project.nechto.dto.Movie;
import ru.bachelors.project.nechto.dto.Room;
import ru.bachelors.project.nechto.service.RoomService;

@RestController
@RequestMapping("/movie/search")
public class MovieController {
    private final RoomService roomService;

    @Autowired
    public MovieController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{sessionId}")
    public List<Movie> startMovieSearch(@PathVariable String sessionId) {
        Room room = roomService.getRoomBySessionId(sessionId);
        if (room.getAllMovies() == null || room.getAllMovies().isEmpty()) {
            room.setAllMovies(roomService.getMoviesByFilters(room.getMovieFilters()));
        }
        return room.getBatchOfMovies();
    }
}
