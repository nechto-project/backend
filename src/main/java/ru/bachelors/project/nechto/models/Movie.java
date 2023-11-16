package ru.bachelors.project.nechto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private int movieId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "score")
    private double score;
    @Column(name = "poster")
    private String poster;
    @ManyToMany()
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "id_genre"),
            inverseJoinColumns = @JoinColumn(name = "id_movie")
    )
    private List<Genre> genres = new ArrayList<>();
    @ManyToMany()
    @JoinTable(
            name = "movie_director",
            joinColumns = @JoinColumn(name = "id_director"),
            inverseJoinColumns = @JoinColumn(name = "id_movie")
    )
    private List<Director> directors = new ArrayList<>();

    public Movie(String name,
                 String description,
                 double score,
                 String poster,
                 List<Genre> genres,
                 List<Director> directors) {
        this.name = name;
        this.description = description;
        this.score = score;
        this.poster = poster;
        this.genres = genres;
        this.directors = directors;
    }

    @Override
    public String toString() {
        String text = name == null ? "null" : name;
        return "Movie{name = " + text + "}";
    }
}
