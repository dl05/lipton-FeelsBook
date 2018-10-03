package com.example.debralipton.lipton_feelsbook;


import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Love extends Feels {
    static int loveCounter;


    public Love(String comment, String emotion) {
        super(comment, emotion);
        getCount();
        //int loveCounter;
        //loveCount =  loveCount.findViewById(R.id.loveNumber);
        //loveCount.setText(Integer.toString(loveCounter));
        //textViewToChange.setText(loveCounter);
        //System.out.println(loveCounter);

    }
    public static int getCount(ArrayList<Feels> feelingList) {
        int loveCounter = Collections.frequency(feelingList, "Love");
        System.out.println("Love #: " + loveCounter);
        return loveCounter;

    }

}
