package com.etybeno.vert.slack.impl;

import com.etybeno.vert.slack.ApiConstant;
import com.etybeno.vert.slack.SlackConversations;
import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Message;
import com.etybeno.vert.slack.type.Multi;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thangpham on 10/08/2018.
 */
public class SlackConversationsImpl implements SlackConversations {

    private SlackOptions options;
    private Vertx vertx;
    private WebClient webClient;
    private HttpClient httpClient;

    public SlackConversationsImpl(Vertx vertx, SlackOptions slackOptions) {
        this.options = new SlackOptions(slackOptions);
        httpClient = vertx.createHttpClient(slackOptions);
        webClient = WebClient.wrap(httpClient);
        this.vertx = vertx;
    }

    @Override
    public void archive(String conversationId, Handler<AsyncResult<Void>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Conversations.ARCHIVE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", conversationId);
        sendAndReceiveVoid(req, handler);
    }

    @Override
    public void close(String conversationId, Handler<AsyncResult<Void>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Conversations.CLOSE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", conversationId);
        sendAndReceiveVoid(req, handler);
    }

    @Override
    public void create(String name, Boolean isPrivate, List<String> userIds, Handler<AsyncResult<Channel>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Conversations.CREATE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("name", name);
        if (null != isPrivate) req.setQueryParam("is_private", isPrivate.toString());
        req.setQueryParam("user_ids", userIds.stream().collect(Collectors.joining(",")));
        sendAndReceiveOne(req, "channel", handler, Channel.class);
    }

    @Override
    public void history(String channelId, String cursor, Boolean inclusive, Long latest, Integer limit, Long oldest,
                        Handler<AsyncResult<Message>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Conversations.CREATE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("channel", channelId);
        if (StringUtil.isNullOrEmpty(cursor)) req.setQueryParam("cursor", cursor);
        if (null != inclusive) req.setQueryParam("inclusive", inclusive.toString());
        if (null != latest) req.setQueryParam("latest", latest.toString());
        if (null != limit) req.setQueryParam("limit", limit.toString());
        if (null != oldest) req.setQueryParam("oldest", oldest.toString());

    }

    @Override
    public void info() {

    }

    @Override
    public void invite() {

    }

    @Override
    public void join() {

    }

    @Override
    public void kick() {

    }

    @Override
    public void leave() {

    }

    @Override
    public void list(String cursor, Boolean excludeArchived, Boolean excludeMembers, Integer limit,
                     Handler<AsyncResult<Multi<Channel>>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Conversations.LIST);
        if (!StringUtil.isNullOrEmpty(cursor)) req.setQueryParam("cursor", cursor);
        if (null != excludeArchived) req.setQueryParam("exclude_archived", excludeArchived.toString());
        if (null != excludeMembers) req.setQueryParam("exclude_members", excludeMembers.toString());
        if (null != limit) req.setQueryParam("limit", limit.toString());
        sendAnReceivedMulti(req, "channels", handler, Channel.class);
    }

    @Override
    public void members() {

    }

    @Override
    public void open() {

    }

    @Override
    public void rename() {

    }

    @Override
    public void replies() {

    }

    @Override
    public void setPurpose() {

    }

    @Override
    public void setTopic() {

    }

    @Override
    public void unarchive() {

    }

}
