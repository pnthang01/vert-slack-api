package com.etybeno.vert.slack.type;

import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 10/08/2018.
 */
public class Message extends SlackType {

    public Message(JsonObject jsonObject) {
        super(jsonObject);
    }

    public String getType() {
        return json.getString("type");
    }

    public String getUserId() {
        return json.getString("user");
    }

    public String getText() {
        return json.getString("text");
    }

    public String getTs() {
        return json.getString("ts");
    }
}
