package com.chadfowler.api;

import com.chadfowler.data.User;


public interface ILoginService {
    public User login(String email, String password) throws User.UserConstructionException;
}
