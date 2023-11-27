package ru.bachelors.project.nechto.dto;

public record MovieDto(Long movieId, String name, String description, double score, String poster, String[] genres, String[] directors) {
}
