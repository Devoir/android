package com.devoir.android.model;

import java.util.Comparator;
import java.util.Date;

public class Task implements Comparable {

    private String id;
    private String iCalEventID;
    private String name;
    private String description;
    private int color;
    private Date dueDate;
    private boolean markedComplete;
    private boolean isVisible;

    public Task(String id, String iCalID, String name, String description, int c,
                Date due, boolean complete, boolean visible) {
        setId(id);
        setICalEventID(iCalID);
        setName(name);
        setDescription(description);
        setColor(c);
        setDueDate(due);
        setMarkedComplete(complete);
        setVisible(visible);
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getICalEventID() {
        return iCalEventID;
    }

    public void setICalEventID(String iCalEventID) {
        this.iCalEventID = iCalEventID;
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