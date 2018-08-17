package com.etybeno.vert.slack;

import com.etybeno.vert.slack.type.Multi;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by thangpham on 11/08/2018.
 */
public interface SlackWebApi {

    default <T extends SlackType> void sendAndReceiveOne(HttpRequest<Buffer> req, String dataKey,
                                                         Handler<AsyncResult<T>> handler, Class<T> clazz) {
        req.send(ar -> {
            if (ar.succeeded()) {
                HttpResponse<Buffer> res = ar.result();
                if (res.statusCode() == 200) {
                    JsonObject object = res.bodyAsJsonObject();
                    if (object.getBoolean("ok")) {
                        JsonObject channelJson = object.getJsonObject(dataKey);
                        try {
                            Constructor<T> constructor = clazz.getDeclaredConstructor(JsonObject.class);
                            handler.handle(Future.succeededFuture(constructor.newInstance(channelJson)));
                        } catch (NoSuchMethodException | IllegalArgumentException |
                                IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            handler.handle(Future.failedFuture(e));
                        }

                    } else handler.handle(Future.failedFuture(object.getString("error")));
                } else handler.handle(Future.failedFuture("Unexpected status: " + res.statusCode()));
            } else {
                handler.handle(Future.failedFuture(ar.cause()));
            }
        });
    }

    default <T extends SlackType> void sendAndReceivedMulti(HttpRequest<Buffer> req, String dataKey,
                                                           Handler<AsyncResult<Multi<T>>> handler, Class<T> clazz) {
        req.send(ar -> {
            if (ar.succeeded()) {
                HttpResponse<Buffer> res = ar.result();
                if (res.statusCode() == 200) {
                    JsonObject object = res.bodyAsJsonObject();
                    if (object.getBoolean("ok")) {
                        handler.handle(Future.succeededFuture(new Multi<T>(object, dataKey, clazz)));
                    } else handler.handle(Future.failedFuture(object.getString("error")));
                } else handler.handle(Future.failedFuture("Unexpected status: " + res.statusCode()));
            } else {
                handler.handle(Future.failedFuture(ar.cause()));
            }
        });
    }

    default void sendAndReceiveVoid(HttpRequest<Buffer> req, Handler<AsyncResult<Void>> handler) {
        req.send(ar -> {
            if (ar.succeeded()) {
                HttpResponse<Buffer> res = ar.result();
                if (res.statusCode() == 200) {
                    JsonObject object = res.bodyAsJsonObject();
                    System.out.println(object);
                    if (object.getBoolean("ok")) handler.handle(Future.succeededFuture());
                    else handler.handle(Future.failedFuture(object.getString("error")));
                } else handler.handle(Future.failedFuture("Unexpected status: " + res.statusCode()));
            } else {
                handler.handle(Future.failedFuture(ar.cause()));
            }
        });
    }

    default <T> void sendAndReceivePrimitive(HttpRequest<Buffer> req, String dataKey,
                                             Handler<AsyncResult<T>> handler, Class<T> clazz) {
        req.send(ar -> {
            if (ar.succeeded()) {
                HttpResponse<Buffer> res = ar.result();
                if (res.statusCode() == 200) {
                    JsonObject object = res.bodyAsJsonObject();
                    if (object.getBoolean("ok")) {
                        handler.handle(Future.succeededFuture(clazz.cast(object.getValue(dataKey))));
                    } else handler.handle(Future.failedFuture(object.getString("error")));
                } else handler.handle(Future.failedFuture("Unexpected status: " + res.statusCode()));
            } else {
                handler.handle(Future.failedFuture(ar.cause()));
            }
        });
    }

    default void sendAndReceiveFullObject(HttpRequest<Buffer> req, Handler<AsyncResult<JsonObject>> handler) {
        req.send(ar -> {
            if (ar.succeeded()) {
                HttpResponse<Buffer> res = ar.result();
                if (res.statusCode() == 200) {
                    JsonObject object = res.bodyAsJsonObject();
                    if (object.getBoolean("ok")) handler.handle(Future.succeededFuture(object));
                    else handler.handle(Future.failedFuture(object.getString("error")));
                } else handler.handle(Future.failedFuture("Unexpected status: " + res.statusCode()));
            } else handler.handle(Future.failedFuture(ar.cause()));
        });
    }
}
