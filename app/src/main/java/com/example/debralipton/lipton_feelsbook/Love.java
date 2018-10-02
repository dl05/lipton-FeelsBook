package com.example.debralipton.lipton_feelsbook;


import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class Love extends Feels {
    static int loveCounter;


    public Love(String comment) {
        super(comment);
        loveCounter++;
        //loveCount =  loveCount.findViewById(R.id.loveNumber);
        //loveCount.setText(Integer.toString(loveCounter));
        //textViewToChange.setText(loveCounter);
        System.out.println(loveCounter);

    }

    public int getCount() {
        return loveCounter;
    }

}
