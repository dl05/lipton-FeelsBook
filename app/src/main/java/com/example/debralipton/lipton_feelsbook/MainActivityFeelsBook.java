package com.example.debralipton.lipton_feelsbook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

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
import java.util.Collection;
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
        final TextView loveCount = (TextView) findViewById(R.id.loveNumber);
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
                    Feels newFeeling = new Love(text, "Love");
                    feelingList.add(newFeeling);
                    int num = (newFeeling.getCount());
                    loveCount.setText(Integer.toString(num));
                    loveCount.setText(R.string.loveNumber);
                    System.out.println("Love");
                }
                if(JoyCheck.isChecked()) {
                    Feels newFeeling = new Joy(text, "Joy");
                    feelingList.add(newFeeling);
                    System.out.println("Joy");
                }

                //tell adapter that something has changed to refresh list
                saveInFile();
                adapter.notifyDataSetChanged();

            }
        });

        feelingHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivityFeelsBook.this, "Edit/Delete Entry", Toast.LENGTH_LONG).show();

                Feeling feeling = feelingList.get(position);
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivityFeelsBook.this);
                adb.setMessage("Edit/Delete " + feeling.toString()+ "  ?");
                adb.setCancelable(true);
                final int finalPosition = position;
                adb.setPositiveButton("Edit/Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(feelingList);
                        EditDelete(finalPosition);
                        //Feeling feeling = feelingList.get(finalPosition);
                        //feelingList.remove(feeling);
                        //System.out.println(feelingList);
                    }
                });

                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.show();
                System.out.println(feeling);
                //saveInFile();
                //adapter.notifyDataSetChanged();
                return false;
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

    public void EditDelete(final int index) {
        final Dialog editDel = new Dialog(MainActivityFeelsBook.this);
        editDel.setTitle("Edit/Delete");
        editDel.setContentView(R.layout.edit_delete);
        final Feels feeling = feelingList.get(index);
        Date date = feeling.getDate();
        final EditText Date = editDel.findViewById(R.id.changedate);
        Date.setText(date.toString());

        final String oldComment = feeling.getComment();
        final EditText comment = editDel.findViewById(R.id.changecomment);
        comment.setText(oldComment.toString());

        String oldEmotion = feeling.getEmotion();
        final EditText emotion = editDel.findViewById(R.id.changefeeling);
        emotion.setText(oldEmotion.toString());

        Button updateEntry = (Button) editDel.findViewById(R.id.finished);
        Button delEntry = (Button) editDel.findViewById(R.id.Delete);
        updateEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                String newComment = comment.getText().toString();
                //System.out.println("old comment" + oldComment.toString());
                System.out.println("comment " + comment.getText().toString());
                feeling.setComment(newComment);
                final Feels newFeels = feeling;
                feelingList.get(index).setComment(newComment);
                //feelingList.set(index, newFeels);
                System.out.println("Feeling is now: " + feeling.getComment());
                System.out.println("newFeels is now: " + newFeels.getComment());
                System.out.println("List is:  " + feelingList);
                //feelingList.set(index, (Feels) Date.getText());
                //feelingList.set(index, (Feels) emotion.getText());
                adapter.notifyDataSetChanged();
                saveInFile();
                System.out.println(feelingList);
                editDel.dismiss();

            }
    });

        delEntry.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                feelingList.remove(feeling);
                adapter.notifyDataSetChanged();
                saveInFile();
                System.out.println(feelingList);
                editDel.dismiss();
            }
        });
        editDel.show();

    }
}
