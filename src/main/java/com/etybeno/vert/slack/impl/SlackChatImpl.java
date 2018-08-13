package com.etybeno.vert.slack.impl;

import com.etybeno.vert.slack.ApiConstant;
import com.etybeno.vert.slack.SlackChat;
import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.type.Message;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

/**
 * Created by thangpham on 11/08/2018.
 */
public class SlackChatImpl implements SlackChat {

    private SlackOptions options;
    private Vertx vertx;
    private WebClient webClient;
    private HttpClient httpClient;

    public SlackChatImpl(Vertx vertx, SlackOptions slackOptions) {
        this.options = new SlackOptions(slackOptions);
        httpClient = vertx.createHttpClient(slackOptions);
        webClient = WebClient.wrap(httpClient);
        this.vertx = vertx;
    }

    @Override
    public void delete(String channelId, String ts, Boolean asUser, Handler<AsyncResult<Void>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Chat.DELETE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", channelId);
        req.setQueryParam("ts", ts);
        if(null != asUser) req.setQueryParam("as_user", asUser.toString());
        sendAndReceiveVoid(req, handler);
    }

    @Override
    public void getPermalink(String channelId, String ts, Handler<AsyncResult<String>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Chat.GET_PERMALINK);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", channelId);
        req.setQueryParam("message_ts", ts);
        sendAndReceivePrimitive(req, "permalink", handler, String.class);
    }

    @Override
    public void meMessage(String channelId, String text, Handler<AsyncResult<String>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Chat.POST_EPHEMERAL);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", channelId);
        req.setQueryParam("text", text);
        sendAndReceivePrimitive(req, "message_ts", handler, String.class);
    }

    @Override
    public void postEphemeral(String channelId, String text, String userId, Boolean asUser, String attachments,
                              Boolean linkNames, String parse, Handler<AsyncResult<String>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Chat.POST_EPHEMERAL);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", channelId);
        req.setQueryParam("text", text);
        req.setQueryParam("user", userId);
        if(null != asUser) req.setQueryParam("as_user", asUser.toString());
        if(null != attachments) req.setQueryParam("attachments", attachments);
        if(null != linkNames) req.setQueryParam("link_names", linkNames.toString());
        if(!StringUtil.isNullOrEmpty(parse)) req.setQueryParam("parse", parse);
        sendAndReceivePrimitive(req, "message_ts", handler, String.class);
    }

    @Override
    public void postMessage(String channelId, String text, Boolean asUser, String attachments, String iconEmoji,
                            String iconUrl, Boolean linkNames, Boolean mrkdwn, String parse, Boolean replyBroadcast,
                            String threadTs, Boolean urfurlLinks, Boolean unfurlMedia, String username,
                            Handler<AsyncResult<Message>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Chat.POST_MESSAGE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", channelId);
        req.setQueryParam("text", text);
        if(null != asUser) req.setQueryParam("as_user", asUser.toString());
        if(null != attachments) req.setQueryParam("attachments", attachments);
        if(!StringUtil.isNullOrEmpty(iconEmoji)) req.setQueryParam("icon_emoji", iconEmoji);
        if(!StringUtil.isNullOrEmpty(iconUrl)) req.setQueryParam("icon_url", iconUrl);
        if(null != linkNames) req.setQueryParam("link_names", linkNames.toString());
        if(null != mrkdwn) req.setQueryParam("mrkdwn", mrkdwn.toString());
        if(!StringUtil.isNullOrEmpty(parse)) req.setQueryParam("parse", parse);
        if(null != replyBroadcast) req.setQueryParam("reply_broadcast", replyBroadcast.toString());
        if(!StringUtil.isNullOrEmpty(threadTs)) req.setQueryParam("thread_ts", threadTs);
        if(null != urfurlLinks) req.setQueryParam("unfurl_links", urfurlLinks.toString());
        if(null != unfurlMedia) req.setQueryParam("unfurl_media", unfurlMedia.toString());
        if(!StringUtil.isNullOrEmpty(username)) req.setQueryParam("username", username);
        sendAndReceiveOne(req, "message", handler, Message.class);
    }

    @Override
    public void unfurl() {

    }

    @Override
    public void update() {

    }
}
