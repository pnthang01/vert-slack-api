package com.etybeno.vert.slack.resp;

import com.etybeno.vert.slack.SlackType;
import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 17/08/2018.
 */
public class RtmMessageResponse extends SlackType {


    public RtmMessageResponse(JsonObject jsonObject) {
        super(jsonObject);
    }

    public String replyTo() {
        return json.getString("reply_to");
    }

    public String getTS() {
        return json.getString("ts");
    }

    public String text() {
        return json.getString("text");
    }

    public String getThreadTs() {
        return json.getString("thread_ts");
    }
}
