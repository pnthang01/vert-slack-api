package com.etybeno.vert.slack;

import com.etybeno.vert.slack.type.Message;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by thangpham on 11/08/2018.
 */
public interface SlackChat extends SlackWebApi {

    void delete(String channelId, String ts, Boolean asUser, Handler<AsyncResult<Void>> handler);

    void getPermalink(String channelId, String ts, Handler<AsyncResult<String>> handler);

    void meMessage(String channelId, String text, Handler<AsyncResult<String>> handler);

    void postEphemeral(String channelId, String text, String userId, Boolean asUser, String attachments,
                       Boolean linkNames, String parse, Handler<AsyncResult<String>> handler);

    void postMessage(String channelId, String text, Boolean asUser, String attachments, String iconEmoji,
                     String iconUrl, Boolean linkNames, Boolean mrkdwn, String parse, Boolean replyBroadcast,
                     String threadTs, Boolean urfurlLinks, Boolean unfurlMedia, String username,
                     Handler<AsyncResult<Message>> handler);

    void unfurl();

    void update();


}
