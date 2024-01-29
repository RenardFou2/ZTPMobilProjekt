package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
            doAnimation(wordListButton);
            Intent intent = new Intent(MainActivity.this, WordListActivity.class);
            startActivity(intent);
        });
        startQuizButton.setOnClickListener(view -> {

            doAnimation(startQuizButton);
            Intent intent = new Intent(MainActivity.this, QuizChoiceActivity.class);
            startActivity(intent);

        });
        settingsButton.setOnClickListener(view -> {

            doAnimation(settingsButton);
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            Log.d("SettingsActivity", "Difficulty: " + SettingsUtil.getDifficulty());
        });

        translateButton.setOnClickListener(view -> {

            doAnimation(translateButton);
            Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(intent);

        });

        luckyWordButton.setOnClickListener(view -> {

            doAnimation(luckyWordButton);
            Intent intent = new Intent(MainActivity.this, LuckyWordActivity.class);
            startActivity(intent);

        });

    }
    public void doAnimation(Button button){
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
        button.startAnimation(animation);
        SystemClock.sleep(300); //ms
    }
}