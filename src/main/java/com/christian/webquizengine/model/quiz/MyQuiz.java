package com.christian.webquizengine.model.quiz;

import com.christian.webquizengine.model.abs.AbstractQuizEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Quiz")
@NoArgsConstructor
@Getter
@Setter
public class MyQuiz extends AbstractQuizEntity {

    @NotBlank(message = "Quiz should have a title.")
    private String title;
    @NotBlank(message = "Quiz should have a text.")
    private String text;
    private String options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String answer = "";
    @JsonIgnore
    private String authentication;

    public MyQuiz(String title, String text, List<String> options, List<Integer> answer, String authentication) {
        setVersion(1);
        setAuthentication(authentication);
        setTitle(title);
        setText(text);
        setOptions(options);
        setAnswer(answer);
    }

    public MyQuiz(QuizData quizData, String authentication) {
        setVersion(1);
        setAuthentication(authentication);
        setTitle(quizData.getTitle());
        setText(quizData.getText());
        setOptions(quizData.getOptions());
        setAnswer(quizData.getAnswer());
    }

    public List<String> getOptions() {
        return List.of(options.split(","));
    }

    public List<Integer> getAnswer() {
        if(answer.isBlank()) return null;
        return Arrays.stream(answer.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public void setOptions(List<String> options) {
        // We know that there are at least 2 options so we can hardcode that.
        StringBuilder buildOption = new StringBuilder(options.get(0) + "," + options.get(1));
        // For any additional options...
        if (options.size() > 2) {
            for(int i = 2; i < options.size(); i++) {
                buildOption.append(",");
                buildOption.append(options.get(i));
            }
        }
        this.options = buildOption.toString();
    }

    public void setAnswer(List<Integer> answer) {
        if (answer.isEmpty()) return;
        List<String> answerStrings = answer.stream().map(String::valueOf).collect(Collectors.toList());
        StringBuilder buildAnswer = new StringBuilder(answerStrings.get(0));
        if (answerStrings.size() > 1) {
            for(int i = 1; i < answerStrings.size(); i++) {
                buildAnswer.append(",");
                buildAnswer.append(answerStrings.get(i));
            }
        }
        System.out.println(buildAnswer);
    }

    @JsonIgnore
    public String getOptionsAsCSV() {
        return this.options;
    }

    @JsonIgnore
    public String getAnswerAsCSV() {
        return this.answer;
    }
}
