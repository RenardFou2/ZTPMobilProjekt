package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button wordListButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordListButton = findViewById(R.id.wordListButton);

        wordListButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WordListActivity.class);
            startActivity(intent);
        });
    }
}