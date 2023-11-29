package ru.bachelors.project.nechto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import ru.bachelors.project.nechto.dto.AnswerDto;
import ru.bachelors.project.nechto.dto.IsMatch;
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
    public String createRoom(@RequestBody String[] genres) {
        return roomService.createRoom(genres);
    }

    @PostMapping("/join/{sessionId}")
    public boolean joinRoom(@PathVariable String sessionId) {
        return roomService.joinRoom(sessionId);
    }

    @PostMapping("/delete/{sessionId}")
    public void deleteRoom(@PathVariable String sessionId) {
        roomService.deleteRoom(sessionId);
    }

    @PostMapping("/decision/{sessionId}")
    public ResponseEntity decisionMovie(@PathVariable String sessionId, @RequestParam boolean isLeader, @RequestBody AnswerDto answer){
        try {
            roomService.setDecision(sessionId, isLeader, answer);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/ismatch/{sessionId}")
    public ResponseEntity<IsMatch> isMatch(@PathVariable String sessionId) {
        try {
            return ResponseEntity.ok().body(roomService.isMatch(sessionId));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isjoin/{sessionId}")
    public ResponseEntity<Boolean> isJoin(@PathVariable String sessionId) {
        try {
            return ResponseEntity.ok().body(roomService.isJoin(sessionId));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
