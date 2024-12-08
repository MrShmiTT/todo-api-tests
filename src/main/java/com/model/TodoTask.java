package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.utils.UtilJava.generateTaskDescription;
import static com.utils.UtilJava.generateTaskId;

public class TodoTask {

    private Integer id;

    @JsonProperty("text")
    private String text;

    private Boolean completed;

    public TodoTask() {}

    public TodoTask(Integer id, String text, Boolean completed) {
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public TodoTask id(Integer id) {
        this.id = id;
        return this;
    }

    public TodoTask description(String text) {
        this.text = text;
        return this;
    }

    public TodoTask completed(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public static TodoTask getTodoTask() {
        return new TodoTask().id(generateTaskId()).description(generateTaskDescription()).completed(false);
    }

    @Override
    public String toString() {
        return
            "{\"id\": " + id +
            ", \"text\": \"" + text + "\"" +
            ", \"completed\": " + completed +
            "}";
    }
}
