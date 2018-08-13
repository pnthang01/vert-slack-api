package com.etybeno.vert.test;

import com.etybeno.vert.slack.SlackClient;
import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Multi;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * Created by thangpham on 07/08/2018.
 */
public class ClientTest extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ClientTest());
    }

    @Override
    public void start() throws Exception {
        SlackClient slackClient = SlackClient.create(vertx, new SlackOptions().setToken("xoxb-331363693952-411370999504-E86xMEfpTn4k5hrUeBk1TzUf"));
        slackClient.rtmConnect(null, null, event -> {
            if(event.succeeded()) {
                System.out.println(event.result());
                System.out.println("called");
            } else System.out.println(event.cause().getMessage());
        });
    }
}