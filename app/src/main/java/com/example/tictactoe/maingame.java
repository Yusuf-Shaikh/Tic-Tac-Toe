package com.example.tictactoe;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class maingame extends AppCompatActivity {
    private Button home ,undo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingame);
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlevelactivity();
            }
        });

    }
    public void openlevelactivity() {
        Intent quizintent = new Intent(maingame.this,home.class);
        startActivity(quizintent);
    }

    @Override
    public void onBackPressed() {
    }

}
