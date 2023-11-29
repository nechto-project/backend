package ru.bachelors.project.nechto.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.bachelors.project.nechto.dto.AnswerDto;

import java.io.Serializable;
@AllArgsConstructor
public class Answer implements Serializable {
    @Getter
    private int idMovie;
    private boolean answer;

    public Answer (AnswerDto answerDto) {
        this.idMovie = answerDto.idMovie();
        this.answer = answerDto.answer();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer other = (Answer) o;
        return idMovie == other.idMovie && answer == other.answer;
    }
}
