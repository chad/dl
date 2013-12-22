package com.chadfowler.data;

import org.json.JSONException;
import org.json.JSONObject;


public class User {

    private final String email;
    private final String accessToken;

    public User(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

    public static User fromJSON(String json) throws UserConstructionException {

        try {
            JSONObject userData = parseJSON(json);
            String email = userData.getString("email");
            String accessToken = userData.getString("access_token");
            return new User(email, accessToken);
        } catch (JSONException e) {
            throw new UserConstructionException(e.getMessage());
        }
    }

    private static JSONObject parseJSON(String json) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static class UserConstructionException extends Exception {

        public UserConstructionException(String message) {
            super(message);
        }
    }
}
