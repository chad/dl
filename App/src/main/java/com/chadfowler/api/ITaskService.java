package com.chadfowler.api;

import java.util.Date;


public interface ITaskService {
    public boolean addWithReminder(String title, Date reminder);
}
