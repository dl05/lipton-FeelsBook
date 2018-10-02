package com.example.debralipton.lipton_feelsbook;

public class Joy extends Feels {
    static private int joyCounter;


    public Joy(String comment) {
        super(comment);
        joyCounter++;
        System.out.println(joyCounter);

    }
}
