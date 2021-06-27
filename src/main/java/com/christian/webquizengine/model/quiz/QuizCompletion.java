package com.christian.webquizengine.model.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "QuizCompletion")
@NoArgsConstructor
@Getter
@Setter
public class QuizCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long quizCompletionID;
    @NotNull
    private long id;
    @NotNull
    private String completedAt;
    @JsonIgnore
    @NotNull
    private String authentication;

    public QuizCompletion(long id, String completedAt, String authentication) {
        this.id = id;
        this.completedAt = completedAt;
        this.authentication = authentication;
    }
}
