package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button checkButton;
    private TextView TextViewQuestion;
    private EditText EditTextAnswer;
    private int currentIndex = 0;
    QuizTemplate quizType;





    //LevelDirector director = new LevelDirector(FillLevelBuilder());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        checkButton = findViewById(R.id.checkBtn);
        TextViewQuestion = findViewById(R.id.question_word);
        EditTextAnswer = findViewById(R.id.answer);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            switch(extras.getInt("key")){
                case 0: {
                    quizType = new QuizLearn();
                    break;
                }
                case 1: {
                    quizType = new QuizTest();
                    break;
                }
            }
        }

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quizType.setRightAnswer(pairs[currentIndex].getAnswer());
                quizType.setUserAnswer(EditTextAnswer.getText().toString());

                if(quizType.check()){

                    currentIndex = (currentIndex+1);

                    if(currentIndex == pairs.length){
                        int endScore = quizType.getScore();
                        int maxScore = pairs.length;
                        Intent i = new Intent(QuizActivity.this, QuizSummaryActivity.class);
                        i.putExtra("endScore",endScore);
                        i.putExtra("maxScore",maxScore);
                        startActivity(i);
                        quizType.setScore(0);

                    }
                    if(currentIndex< pairs.length) setNextQuestion();

                }
                else {
                    Toast.makeText(QuizActivity.this,"Zla odpowiedz",Toast.LENGTH_SHORT).show();
                }
                EditTextAnswer.setText("");

            }
        });
        TextViewQuestion.setText(pairs[currentIndex].getQuestion());
    }
    public void setNextQuestion(){
        TextViewQuestion.setText(pairs[currentIndex].getQuestion());
    }
    private Pair[] pairs = {
            new Pair("kupic", "buy"),
            new Pair("sprzedac", "sell"),
            new Pair("porzyczyc", "borrow"),
            new Pair("znalezc","find")
    };  //TODO zmienic mockup pytan
}