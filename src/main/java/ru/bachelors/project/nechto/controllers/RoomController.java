package ru.bachelors.project.nechto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.bachelors.project.nechto.dto.UserDto;
import ru.bachelors.project.nechto.models.Room;
import ru.bachelors.project.nechto.service.RoomService;

@RestController
@Slf4j
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public String createRoom(@RequestBody UserDto leader) {
        return roomService.createRoom(leader);
    }

    @PostMapping("/join/{sessionId}")
    public boolean joinRoom(@PathVariable String sessionId,
                         @RequestBody UserDto participant) {
        return roomService.joinRoom(sessionId, participant);
    }

    @PostMapping("/delete/{sessionId}")
    public void deleteRoom(@PathVariable String sessionId) {
        roomService.deleteRoom(sessionId);
    }

    @PostMapping("/{sessionId}/filters")
    public void setFiltersToRoom(@PathVariable String sessionId,
                           @RequestBody String filters) {
        log.info("Установка фильмов по фильтру для комнаты, sessionId " + sessionId);
        Room room = roomService.getRoomBySessionId(sessionId);
        roomService.saveFiltredMovies(room, filters);
    }
}
