package com.chadfowler.api;

import java.util.Date;


public interface ITaskService {
    public void addWithReminder(int userId, String title, Date reminder);
}
