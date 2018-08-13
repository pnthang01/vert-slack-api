package com.etybeno.vert.slack.type;

import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 10/08/2018.
 */
public class Conversation extends SlackType{

    public Conversation(JsonObject jsonObject) {
        super(jsonObject);
    }

    public String getId() {
        return json.getString("id");
    }

    public String getName() {
        return json.getString("name");
    }

    public Boolean isChannel() {
        return json.getBoolean("is_channel", false);
    }

    public Boolean isGroup () {
        return json.getBoolean("is_group", false);
    }

    public Boolean isIM() {
        return json.getBoolean("is_im", false);
    }

    public Long getCreated() {
        return json.getLong("created");
    }

    public String getCreator() {
        return json.getString("creator");
    }

    public Boolean isArchived() {
        return json.getBoolean("is_archived", false);
    }

    public Boolean isGeneral() {
        return json.getBoolean("is_general", false);
    }


}
