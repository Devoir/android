package com.devoir.android.model;

/**
 * Created by Brady on 4/10/2015.
 */
public class Course {

    private int id;
    private String name;
    private String iCalFeed;
    private int color;

    public Course(int id, String name, String iCalFeed, int color) {
        this.id = id;
        this.name = name;
        this.iCalFeed = iCalFeed;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getiCalFeed() {
        return iCalFeed;
    }

    public void setiCalFeed(String iCalFeed) {
        this.iCalFeed = iCalFeed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
