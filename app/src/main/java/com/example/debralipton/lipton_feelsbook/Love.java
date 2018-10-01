package com.example.debralipton.lipton_feelsbook;

public class Love extends Feeling {
    private String message;
    static private Integer loveCounter = 0;

    Love() {
        this.message = "Optional message goes here";
    }

    public String getMessage() {return this.message;}
}
