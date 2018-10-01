package com.example.debralipton.lipton_feelsbook;

import java.util.Date;

public abstract class Feeling {
    private Date date;
    private String message;

    Feeling(){
        this.date = new Date();
        this.message = message;
    }

    public Date getDate() {return this.date;}

    public String getMessage() {return this.message;}

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return this.getMessage();
    }


}
