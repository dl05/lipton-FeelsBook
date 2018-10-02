package com.example.debralipton.lipton_feelsbook;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Feels implements Feeling {
    private Date date;
    //private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    private String comment;

    Feels() {
        this.date = new Date();
        this.comment = "";
    }

    Feels(String comment) {
        this.date = new Date();
        this.comment = (this.date + "\n" + "Feeling Love " + comment);
    }

    public Date getDate() {return this.date;}
    public String getComment() {return this.comment;}

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return this.getComment();
    }
    public int getCount() {
        return this.getCount();
    }

}
