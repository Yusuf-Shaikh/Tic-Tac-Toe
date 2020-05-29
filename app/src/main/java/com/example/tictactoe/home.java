package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class home extends AppCompatActivity {
    private Button start;
    Vibrator vibrator;
    private EditText player1,player2;
    boolean con1 = false, con2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        start = (Button) findViewById(R.id.start);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        player1 = (EditText) findViewById(R.id.player1);
        player2 = (EditText) findViewById(R.id.player2);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkplayers();
                if(con1){
                    opengameactivity();
                }
            }
        });
    }
    public void checkplayers(){
        if (TextUtils.isEmpty(player1.getText().toString())) {
            Toast.makeText(this, "Enter player1 name", Toast.LENGTH_SHORT).show();
        }
        else {
            if (TextUtils.isEmpty(player2.getText().toString())) {
                Toast.makeText(this, "Enter player2 name", Toast.LENGTH_SHORT).show();
            }
            else {
                saveplayer1();
                saveplayer2();
                con1 = true;
            }
        }
    }
    public void saveplayer1() {
        String temp = player1.getText().toString();
        String player1name = temp.substring(0,1).toUpperCase()+temp.substring(1);
        SharedPreferences sharedpreferences = getSharedPreferences("player1",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("player1",player1name);
        editor.apply();
    }
    public void saveplayer2() {
        String temp = player2.getText().toString();
        String player2name = temp.substring(0,1).toUpperCase()+temp.substring(1);
        SharedPreferences sharedpreferences = getSharedPreferences("player2",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("player2",player2name);
        editor.apply();
    }
    public void opengameactivity() {
        Intent gameintent = new Intent(home.this,maingame.class);
        startActivity(gameintent);
    }
    @Override
    public void onBackPressed() {
    }
}
