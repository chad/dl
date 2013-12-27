package com.chadfowler.data;

import java.util.Date;

public class Task {
    public String title;
    public Date remindAt;
    public String id;

    public Task(String title, Date remindAt) {
        this.title = title;
        this.remindAt = remindAt;
    }
}
