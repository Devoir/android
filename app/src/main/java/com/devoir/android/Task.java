package com.devoir.android;

import android.graphics.Color;

import java.util.Comparator;
import java.util.Date;

public class Task implements Comparable {

    private String id;
    private String name;
    private String description;
    private Color color;
    private Date createdDate;
    private Date startDate;
    private Date dueDate;
    private boolean markedComplete;
    private boolean pastDue;

    public Task(String id, String name, String description, Color c, Date created, Date start,
                Date due, boolean complete) {
        setId(id);
        setName(name);
        setDescription(description);
        setColor(c);
        setCreatedDate(created);
        setStartDate(start);
        setDueDate(due);
        setMarkedComplete(complete);
        setPastDue(new Date().after(due));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getMarkedComplete() {
        return markedComplete;
    }

    public void setMarkedComplete(boolean complete) {
        this.markedComplete = complete;
    }

    public boolean getPastDue() {
        return pastDue;
    }

    public void setPastDue(boolean pastDue) {
        this.pastDue = pastDue;
    }

    @Override
    public int compareTo(Object anotherTask) {
        if (!(anotherTask instanceof Task)) {
            throw new ClassCastException("A Task object was expected.");
        }
        return this.dueDate.compareTo(((Task) anotherTask).getDueDate());
    }

    public static Comparator NameComparator = new Comparator() {
        public int compare(Object task, Object anotherTask) {
            if (!(task instanceof Task) || !(anotherTask instanceof Task)) {
                throw new ClassCastException("Both parameters must be Task objects.");
            }
            String name1 = ((Task) task).getName();
            String name2 = ((Task) anotherTask).getName();
            return name1.compareTo(name2);
        }
    };
}