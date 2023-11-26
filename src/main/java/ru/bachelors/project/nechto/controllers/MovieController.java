package ru.bachelors.project.nechto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.bachelors.project.nechto.models.Movie;
import ru.bachelors.project.nechto.models.Room;
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
    public ResponseEntity<List<Movie>> startMovieSearch(@PathVariable String sessionId) {
        return ResponseEntity.ok().body(roomService.getMoviesByFilters(sessionId));
    }
}
