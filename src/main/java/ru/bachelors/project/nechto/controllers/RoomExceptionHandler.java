package ru.bachelors.project.nechto.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.bachelors.project.nechto.dto.ApiErrorResponse;
import ru.bachelors.project.nechto.exceptions.RoomNotFoundException;

@RestControllerAdvice
public class RoomExceptionHandler {
    private static final String BAD_REQUEST = "400";
    private static final String NOT_FOUND = "404";

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleRoomNotFoundException(RoomNotFoundException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(NOT_FOUND,"Данная комната не найдена", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
