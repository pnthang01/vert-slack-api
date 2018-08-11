package com.etybeno.vert.slack;

/**
 * Created by thangpham on 11/08/2018.
 */
public interface SlackReminders extends AbstractSlack {

    void add();

    void complete();

    void delete();

    void info();

    void list();
}