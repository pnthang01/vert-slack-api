package com.etybeno.vert.slack;

import com.etybeno.vert.slack.impl.SlackClientImpl;
import io.vertx.core.Vertx;

/**
 * Created by thangpham on 07/08/2018.
 */
public interface SlackClient {

    static SlackClient create(Vertx vertx, SlackOptions options) {
        return new SlackClientImpl(vertx, options);
    }

    SlackChannels channels();

    SlackConversations conversations();

}
