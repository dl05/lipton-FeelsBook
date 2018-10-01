package com.example.debralipton.lipton_feelsbook;

import java.util.Date;

public abstract class Feels implements Feeling {
    private Date date;
    private String comment;

    Feels() {
        this.date = new Date();
        this.comment = "";
    }

    Feels(String comment) {
        this.date = new Date();
        this.comment = comment;
    }

    public Date getDate() {return this.date;}
    public String getComment() {return this.comment;}

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return this.getComment();
    }
}
