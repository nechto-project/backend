package ru.bachelors.project.nechto.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Room {
    private final String sessionId;
    private final User leader; //если лидер выходит, комната удаляется
    private User participant;
    private String movieFilters;

    private List<Movie> allMovies; // тут будет большой список из фильмов, мы будет предлагать по 30 штук за раз

    public Room(String sessionId, User leader) {
        this.sessionId = sessionId;
        this.leader = leader;
    }

    public List<Movie> getBatchOfMovies() {
        List<Movie> batchOfMovies = allMovies.stream().limit(30).toList();
        allMovies = allMovies.stream().skip(30).toList();
        return batchOfMovies;
    }
}
