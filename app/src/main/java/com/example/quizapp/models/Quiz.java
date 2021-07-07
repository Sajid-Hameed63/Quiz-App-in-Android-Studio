package com.example.quizapp.models;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Quiz {
    String id = "";
    String title = "";
    Map<String, Question> question = new HashMap<>();

    public Quiz(){

    }
    public Quiz(String id, String title, Map<String, Question> question) {
        this.id = id;
        this.title = title;
        this.question = question;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", question=" + question +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Question> getQuestion() {
        return question;
    }

    public void setQuestion(Map<String, Question> question) {
        this.question = question;
    }
}