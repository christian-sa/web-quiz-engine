package com.christian.webquizengine.model.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "Quiz")
@NoArgsConstructor
@Getter
@Setter
public class Quiz {

    @Id
//    @GenericGenerator(name = "QuizIdGenerator", strategy = "engine.api.model.QuizIdGenerator")
//    @GeneratedValue(generator = "QuizIdGenerator")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private long id;
    @NotBlank(message = "Quiz should have a title.")
    private String title;
    @NotBlank(message = "Quiz should have a text.")
    private String text;
    @Size(min = 2, message = "Quiz should contain at least 2 options.")
    @NotNull
    @ElementCollection
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    private List<Integer> answer;
    @JsonIgnore
    private String authentication;
}
