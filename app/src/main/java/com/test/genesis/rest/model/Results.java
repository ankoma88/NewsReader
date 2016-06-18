package com.test.genesis.rest.model;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class Results {
    public String[] topic;

    public String body;

    public String vuuid;

    public String[] image;

    public String date;

    public String url;

    public String changed;

    public String title;

    public Component[] component;

    public String created;

    public String[] teaser;

    public String[] attachments;

    public String uuid;

    @Override
    public String toString() {
        return "Results [topic = " + topic + ", body = " + body + ", vuuid = " + vuuid
                + ", image = " + image + ", date = " + date + ", url = " + url + ", changed = "
                + changed + ", title = " + title + ", component = " + component + ", created = "
                + created + ", teaser = " + teaser + ", attachments = " + attachments
                + ", uuid = " + uuid + "]";
    }
}