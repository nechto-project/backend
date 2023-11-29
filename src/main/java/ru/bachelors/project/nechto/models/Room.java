package ru.bachelors.project.nechto.models;


import java.io.Serializable;
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
    @Column(name = "is_join")
    boolean isJoin;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "room_movie",
            joinColumns = @JoinColumn(name = "id_session"),
            inverseJoinColumns = @JoinColumn(name = "id_movie")
    )
    private List<Movie> movies = new ArrayList<>();
    @Lob
    @Column(name = "leader_movies")
    private Serializable leaderAnswers;
    @Lob
    @Column(name = "participant_movies")
    private Serializable participantAnswers;
    public Room(String sessionId, List<Movie> movies) {
        this.sessionId = sessionId;
        this.movies.addAll(movies);
        this.isJoin = false;
        this.leaderAnswers = new ArrayList<Answer>();
        this.participantAnswers = new ArrayList<Answer>();
    }

    public ArrayList<Answer> getAnswers(boolean isLeader) {
        if(isLeader) {
            return (ArrayList<Answer>) leaderAnswers;
        }
        return (ArrayList<Answer>) participantAnswers;
    }

    public void setAnswer(ArrayList<Answer> answers, boolean isLeader) {
        if (isLeader) {
            this.leaderAnswers = answers;
        }
        else {
            this.participantAnswers = answers;
        }
    }

}
