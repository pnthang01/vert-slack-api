package com.etybeno.vert.slack;

import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 11/08/2018.
 */
public class SlackType {

    protected final JsonObject json;

    protected SlackType(JsonObject jsonObject) {
        this.json = jsonObject;
    }

    @Override
    public String toString() {
        return json.toString();
    }
}
