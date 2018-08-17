package com.etybeno.vert.slack;

import com.etybeno.vert.slack.impl.SlackRtmImpl;
import com.etybeno.vert.slack.resp.RtmMessageResponse;
import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Message;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 13/08/2018.
 */
public interface SlackRtm {

    void start(JsonObject startApiResp, Handler<AsyncResult<Void>> handler);

    SlackRtm messageHandler(Handler<Message> handler);

    SlackRtmImpl send(Channel ch, String text);

    SlackRtmImpl send(Channel ch, String text, Handler<AsyncResult<RtmMessageResponse>> handler);

    SlackRtmImpl sendToThread(Channel ch, String threadTs, String text);

    SlackRtmImpl sendToThread(Channel ch, String threadTs, String text, Handler<AsyncResult<RtmMessageResponse>> handler);

    void close();
}