package com.example.debralipton.lipton_feelsbook;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class MainActivityFeelsBook extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView feelingHistory;
    boolean loveCheck = false;


    ArrayList<Feels> feelingList;
    ArrayAdapter<Feels> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feels_book);

        bodyText = (EditText) findViewById(R.id.editComment);
        Button submitButton = (Button) findViewById(R.id.submitButton);

        final CheckBox LoveCheck = (CheckBox) findViewById(R.id.LoveBox);
        final CheckBox JoyCheck = (CheckBox) findViewById(R.id.JoyBox);
        CheckBox FearBox = (CheckBox) findViewById(R.id.FearBox);
        CheckBox AngerBox = (CheckBox) findViewById(R.id.AngerBox);
        CheckBox SadnessBox = (CheckBox) findViewById(R.id.SadnessBox);
        CheckBox SurpriseBox = (CheckBox) findViewById(R.id.SurpriseBox);


        feelingHistory = (ListView) findViewById(R.id.feelingHistory);
/*
        LoveBox.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           setResult(RESULT_OK);
                                           switch (v.getId()) {
                                               case R.id.LoveBox:
                                                   System.out.println("Love");
                                                   loveCheck = true;}
                                       }
                                   }); */

        submitButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();

                if(LoveCheck.isChecked()) {
                    Feels newFeeling = new Love(text);
                    feelingList.add(newFeeling);
                    System.out.println("Love");
                }
                if(JoyCheck.isChecked()) {
                    Feels newFeeling = new Joy(text);
                    feelingList.add(newFeeling);
                    System.out.println("Joy");
                }

                //tell adapter that something has changed to refresh list
                saveInFile();
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<Feels>(this, R.layout.activity_display, feelingList);
        feelingHistory.setAdapter(adapter);
    }

    private String[] loadFromFile() {
        ArrayList<String> feel = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Love>>(){}.getType();
            feelingList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            feelingList = new ArrayList<Feels>();
        }
        return feel.toArray(new String[feel.size()]);
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(feelingList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
