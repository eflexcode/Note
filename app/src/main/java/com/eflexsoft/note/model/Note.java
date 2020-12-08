package com.eflexsoft.note.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String subject;
    private String body;
    private String date;
    private int priority;

    public Note(String subject, String body, String date, int priority) {
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.priority = priority;
    }

    public String getSubject() {
        return subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }


}
