package com.etybeno.vert.slack;

import com.etybeno.vert.slack.type.Conversation;
import com.etybeno.vert.slack.type.Multi;
import com.etybeno.vert.slack.type.User;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.util.List;

/**
 * Created by thangpham on 13/08/2018.
 */
public interface SlackUsers extends AbstractSlack {

    void conversations(String cursor, Boolean excludeArchived, Integer limit, List<String> types, String userId,
                       Handler<AsyncResult<Multi<Conversation>>> handler);

    void deletePhoto(Handler<AsyncResult<Void>> handler);

    void getPresence(String userId, Handler<AsyncResult<JsonObject>> handler);

    void identity(Handler<AsyncResult<JsonObject>> handler);

    void info(String userId, Boolean includeLocale, Handler<AsyncResult<User>> handler);

    void list(String cursor, Boolean includeLocale, Integer limit, Boolean presence, Handler<AsyncResult<Multi<User>>> handler);

    void lookupByEmail(String email, Handler<AsyncResult<User>> handler);

    void setActive(Handler<AsyncResult<Void>> handler);

    void setPhoto(File image, Integer cropW, Integer cropX, Integer cropY, Handler<AsyncResult<Void>> handler);

    void setPresence(String status, Handler<AsyncResult<Void>> handler);
}
