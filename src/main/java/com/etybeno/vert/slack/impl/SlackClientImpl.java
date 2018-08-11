package com.etybeno.vert.slack.impl;

import com.etybeno.vert.slack.SlackChannels;
import com.etybeno.vert.slack.SlackClient;
import com.etybeno.vert.slack.SlackConversations;
import com.etybeno.vert.slack.SlackOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 07/08/2018.
 */
public class SlackClientImpl implements SlackClient {

    private Vertx vertx;
    private SlackOptions options;

    private SlackChannels channels;
    private SlackConversations conversations;

    public SlackClientImpl(Vertx vertx, SlackOptions options) {
        this.options = new SlackOptions(options);
        this.vertx = vertx;
    }

    @Override
    public synchronized SlackChannels channels() {
        if(null == channels) channels = new SlackChannelsImpl(vertx, options);
        return channels;
    }

    @Override
    public synchronized SlackConversations conversations() {
        if(null == conversations) conversations = new SlackConversationsImpl(vertx, options);
        return conversations;
    }

}
