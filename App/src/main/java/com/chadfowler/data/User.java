package com.chadfowler.data;

import org.json.JSONException;
import org.json.JSONObject;


public class User {

    public final int id;
    public final String email;
    public final String accessToken;

    public User(Integer id, String email, String accessToken) {
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static User fromJSON(String json) throws UserConstructionException {

        try {
            JSONObject userData = parseJSON(json);
            String email = userData.getString("email");
            String accessToken = userData.getString("access_token");
            Integer id = userData.getInt("id");
            return new User(id, email, accessToken);
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
