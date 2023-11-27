package ru.bachelors.project.nechto.models;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @Column(name = "id_session")
    String sessionId;
    @Column(name = "leader")
    Long leader;
    @Column(name = "participant")
    Long participant;
    @Column(name = "movie_filters")
    String movieFilters;
    @ManyToMany()
    @JoinTable(
            name = "room_movie",
            joinColumns = @JoinColumn(name = "id_session"),
            inverseJoinColumns = @JoinColumn(name = "id_movie")
    )
    private List<Movie> movies = new ArrayList<>();

    public Room(String sessionId, Long leader) {
        this.sessionId = sessionId;
        this.leader = leader;
    }
}
