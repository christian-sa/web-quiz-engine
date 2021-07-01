package com.christian.webquizengine.model.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizData {

    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @Size(min = 2)
    private List<String> options;
    private List<Integer> answer;
}
