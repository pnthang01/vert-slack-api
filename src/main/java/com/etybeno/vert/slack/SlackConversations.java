package com.etybeno.vert.slack;

import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Message;
import com.etybeno.vert.slack.type.Multi;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * Created by thangpham on 10/08/2018.
 */
public interface SlackConversations extends AbstractSlack {

    void archive(String conversationId, Handler<AsyncResult<Void>> handler);

    void close(String conversationId, Handler<AsyncResult<Void>> handler);

    void create(String name, Boolean isPrivate, List<String> userIds, Handler<AsyncResult<Channel>> handler);

    void history(String channelId, String cursor, Boolean inclusive, Long latest, Integer limit, Long oldest,
                 Handler<AsyncResult<Message>> handler);

    void info();

    void invite();

    void join();

    void kick();

    void leave();

    void list(String cursor, Boolean excludeArchived, Boolean excludeMembers, Integer limit,
              Handler<AsyncResult<Multi<Channel>>> handler);

    void members();

    void open();

    void rename();

    void replies();

    void setPurpose();

    void setTopic();

    void unarchive();
}
