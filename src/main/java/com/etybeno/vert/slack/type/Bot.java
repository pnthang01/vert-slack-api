package com.etybeno.vert.slack.type;

import com.etybeno.vert.slack.SlackType;
import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 13/08/2018.
 */
public class Bot extends SlackType {

    public Bot(JsonObject jsonObject) {
        super(jsonObject);
    }

    public String getId() {
        return json.getString("id");
    }

    public Boolean getDeleted() {
        return json.getBoolean("deleted", false);
    }

    public String getName() {
        return json.getString("name");
    }

    public Long getUpdated() {
        return json.getLong("updated");
    }

    public String getAppId() {
        return json.getString("app_id");
    }
}
