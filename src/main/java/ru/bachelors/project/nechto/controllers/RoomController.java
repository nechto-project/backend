package ru.bachelors.project.nechto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.bachelors.project.nechto.dto.Room;
import ru.bachelors.project.nechto.dto.User;
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
    public String createRoom(@RequestBody User leader) {
        return roomService.createRoom(leader);
    }

    @PostMapping("/join/{sessionId}")
    public boolean joinRoom(@PathVariable String sessionId,
                         @RequestBody User participant) {
        return roomService.joinRoom(sessionId, participant);
    }

    @PostMapping("/delete/{sessionId}")
    public boolean deleteRoom(@PathVariable String sessionId) {
        return roomService.deleteRoom(sessionId);
    }

    @PostMapping("/{sessionId}/filters")
    public void setFiltersToRoom(@PathVariable String sessionId,
                           @RequestBody String filters) {
        log.info("Установка фильтров для комнаты, sessionId " + sessionId);
        Room room = roomService.getRoomBySessionId(sessionId);
        room.setMovieFilters(filters);
    }
}
