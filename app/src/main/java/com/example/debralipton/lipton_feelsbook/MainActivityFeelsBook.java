package com.example.debralipton.lipton_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFeelsBook extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView feelingHistory;

    ArrayList<Feeling> feelingList;
    ArrayAdapter<Feeling> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feels_book);

        bodyText = (EditText) findViewById(R.id.body);
        Button submitButton = (Button) findViewById(R.id.submitButton);


        feelingHistory = (ListView) findViewById(R.id.feelingHistory);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();

                Feeling feeling = new Feeling(text);
                feelingHistory.add(feeling);
                //tell adapter that something has changed to refresh list

                adapter.notifyDataSetChanged();

            }
        });



        }
}
