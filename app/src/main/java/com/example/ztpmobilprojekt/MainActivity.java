package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button wordListButton;
    private Button startQuizButton;
    private Button settingsButton;
    private Button translateButton;
    private Button luckyWordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SettingsUtil.initialize(this);

        wordListButton = findViewById(R.id.wordListButton);
        startQuizButton = findViewById(R.id.startQuizButton);
        settingsButton = findViewById(R.id.settingsButton);
        translateButton = findViewById(R.id.translateButton);
        luckyWordButton = findViewById(R.id.luckyButton);


        wordListButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WordListActivity.class);
            startActivity(intent);
        });
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizChoiceActivity.class);
                startActivity(intent);
            }
        });
        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            Log.d("SettingsActivity", "Difficulty: " + SettingsUtil.getDifficulty());
        });

        translateButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(intent);

        });

        luckyWordButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LuckyWordActivity.class);
            startActivity(intent);

        });



    }
}