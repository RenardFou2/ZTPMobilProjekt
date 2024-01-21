package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button wordListButton;
    private Button startQuizButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordListButton = findViewById(R.id.wordListButton);
        startQuizButton = findViewById(R.id.startQuizButton);
        settingsButton = findViewById(R.id.settingsButton);

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
        });



    }
}