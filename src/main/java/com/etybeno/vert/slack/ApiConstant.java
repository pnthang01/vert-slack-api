package com.etybeno.vert.slack;

/**
 * Created by thangpham on 07/08/2018.
 */
public interface ApiConstant {

    interface Channels {
        String ARCHIVE = "/api/channels.archive";
        String CREATE = "/api/channels.create";
        String HISTORY = "/api/channels.history";
        String INFO = "/api/channels.info";
        String INVITE = "/api/channels.invite";
        String JOIN = "/api/channels.join";
        String KICK = "/api/channels.kick";
        String LEAVE = "/api/channels.leave";
        String LIST = "/api/channels.list";
        String MARK = "/api/channels.mark";
        String RENAME = "/api/channels.rename";
        String REPLIES = "/api/channels.replies";
        String SET_PURPOSE = "/api/channels.setPurpose";
        String SET_TOPIC = "/api/channels.setTopic";
        String UNARCHIVE = "/api/channels.unarchive";
    }

    interface Conversations {
        String ARCHIVE = "/api/conversations.archive";
        String CLOSE = "/api/conversations.close";
        String CREATE = "/api/conversations.create";
        String HISTORY = "/api/conversations.history";
        String INFO = "/api/conversations.info";
        String INVITE = "/api/conversations.invite";
        String JOIN = "/api/conversations.join";
        String KICK = "/api/conversations.kick";
        String LEAVE = "/api/conversations.leave";
        String LIST = "/api/conversations.list";
        String MEMBERS = "/api/conversations.members";
        String OPEN = "/api/conversations.open";
        String RENAME = "/api/conversations.rename";
        String REPLIES = "/api/conversations.replies";
        String SET_PURPOSE = "/api/conversations.setPurpose";
        String SET_TOPIC = "/api/conversations.setTopic";
        String UNARCHIVE = "/api/conversations.unarchive";
    }

    interface Chat {
        String DELETE = "/api/chat.delete";
        String GET_PERMALINK = "/api/chat.getPermalink";
        String ME_MESSAGE = "/api/chat.meMessage";
        String POST_EPHEMERAL = "/api/chat.postEphemeral";
        String POST_MESSAGE = "/api/chat.postMessage";
        String UNFURL = "/api/chat.unfurl";
        String UPDATE = "/api/chat.update";
    }
}
