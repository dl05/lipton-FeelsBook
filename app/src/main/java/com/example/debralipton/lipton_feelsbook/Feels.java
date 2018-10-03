package com.example.debralipton.lipton_feelsbook;

import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Feels implements Feeling {
    private Date date;
    private String dateString;
    //private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    private String comment;
    private String emotion;
    private String output;
    private int count;

    Feels() {
        date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
        this.dateString = dateFormat.format(date);
        this.comment = "";
        this.emotion = "emotion";
    }

    Feels(String comment, String emotion) {
        date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
        this.dateString = dateFormat.format(date);
        this.comment = comment;
        this.emotion = emotion;
        this.output = (this.dateString + " - " + this.emotion  + "\n" + this.comment);

    }

    public Date getDate() {return this.date;}
    public String getDateString() {return this.dateString;}
    public String getComment() {return this.comment;}
    public String getEmotion() {return this.emotion;}
    public String getOutput() {return this.output;}

    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() <= 100) {
            System.out.println("Comment length: " + comment.length());
            this.comment = comment;
            this.output = (dateString + " - " + emotion + "\n" + comment);
        } else {
            throw new CommentTooLongException();
        }
    }

    public void setEmotion(String emotion) {

        this.emotion = emotion;
        this.output = (dateString + " - " + emotion + "\n" + comment);
    }

    public void setDate(String date) {
        this.dateString = date;
        this.output = (dateString + " - " + emotion + "\n" + comment);
    }

    public String toString() {
        return this.getOutput();
    }
    public int getCount() {return this.count;}

}
