package com.etybeno.vert.test;

import com.etybeno.vert.slack.SlackClient;
import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Multi;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

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
        SlackClient slackClient = SlackClient.create(vertx, new SlackOptions().setToken("xoxb-320661762674-416230606502-nkOUwy4wCgJumbzF7cKqQqw6"));
        slackClient.channels().list(null, false, false, null, listRes -> {
            if(listRes.succeeded()) {
                Multi<Channel> result = listRes.result();
                try {
                    result.getDataList().stream().forEach(channel -> System.out.println(channel.getId() + ": " + channel.getName()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(result.getNextCursor());
            } else listRes.cause().printStackTrace();
        });
    }
}