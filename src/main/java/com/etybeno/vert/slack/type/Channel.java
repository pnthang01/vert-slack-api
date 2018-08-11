package com.etybeno.vert.slack.type;

import io.vertx.core.json.JsonObject;

/**
 * Created by thangpham on 07/08/2018.
 */
public class Channel extends SlackType {

    public Channel(JsonObject json) {
        super(json);
    }

    public String getId() {
        return json.getString("id");
    }

    public String getName() {
        return json.getString("name");
    }

    public boolean isChannel() {
        return Boolean.TRUE.equals(json.getBoolean("is_channel"));
    }

    public int getCreated() {
        return json.getInteger("created");
    }

    public String getCreator() {
        return json.getString("creator");
    }

    public JsonObject toJson() {
        return json.copy();
    }
//    private String creator;
//    @JsonProperty("is_archived")
//    private boolean isArchived;
//    @JsonProperty("is_general")
//    private boolean isGeneral;
//    @JsonProperty("name_normalized")
//    private String nameNormalized;
//    @JsonProperty("is_shared")
//    private boolean isShared;
//    @JsonProperty("is_org_shared")
//    private boolean isOrgShared;
//    @JsonProperty("is_member")
//    private boolean isMember;
//    @JsonProperty("is_private")
//    private boolean isPrivate;
//    @JsonProperty("is_mpim")
//    private boolean isMpim;
//    @JsonProperty("last_read")
//    private String lastRead;
//    @JsonProperty("unread_count")
//    private int unreadCount;
//    @JsonProperty("unread_count_display")
//    private int unreadCountDisplay;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public boolean isChannel() {
//        return isChannel;
//    }
//
//    public void setChannel(boolean channel) {
//        isChannel = channel;
//    }
//
//    public int getCreated() {
//        return created;
//    }
//
//    public void setCreated(int created) {
//        this.created = created;
//    }
//
//    public String getCreator() {
//        return creator;
//    }
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }
//
//    public boolean isArchived() {
//        return isArchived;
//    }
//
//    public void setArchived(boolean archived) {
//        isArchived = archived;
//    }
//
//    public boolean isGeneral() {
//        return isGeneral;
//    }
//
//    public void setGeneral(boolean general) {
//        isGeneral = general;
//    }
//
//    public String getNameNormalized() {
//        return nameNormalized;
//    }
//
//    public void setNameNormalized(String nameNormalized) {
//        this.nameNormalized = nameNormalized;
//    }
//
//    public boolean isShared() {
//        return isShared;
//    }
//
//    public void setShared(boolean shared) {
//        isShared = shared;
//    }
//
//    public boolean isOrgShared() {
//        return isOrgShared;
//    }
//
//    public void setOrgShared(boolean orgShared) {
//        isOrgShared = orgShared;
//    }
//
//    public boolean isMember() {
//        return isMember;
//    }
//
//    public void setMember(boolean member) {
//        isMember = member;
//    }
//
//    public boolean isPrivate() {
//        return isPrivate;
//    }
//
//    public void setPrivate(boolean aPrivate) {
//        isPrivate = aPrivate;
//    }
//
//    public boolean isMpim() {
//        return isMpim;
//    }
//
//    public void setMpim(boolean mpim) {
//        isMpim = mpim;
//    }
//
//    public String getLastRead() {
//        return lastRead;
//    }
//
//    public void setLastRead(String lastRead) {
//        this.lastRead = lastRead;
//    }
//
//    public int getUnreadCount() {
//        return unreadCount;
//    }
//
//    public void setUnreadCount(int unreadCount) {
//        this.unreadCount = unreadCount;
//    }
//
//    public int getUnreadCountDisplay() {
//        return unreadCountDisplay;
//    }
//
//    public void setUnreadCountDisplay(int unreadCountDisplay) {
//        this.unreadCountDisplay = unreadCountDisplay;
//    }
}
