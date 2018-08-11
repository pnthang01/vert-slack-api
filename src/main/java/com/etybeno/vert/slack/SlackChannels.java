package com.etybeno.vert.slack;

import com.etybeno.vert.slack.type.Channel;
import com.etybeno.vert.slack.type.Multi;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * Created by thangpham on 07/08/2018.
 */
public interface SlackChannels extends AbstractSlack {

    void archive(String channelId, Handler<AsyncResult<Channel>> handler);

    void create(String name, Boolean isPrivate, List<String> userIds, Handler<AsyncResult<Channel>> handler);

    void history();

    void info();

    void invite(String channelId, String userId, Handler<AsyncResult<Channel>> handler);

    void join();

    void kick();

    void leave();

    void list(String cursor, Boolean excludeArchived, Boolean excludeMembers, Integer limit, Handler<AsyncResult<Multi<Channel>>> handler);

    void mark();

    void rename();

    void replies();

    void setPurpose();

    void setTopic();

    void unarchive();

}