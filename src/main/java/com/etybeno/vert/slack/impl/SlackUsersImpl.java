package com.etybeno.vert.slack.impl;

import com.etybeno.vert.slack.ApiConstant;
import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.SlackUsers;
import com.etybeno.vert.slack.type.Conversation;
import com.etybeno.vert.slack.type.Multi;
import com.etybeno.vert.slack.type.User;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thangpham on 13/08/2018.
 */
public class SlackUsersImpl implements SlackUsers {

    private SlackOptions options;
    private Vertx vertx;
    private WebClient webClient;
    private HttpClient httpClient;

    public SlackUsersImpl(Vertx vertx, SlackOptions slackOptions) {
        this.options = new SlackOptions(slackOptions);
        httpClient = vertx.createHttpClient(slackOptions);
        webClient = WebClient.wrap(httpClient);
        this.vertx = vertx;
    }


    @Override
    public void conversations(String cursor, Boolean excludeArchived, Integer limit, List<String> types, String userId,
                              Handler<AsyncResult<Multi<Conversation>>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Users.CONVERSATIONS);
        req.setQueryParam("token", options.getToken());
        if (!StringUtil.isNullOrEmpty(cursor)) req.setQueryParam("cursor", cursor);
        if (null != excludeArchived) req.setQueryParam("exclude_archived", excludeArchived.toString());
        if (null != limit) req.setQueryParam("limit", limit.toString());
        if (null != types && !types.isEmpty())
            req.setQueryParam("types", types.stream().collect(Collectors.joining(",")));
        if (!StringUtil.isNullOrEmpty(userId)) req.setQueryParam("user", userId);
        sendAndReceivedMulti(req, "channels", handler, Conversation.class);
    }

    @Override
    public void deletePhoto(Handler<AsyncResult<Void>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Users.DELETE_PHOTO);
        req.setQueryParam("token", options.getToken());
        sendAndReceiveVoid(req, handler);
    }

    @Override
    public void getPresence(String userId, Handler<AsyncResult<JsonObject>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Users.GET_PRESENCE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("user", userId);
        sendAndReceiveFullObject(req, handler);
    }

    @Override
    public void identity(Handler<AsyncResult<JsonObject>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Users.IDENTIFY);
        req.setQueryParam("token", options.getToken());
        sendAndReceiveFullObject(req, handler);
    }

    @Override
    public void info(String userId, Boolean includeLocale, Handler<AsyncResult<User>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Users.INFO);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("user", userId);
        if(null != includeLocale) req.setQueryParam("include_locale", includeLocale.toString());
        sendAndReceiveOne(req, "user", handler, User.class);
    }

    @Override
    public void list(String cursor, Boolean includeLocale, Integer limit, Boolean presence, Handler<AsyncResult<Multi<User>>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Users.LIST);
        req.setQueryParam("token", options.getToken());
        if(!StringUtil.isNullOrEmpty(cursor)) req.setQueryParam("cursor", cursor);
        if(null != includeLocale) req.setQueryParam("include_locale", includeLocale.toString());
        if(null != limit) req.setQueryParam("limit", limit.toString());
        if(null != presence) req.setQueryParam("presence", presence.toString());
        sendAndReceivedMulti(req, "members", handler, User.class);
    }

    @Override
    public void lookupByEmail(String email, Handler<AsyncResult<User>> handler) {
        HttpRequest<Buffer> req = webClient.get(ApiConstant.Users.LOOKUP_BY_EMAIL);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("email", email);
        sendAndReceiveOne(req, "user", handler, User.class);
    }

    @Override
    public void setActive(Handler<AsyncResult<Void>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Users.SET_ACTIVE);
        req.setQueryParam("token", options.getToken());
        sendAndReceiveVoid(req, handler);
    }

    @Override
    public void setPhoto(File image, Integer cropW, Integer cropX, Integer cropY, Handler<AsyncResult<Void>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Users.SET_PHOTO);
        req.setQueryParam("token", options.getToken());
        //
        if(null != cropW) req.setQueryParam("crop_w", cropW.toString());
        if(null != cropX) req.setQueryParam("crop_x", cropX.toString());
        if(null != cropY) req.setQueryParam("crop_y", cropY.toString());
        sendAndReceiveVoid(req, handler);
    }

    @Override
    public void setPresence(String status, Handler<AsyncResult<Void>> handler) {
        HttpRequest<Buffer> req = webClient.post(ApiConstant.Users.SET_PRESENCE);
        req.setQueryParam("token", options.getToken());
        req.setQueryParam("presence", status);
        sendAndReceiveVoid(req, handler);
    }
}
