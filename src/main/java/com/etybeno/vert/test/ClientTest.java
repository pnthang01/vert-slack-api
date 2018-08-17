package com.etybeno.vert.test;

import com.etybeno.vert.slack.SlackClient;
import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.SlackRtm;
import com.etybeno.vert.slack.resp.RtmMessageResponse;
import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Multi;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.Scanner;

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
        SlackRtm rtm = slackClient.rtm();
        rtm.messageHandler(event -> {
            System.out.println(event.toString());
            System.out.println(String.format("User %s just sent '%s' at %s", event.getUserId(), event.getText(), event.getTs()));
        });

        slackClient.rtmConnect(null, null, event -> {
            if(event.succeeded()) {
                System.out.println(event.result());
                JsonObject result = event.result();
//                JsonObject needNotInit = new JsonObject().put("url", "wss://cerberus-xxxx.lb.slack-msgs.com/websocket/Ggf4P-RjWMb5wDh0DPkm9fvCeZl6ihBBIo1AE0-cyRBmeXV9tCMD7cC5OyTUgWk4H0AOtzF6Nznm1Y_UDBWyeZQN-8Q-ZV-Db68CL792VqA=");
                rtm.start(result, connectRes -> {
                    if(connectRes.succeeded()) {
                        System.out.println("Connected");


                        Channel channel = new Channel(new JsonObject().put("id", "C9S0L0N8J"));
                        rtm.send(channel, "Hello and start thread", event1 -> {
                            System.out.println("Should send 1");
                            if(event1.succeeded()) {
                                RtmMessageResponse result1 = event1.result();
                                System.out.println(result1.toString());
                                rtm.sendToThread(channel, result1.getTS(), "Reply to the new thread", event2 -> {
                                    System.out.println("Should send 2");
                                    if(event2.succeeded()) {
                                        System.out.println(event2.result().toString());
                                    } else event2.cause().printStackTrace();
                                });
                            } else event1.cause().printStackTrace();
                        });
                    } else connectRes.cause().printStackTrace();
                });
                System.out.println("called");
            } else System.out.println(event.cause().getMessage());
        });

    }
}