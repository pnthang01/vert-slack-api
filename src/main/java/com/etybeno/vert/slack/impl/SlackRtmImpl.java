package com.etybeno.vert.slack.impl;

import com.etybeno.vert.slack.SlackError;
import com.etybeno.vert.slack.SlackOptions;
import com.etybeno.vert.slack.SlackRtm;
import com.etybeno.vert.slack.resp.RtmMessageResponse;
import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Message;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by thangpham on 13/08/2018.
 */
public class SlackRtmImpl implements SlackRtm {

    private SlackOptions options;
    private Vertx vertx;
    private WebClient webClient;
    private WebSocket websocket;
    private HttpClient httpClient;

    private long serial;
    private Handler<Message> messageHandler;
    private Future<Void> startedHandler;
    private Handler<Void> closeHandler;
    private ConcurrentMap<String, Future<RtmMessageResponse>> pendingAcks = new ConcurrentHashMap();


    public SlackRtmImpl(Vertx vertx, SlackOptions slackOptions) {
        this.options = new SlackOptions(slackOptions);
        httpClient = vertx.createHttpClient(slackOptions);
        webClient = WebClient.wrap(httpClient);
        this.vertx = vertx;
    }

    @Override
    public synchronized SlackRtmImpl messageHandler(Handler<Message> handler) {
        messageHandler = handler;
        return this;
    }

    @Override
    public void start(JsonObject startApiResp, Handler<AsyncResult<Void>> handler) {
        if (websocket != null) {
            throw new IllegalStateException("Already connected");
        }

        startedHandler = Future.future();
        startedHandler.setHandler(handler);
        URL wsURL;
        try {
            wsURL = new URL(startApiResp.getString("url").replace("ws", "http"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        int port = wsURL.getPort();
        if (port == -1) {
            if (options.isSsl()) {
                port = 443;
            } else {
                port = 80;
            }
        }

        // Collect all the channels
//        JsonArray channelsObj = respObj.getJsonArray("channels");
//        for (int i = 0;i < channelsObj.size();i++) {
//            JsonObject channelObj = channelsObj.getJsonObject(i);
//            addChannel(channelObj);
//        }
//
//        // Collect all known users
//        JsonArray usersObj = respObj.getJsonArray("users");
//        for (int i = 0;i < usersObj.size();i++) {
//            JsonObject userObj = usersObj.getJsonObject(i);
//            addUser(userObj);
//        }
//
//        // Collect all known ims
//        JsonArray imsObj = respObj.getJsonArray("ims");
//        for (int i = 0;i < imsObj.size();i++) {
//            JsonObject userObj = imsObj.getJsonObject(i);
//            addIM(userObj);
//        }
//
//        slackId = respObj.getJsonObject("self").getString("id");
//        slackName = respObj.getJsonObject("self").getString("name");
        httpClient.websocket(port, wsURL.getHost(), wsURL.getPath(), ws -> wsOpen(ws),
                err -> {
                    if (!startedHandler.isComplete()) {
                        startedHandler.fail(err);
                    }
                });
    }

    private synchronized void schedulePing(Vertx vertx, long expectedSerial) {
        vertx.setTimer(4000, timerID -> {
            synchronized (this) {
                if (websocket != null) {
                    if (expectedSerial == serial) {
                        websocket.writeFinalTextFrame(new JsonObject().put("type", "ping").put("id", UUID.randomUUID().toString()).encode());
                    }
                    schedulePing(vertx, ++serial);
                }
            }
        });
    }

    private void wsOpen(WebSocket ws) {
        synchronized (this) {
            websocket = ws;
        }
        LinkedList<WebSocketFrame> pendingFrames = new LinkedList<>();
        AtomicReference<Handler<WebSocketFrame>> handler = new AtomicReference<>();
        schedulePing(vertx, serial);
        handler.set(frame -> {
            wsHandle(frame);
        });
        for (WebSocketFrame frame : pendingFrames) {
            wsHandle(frame);
        }
        ws.frameHandler(frame -> {
            if (handler.get() != null) {
                handler.get().handle(frame);
            } else {
                pendingFrames.add(frame);
            }
        });
        ws.closeHandler(v -> {
            Handler<Void> h;
            synchronized (this) {
                websocket = null;
                h = closeHandler;
            }
            if (h != null) {
                h.handle(null);
            }
        });
    }

    void wsHandle(WebSocketFrame frame) {
        JsonObject json = new JsonObject(frame.textData());
        String type = json.getString("type");
        if (type != null) {
            switch (type) {
//                case "channel_left":
//                    removeChannel(json.getString("channel"));
//                    return;
//                case "channel_joined":
//                    addChannel(json.getJsonObject("channel"));
//                    return;
//                case "team_join":
//                    addUser(json.getJsonObject("user"));
//                    return;
//                case "im_created":
//                    addIM(json.getJsonObject("channel"));
//                    return;
//                case "im_close":
//                    removeIM(json.getString("channel"));
//                    return;
                case "message":
                    handleMessage(json);
                    return;
                case "hello":
                    startedHandler.handle(Future.succeededFuture());
                    return;
                case "presence_change":
                case "pong": {
                    return;
                }
            }
        }
        Boolean ok = json.getBoolean("ok");
        if (ok != null) {
            String replyTo = json.getString("reply_to");
            if (replyTo != null) {
                Future<RtmMessageResponse> ack = pendingAcks.remove(replyTo);
                if (ack != null) {
                    if (ok) {
                        ack.handle(Future.succeededFuture(new RtmMessageResponse(json)));
                    } else {
                        JsonObject error = json.getJsonObject("error");
                        SlackError err = new SlackError(error);
                        ack.handle(Future.failedFuture(err));
                    }
                }
                return;
            }
        }
        System.out.println("Unhandled frame " + json);
    }

    @Override
    public SlackRtmImpl send(Channel ch, String text) {
        return send(ch, text, null);
    }

    @Override
    public SlackRtmImpl send(Channel ch, String text, Handler<AsyncResult<RtmMessageResponse>> handler) {
        send(ch.getId(), null, text, handler);
        return this;
    }

    @Override
    public SlackRtmImpl sendToThread(Channel ch, String threadTs, String text) {
        return sendToThread(ch, threadTs, text, null);
    }

    @Override
    public SlackRtmImpl sendToThread(Channel ch, String threadTs, String text, Handler<AsyncResult<RtmMessageResponse>> handler) {
        send(ch.getId(), threadTs, text, handler);
        return this;
    }

    private void send(String channel, String threadTs, String msg, Handler<AsyncResult<RtmMessageResponse>> handler) {
        synchronized (this) {
            String id = UUID.randomUUID().toString();
            if (handler != null) {
                pendingAcks.put(id, Future.<RtmMessageResponse>future().setHandler(handler));
            }
            serial++; // Need to sync on SlackAdapterImpl
            JsonObject messageObject = new JsonObject().
                    put("id", id).
                    put("type", "message").
                    put("channel", channel).
                    put("text", msg);
            if(!StringUtil.isNullOrEmpty(threadTs)) messageObject.put("thread_ts", threadTs);
            websocket.writeFinalTextFrame(messageObject.encode());
        }
    }



    private synchronized void handleMessage(JsonObject messageObj) {
        if (messageHandler != null) {
            messageHandler.handle(new Message(messageObj.copy()));
        }
    }

    @Override
    public synchronized void close() {
        if (websocket != null) {
            websocket.close();
        }
    }
}
