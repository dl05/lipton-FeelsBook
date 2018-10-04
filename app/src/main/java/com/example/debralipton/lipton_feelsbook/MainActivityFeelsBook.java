/**
 * This is the main activity for FeelsBook.
 * The app allows the user to record an emotion and add an optional comment
 * Emotions, comments and dates may edited or deleted after being entered
 * The counts of each emotion entered are displayed next to the emotion checkboxes
 */
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class MainActivityFeelsBook extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView feelingHistory;

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
        final CheckBox FearCheck = (CheckBox) findViewById(R.id.FearBox);
        final CheckBox AngerCheck = (CheckBox) findViewById(R.id.AngerBox);
        final CheckBox SadnessCheck = (CheckBox) findViewById(R.id.SadnessBox);
        final CheckBox SurpriseCheck = (CheckBox) findViewById(R.id.SurpriseBox);

        feelingHistory = (ListView) findViewById(R.id.feelingHistory);

        LoveCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Love("", "Love");
                feelingList.add(newFeeling);
                countEmotion("Love");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                LoveCheck.setChecked(false);
                update();
                                       }
                                   });

        JoyCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Joy("", "Joy");
                feelingList.add(newFeeling);
                countEmotion("Joy");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                JoyCheck.setChecked(false);
                update();
            }
        });

        SurpriseCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Surprise("", "Surprise");
                feelingList.add(newFeeling);
                countEmotion("Surprise");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                SurpriseCheck.setChecked(false);
                update();
            }
        });

        SadnessCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Sadness("", "Sadness");
                feelingList.add(newFeeling);
                countEmotion("Sadness");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                SadnessCheck.setChecked(false);
                update();
            }
        });

        AngerCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Anger("", "Angry");
                feelingList.add(newFeeling);
                countEmotion("Angry");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                AngerCheck.setChecked(false);
                update();
            }
        });

        FearCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Fear("", "Fear");
                feelingList.add(newFeeling);
                countEmotion("Fear");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                FearCheck.setChecked(false);
                update();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();

                try {
                    feelingList.get(feelingList.size()-1).setComment(text);
                    Toast.makeText(MainActivityFeelsBook.this, "Comment has been added to last entry",
                            Toast.LENGTH_LONG).show();
                    bodyText.getText().clear();
                } catch (CommentTooLongException e) {
                    Toast.makeText(MainActivityFeelsBook.this, "Comment must be less than 100 characters",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                //tell adapter that something has changed to refresh list
                bodyText.getText().clear();
                update();

            }
        });

        feelingHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                Toast.makeText(MainActivityFeelsBook.this, "Edit/Delete Entry", Toast.LENGTH_LONG).show();

                Feeling feeling = feelingList.get(position);
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivityFeelsBook.this);
                adb.setMessage("Edit/Delete entry? \n" + feeling.toString());
                adb.setCancelable(true);
                final int finalPosition = position;
                adb.setPositiveButton("Edit/Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(feelingList);
                        EditDelete(finalPosition);
                    }
                });

                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                adb.show();
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
        countEmotion("Love");
        countEmotion("Joy");
        countEmotion("Fear");
        countEmotion("Angry");
        countEmotion("Surprise");
        countEmotion("Sadness");
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

        final String date = feeling.getDateString();
        final EditText newDate = editDel.findViewById(R.id.changedate);
        newDate.setText(date.toString());

        final String oldComment = feeling.getComment();
        final EditText comment = editDel.findViewById(R.id.changecomment);
        comment.setText(oldComment.toString());

        final String oldEmotion = feeling.getEmotion();
        final EditText emotion = editDel.findViewById(R.id.changefeeling);
        emotion.setText(oldEmotion.toString());

        Button updateEntry = (Button) editDel.findViewById(R.id.finished);
        Button delEntry = (Button) editDel.findViewById(R.id.Delete);
        updateEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                try {
                    feelingList.get(index).setComment(comment.getText().toString());
                } catch (CommentTooLongException e) {
                    Toast.makeText(MainActivityFeelsBook.this, "Comment must be less than 100 characters", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                feelingList.get(index).setEmotion(emotion.getText().toString());
                feelingList.get(index).setDate(newDate.getText().toString());
                countEmotion(oldEmotion);
                countEmotion(emotion.getText().toString());
                //sortFeelings();
                update();
                editDel.dismiss();

            }
    });

        delEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                feelingList.remove(feeling);
                countEmotion(feeling.getEmotion().toString());
                update();
                editDel.dismiss();
            }
        });
        editDel.show();
    }

    public void countEmotion(String emotion) {
        int count=0;
        int i;

        for (i=0;i<feelingList.size(); i++){
            if (feelingList.get(i).getEmotion().trim().equals(emotion)) {
                count++;
            }
        }
        update(emotion, count);
    }

    public void update(String emotion, int count) {
        final TextView loveCount = (TextView) findViewById(R.id.loveNumber);
        final TextView sadCount = (TextView) findViewById(R.id.sadnessNumber);
        final TextView angerCount = (TextView) findViewById(R.id.angerNumber);
        final TextView surpriseCount = (TextView) findViewById(R.id.surpriseNumber);
        final TextView fearCount = (TextView) findViewById(R.id.fearNumber);
        final TextView joyCount = (TextView) findViewById(R.id.joyNumber);

        switch (emotion) {
            case "Love":
                loveCount.setText(Integer.toString(count));
                break;
            case "Sadness":
                sadCount.setText(Integer.toString(count));
                break;
            case "Angry":
                angerCount.setText(Integer.toString(count));
                break;
            case "Fear":
                fearCount.setText(Integer.toString(count));
                break;
            case "Surprise":
                surpriseCount.setText(Integer.toString(count));
                break;
            case "Joy":
                joyCount.setText(Integer.toString(count));
                break;
        }
        update();

    }

    public void sortFeelings() {
        Collections.sort(feelingList, new Comparator<Feeling>(){
            public int compare(Feeling obj2, Feeling obj1) {
                return obj1.getDateString().compareToIgnoreCase(obj2.getDateString()); // To compare string values
            }
        });
    }

    public void update() {
        sortFeelings();
        adapter.notifyDataSetChanged();
        saveInFile();
    }

}