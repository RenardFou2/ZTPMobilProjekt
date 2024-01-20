package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizSummaryActivity extends AppCompatActivity {

    Button quizSumBtn;
    TextView quizScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_summary);

        quizSumBtn = findViewById(R.id.quiz_summary_button);
        quizScoreTextView = findViewById(R.id.quiz_summary_score);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            quizScoreTextView.setText(String.valueOf(extras.getInt("endScore"))+"/"+String.valueOf(extras.getInt("maxScore")));

        quizSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizSummaryActivity.this, QuizChoiceActivity.class);
                startActivity(i);
            }
        });
    }//TODO zmienic petle
}