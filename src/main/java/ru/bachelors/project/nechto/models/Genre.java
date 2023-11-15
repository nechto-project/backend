package ru.bachelors.project.nechto.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name="genre")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre")
    private int genreId;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies = new ArrayList<>();
}
