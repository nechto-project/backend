package ru.bachelors.project.nechto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_director")
    private Long directorId;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "directors")
    private List<Movie> movies = new ArrayList<>();

    public Director(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String text = name == null ? "null" : name;
        return "Director{name = " + text + "}";
    }
}
