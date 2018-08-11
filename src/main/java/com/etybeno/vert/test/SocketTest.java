package com.etybeno.vert.test;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import io.vertx.reactivex.ext.web.handler.sockjs.SockJSHandler;

/**
 * Created by thangpham on 04/08/2018.
 */
public class SocketTest extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route("/news-feed/*").handler(SockJSHandler.create(vertx).socketHandler(sockJSSocket -> {
            Flowable<String> msg = vertx.eventBus().<String>consumer("news-feed")
                    .bodyStream()
                    .toFlowable();

            Disposable subscription = msg.subscribe(sockJSSocket::write);

            sockJSSocket.endHandler(event -> subscription.dispose());
        }));

        router.route().handler(StaticHandler.create());
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);

        vertx.setPeriodic(1000, t -> vertx.eventBus().publish("news-feed",
                new JsonObject().put("message", "news feed from the server!'").toString()));
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SocketTest());
    }
}
