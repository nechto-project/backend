package ru.bachelors.project.nechto.models;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    public Room(String sessionId, Long leader) {
        this.sessionId = sessionId;
        this.leader = leader;
    }
}
