package ru.bachelors.project.nechto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Table(name = "movie_genre")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CompositePK.class)
public class MovieGenre {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_movie")
    private Movie movie;
    @Id
    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;

}

class CompositePK implements Serializable {
    private Genre genre;
    private Movie movie;
}