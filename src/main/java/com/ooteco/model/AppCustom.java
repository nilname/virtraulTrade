package com.ooteco.model;

public class AppCustom {
    private Integer id;

    private String question;

    private Long inputtime;

    private String answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public Long getInputtime() {
        return inputtime;
    }

    public void setInputtime(Long inputtime) {
        this.inputtime = inputtime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }
}