package ru.bachelors.project.nechto.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
