package com.chadfowler.api;

import java.util.Date;


public interface ITaskService {
    public void addWithReminder(String title, Date reminder);
}
