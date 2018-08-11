package com.etybeno.vert.slack.impl;

import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.SlackReminders;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.client.WebClient;

/**
 * Created by thangpham on 11/08/2018.
 */
public class SlackRemindersImpl implements SlackReminders {

    private SlackOptions options;
    private Vertx vertx;
    private WebClient webClient;
    private HttpClient httpClient;

    public SlackRemindersImpl(Vertx vertx, SlackOptions slackOptions) {
        this.options = new SlackOptions(slackOptions);
        httpClient = vertx.createHttpClient(slackOptions);
        webClient = WebClient.wrap(httpClient);
        this.vertx = vertx;
    }

    @Override
    public void add() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void info() {

    }

    @Override
    public void list() {

    }
}
