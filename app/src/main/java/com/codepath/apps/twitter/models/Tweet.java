package com.codepath.apps.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by toan on 3/27/2016.
 */
public class Tweet {
    private String body;
    private long uid;
    private User user;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public Tweet fromJson(JSONObject jsonObject) {
        Tweet t = new Tweet();
        try {
            t.body = jsonObject.getString("text");
            t.uid = jsonObject.getLong("id");
            t.createdAt = jsonObject.getString("created_at");
            t.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return t;
    }
}
