package com.chadfowler.data;

import java.util.Date;

public class Task {
    public String title;
    public Date remindAt;
    public String id;
    public int listId;

    public Task(String title, Date remindAt, int listId) {
        this.title = title;
        this.remindAt = remindAt;
        this.listId = listId;
    }

    public Task(String title, Date remindAt) {
        this.title = title;
        this.remindAt = remindAt;
    }
}
