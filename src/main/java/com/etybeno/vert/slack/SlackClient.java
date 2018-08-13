package com.etybeno.vert.slack;

import com.etybeno.vert.slack.impl.SlackClientImpl;
import com.etybeno.vert.slack.type.Bot;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 07/08/2018.
 */
public interface SlackClient extends AbstractSlack {

    static SlackClient create(Vertx vertx, SlackOptions options) {
        return new SlackClientImpl(vertx, options);
    }

    SlackChannels channels();

    SlackConversations conversations();

    SlackChat chat();

    SlackReminders reminders();

    SlackUsers users();

    void rtmConnect(Boolean batchPresenceAware, Boolean presenceSub, Handler<AsyncResult<JsonObject>> handler);

    void rtmStart(Boolean batchPresenceAware, Boolean includeLocale, Boolean mpimAware, Integer noLatest,
                         Boolean noUnreads, Boolean presenceSub, Boolean simpleLatest, Handler<AsyncResult<JsonObject>> handler);

    void botInfo(String botId, Handler<AsyncResult<Bot>> handler);
}
