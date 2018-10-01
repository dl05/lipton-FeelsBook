package com.example.debralipton.lipton_feelsbook;


public class Love extends Feels {
    public int loveCounter=0;

    public Love(String comment) {
        super(comment);
        loveCounter++;
        System.out.println(loveCounter);

    }
}
