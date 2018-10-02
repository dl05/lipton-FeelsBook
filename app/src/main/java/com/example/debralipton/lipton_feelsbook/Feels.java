package com.example.debralipton.lipton_feelsbook;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Feels implements Feeling {
    private Date date;
    //private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    private String comment;
    private String emotion;
    private String output;

    Feels() {
        this.date = new Date();
        this.comment = "";
        this.emotion = emotion;
    }

    Feels(String comment, String emotion) {
        this.date = new Date();
        this.comment = comment;
        this.emotion = emotion;
        this.output = (this.date + "\n" + emotion + " - "+ comment);
    }

    public Date getDate() {return this.date;}
    public String getComment() {return this.comment;}
    public String getEmotion() {return this.emotion;}
    public String getOutput() {return this.output;}

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return this.getOutput();
    }
    public int getCount() {
        return this.getCount();
    }

}
