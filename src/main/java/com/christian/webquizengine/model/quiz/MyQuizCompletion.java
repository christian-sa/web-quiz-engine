package com.christian.webquizengine.model.quiz;
import com.christian.webquizengine.model.abs.AbstractQuizEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "QuizCompletion")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"id", "version", "updatedAt"})
public class MyQuizCompletion extends AbstractQuizEntity {

    @JsonIgnore
    @NotNull
    private String authentication;
    private long quiz_id;

    public MyQuizCompletion(String authentication, long id) {
        this.authentication = authentication;
        this.quiz_id = id;
    }
}

