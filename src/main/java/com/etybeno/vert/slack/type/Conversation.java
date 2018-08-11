package com.etybeno.vert.slack.type;

import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 10/08/2018.
 */
public class Conversation {

    private final JsonObject json;

    public Conversation(JsonObject object) {
        this.json = object;
    }
}
