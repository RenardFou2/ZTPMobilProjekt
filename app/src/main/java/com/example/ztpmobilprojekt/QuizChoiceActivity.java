package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizChoiceActivity extends AppCompatActivity {

    private Button learnChoiceBtn;
    private Button testChoiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_choice);

        learnChoiceBtn = findViewById(R.id.learn_choice_btn);
        testChoiceBtn = findViewById(R.id.test_choice_btn);

        learnChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int choice=0;
                Intent i = new Intent(QuizChoiceActivity.this, QuizActivity.class);
                i.putExtra("key",choice);
                startActivity(i);
            }
        });

        testChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int choice=1;
                Intent i = new Intent(QuizChoiceActivity.this, QuizActivity.class);
                i.putExtra("key",choice);
                startActivity(i);
            }
        });
    }
}