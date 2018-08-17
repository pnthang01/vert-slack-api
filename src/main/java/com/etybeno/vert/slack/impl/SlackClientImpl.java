package com.etybeno.vert.slack.impl;

import com.etybeno.vert.slack.*;
import com.etybeno.vert.slack.type.Bot;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

/**
 * Created by thangpham on 07/08/2018.
 */
public class SlackClientImpl implements SlackClient {

    private Vertx vertx;
    private SlackOptions options;


    private WebClient webClient;
    private HttpClient httpClient;

    private SlackChannels channels;
    private SlackConversations conversations;
    private SlackChat chat;
    private SlackReminders reminders;
    private SlackUsers users;
    private SlackRtm rtm;

    public SlackClientImpl(Vertx vertx, SlackOptions options) {
        this.options = new SlackOptions(options);
        httpClient = vertx.createHttpClient(options);
        webClient = WebClient.wrap(httpClient);
        this.vertx = vertx;
    }

    @Override
    public synchronized SlackChannels channels() {
        if (null == channels) channels = new SlackChannelsImpl(vertx, options);
        return channels;
    }

    @Override
    public synchronized SlackConversations conversations() {
        if (null == conversations) conversations = new SlackConversationsImpl(vertx, options);
        return conversations;
    }

    @Override
    public synchronized SlackChat chat() {
        if (null == chat) chat = new SlackChatImpl(vertx, options);
        return chat;
    }

    @Override
    public synchronized SlackReminders reminders() {
        if (null == reminders) reminders = new SlackRemindersImpl(vertx, options);
        return reminders;
    }

    @Override
    public synchronized SlackUsers users() {
        if(null == users) users = new SlackUsersImpl(vertx, options);
        return users;
    }

    @Override
    public synchronized SlackRtm rtm() {
        if(null == rtm) rtm = new SlackRtmImpl(vertx, options);
        return rtm;
    }

    @Override
    public void rtmConnect(Boolean batchPresenceAware, Boolean presenceSub, Handler<AsyncResult<JsonObject>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.RTM.RTM_CONNET);
        req.setQueryParam("token", options.getToken());
        if (null != batchPresenceAware) req.setQueryParam("batch_presence_aware", batchPresenceAware.toString());
        if (null != presenceSub) req.setQueryParam("presence_sub", presenceSub.toString());
        sendAndReceiveFullObject(req, handler);
    }

    @Override
    public void rtmStart(Boolean batchPresenceAware, Boolean includeLocale, Boolean mpimAware, Integer noLatest,
                         Boolean noUnreads, Boolean presenceSub, Boolean simpleLatest, Handler<AsyncResult<JsonObject>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.RTM.RTM_CONNET);
        req.setQueryParam("token", options.getToken());
        if (null != batchPresenceAware) req.setQueryParam("batch_presence_aware", batchPresenceAware.toString());
        if (null != includeLocale) req.setQueryParam("include_locale", includeLocale.toString());
        if (null != mpimAware) req.setQueryParam("mpim_aware", mpimAware.toString());
        if (null != noLatest) req.setQueryParam("no_latest", noLatest.toString());
        if (null != noUnreads) req.setQueryParam("no_unreads", noUnreads.toString());
        if (null != presenceSub) req.setQueryParam("presence_sub", presenceSub.toString());
        if (null != simpleLatest) req.setQueryParam("simple_latest", simpleLatest.toString());
        sendAndReceiveFullObject(req, handler);
    }

    @Override
    public void botInfo(String botId, Handler<AsyncResult<Bot>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Bot.BOT_INFO);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("bot", botId);
        sendAndReceiveOne(req, "bot", handler, Bot.class);
    }

}
