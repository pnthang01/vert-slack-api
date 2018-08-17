package com.etybeno.vert.slack;

import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 17/08/2018.
 */
public class SlackError extends RuntimeException {

    private final int code;

    public SlackError(JsonObject error) {
        this(error.getInteger("code"), error.getString("msg"));
    }

    public SlackError(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
