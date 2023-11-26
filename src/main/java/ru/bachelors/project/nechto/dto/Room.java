//package ru.bachelors.project.nechto.dto;
//
//import java.util.List;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Setter
//@Getter
//public class Room {
//    private final String sessionId;
//    private final UserDto leader; //если лидер выходит, комната удаляется
//    private UserDto participant;
//    private String movieFilters;
//
//    private List<MovieDto> allMovies; // тут будет большой список из фильмов, мы будет предлагать по 30 штук за раз
//
//    public Room(String sessionId, UserDto leader) {
//        this.sessionId = sessionId;
//        this.leader = leader;
//    }
//
//    public List<MovieDto> getBatchOfMovies() {
//        List<MovieDto> batchOfMovies = allMovies.stream().limit(30).toList();
//        allMovies = allMovies.stream().skip(30).toList();
//        return batchOfMovies;
//    }
//}
