package com.etybeno.vert.slack.type;

import com.etybeno.vert.slack.SlackType;
import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 07/08/2018.
 */
public class User extends SlackType {


    public User(JsonObject jsonObject) {
        super(jsonObject);
    }

    public String getId() {
        return json.getString("id");
    }

    public String getTeamId() {
        return json.getString("team_id");
    }

    public String getName() {
        return json.getString("name");
    }

    public JsonObject getProfile() {
        return json.getJsonObject("profile");
    }

    private boolean isAdmin() {
        return Boolean.TRUE.equals(json.getBoolean("is_admin"));
    }

    public boolean getHas2fa() {
        return Boolean.TRUE.equals(json.getBoolean("has2fa"));
    }

    public String getTwoFactoryType() {
        return json.getString("two_factor_type");
    }

    public boolean getHasFiles() {
        return Boolean.TRUE.equals(json.getBoolean("has_files"));
    }

    public JsonObject toJson() {
        return json.copy();
    }
}
