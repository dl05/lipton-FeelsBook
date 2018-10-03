package com.example.debralipton.lipton_feelsbook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
    //boolean loveCheck = false;
    //final TextView loveCount = (TextView) findViewById(R.id.loveNumber);

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
                saveInFile();
                adapter.notifyDataSetChanged();
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
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });

        SurpriseCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Love("", "Surprise");
                feelingList.add(newFeeling);
                countEmotion("Surprise");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                SurpriseCheck.setChecked(false);
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });

        SadnessCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Love("", "Sadness");
                feelingList.add(newFeeling);
                countEmotion("Sadness");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                SadnessCheck.setChecked(false);
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });

        AngerCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Love("", "Angry");
                feelingList.add(newFeeling);
                countEmotion("Angry");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                AngerCheck.setChecked(false);
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });

        FearCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Feels newFeeling = new Love("", "Fear");
                feelingList.add(newFeeling);
                countEmotion("Fear");
                Toast.makeText(MainActivityFeelsBook.this, "Your Feeling has been recorded!", Toast.LENGTH_LONG).show();
                FearCheck.setChecked(false);
                saveInFile();
                adapter.notifyDataSetChanged();
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
                saveInFile();
                adapter.notifyDataSetChanged();

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
        countEmotion("Love");
        countEmotion("Joy");
        countEmotion("Fear");
        countEmotion("Anger");
        countEmotion("Surprise");
        countEmotion("Sadness");
        System.out.println("onStart: " + feelingList);

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
                //System.out.println("old comment" + oldComment.toString());
                //System.out.println("comment " + comment.getText().toString());
                //feeling.setComment(newComment);
                try {
                    feelingList.get(index).setComment(comment.getText().toString());
                } catch (CommentTooLongException e) {
                    Toast.makeText(MainActivityFeelsBook.this, "Comment must be less than 100 characters", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //String oldFeeling = feelingList.get(index).getEmotion().toString();
                feelingList.get(index).setEmotion(emotion.getText().toString());
                feelingList.get(index).setDate(newDate.getText().toString());
                //System.out.println("Feeling is now: " + feeling.getEmotion());
                //System.out.println("Feeling.get is now: " + feelingList.get(index).getComment());
                //System.out.println("List is:  " + feelingList);
                //feelingList.set(index, (Feels) Date.getText());
                //feelingList.set(index, (Feels) emotion.getText());
                countEmotion(oldEmotion);
                countEmotion(emotion.getText().toString());
                sortFeelings();
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
                countEmotion(feeling.getEmotion().toString());
                adapter.notifyDataSetChanged();
                saveInFile();
                System.out.println(feelingList);
                editDel.dismiss();
            }
        });
        editDel.show();
    }

    public void countEmotion(String emotion) {
        int count=0;
        int i;
        System.out.println("feeling size" + feelingList.size());
        for (i=0;i<feelingList.size(); i++){
            System.out.println("i is : " +i);
            System.out.println(feelingList.get(i));
            System.out.println("Emotion is: "+feelingList.get(i).getEmotion());
            System.out.println("Match is: " + feelingList.get(i).getEmotion().trim().equals(emotion));

            if (feelingList.get(i).getEmotion().trim().equals(emotion)) {
                count++;
                System.out.println("Count: " + count);
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
                System.out.println("Updating Love to: "+count);
                loveCount.setText(Integer.toString(count));
                break;
            case "Sadness":
                System.out.println("Updating Sad to: "+count);
                sadCount.setText(Integer.toString(count));
                break;
            case "Anger":
                System.out.println("Updating Anger to: "+count);
                angerCount.setText(Integer.toString(count));
                break;
            case "Fear":
                System.out.println("Updating Fear to: "+count);
                fearCount.setText(Integer.toString(count));
                break;
            case "Surprise":
                System.out.println("Updating Surprise to: "+count);
                surpriseCount.setText(Integer.toString(count));
                break;
            case "Joy":
                System.out.println("Updating Joy to: "+count);
                joyCount.setText(Integer.toString(count));
                break;
        }
        adapter.notifyDataSetChanged();
        saveInFile();

    }

    public void sortFeelings() {
        Collections.sort(feelingList, new Comparator<Feeling>(){
            public int compare(Feeling obj1, Feeling obj2) {
                // https://stackoverflow.com/questions/9109890/android-java-how-to-sort-a-list-of-objects-by-a-certain-value-within-the-object
                return obj1.getDateString().compareToIgnoreCase(obj2.getDateString()); // To compare string values
            }
        });
    }

}
