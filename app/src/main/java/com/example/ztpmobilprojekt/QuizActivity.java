package com.example.ztpmobilprojekt;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private Button checkButton;
    private TextView textViewQuestion;
    private Button textButton;
    private EditText editTextAnswer;
    private int currentIndex = 0;

    QuizTemplate quizType;
    WordRepository repository;
    ILevelBuilder builder;
    List<Pair> pairs;
    LevelDirector director;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Locale.getDefault();

        checkButton = findViewById(R.id.checkBtn);
        textViewQuestion = findViewById(R.id.question_word);
        editTextAnswer = findViewById(R.id.answer);
        textButton = findViewById(R.id.exerciseText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            switch(extras.getInt("modeChoice")){
                case 0: {
                    quizType = new QuizLearn();
                    break;
                }
                case 1: {
                    quizType = new QuizTest();
                    break;
                }
                default:
                    break;
            }
        }
        if (extras != null) {
            switch(extras.getInt("quizChoice")){
                case 0: {
                    textButton.setText(R.string.fill_the_blanks);
                    builder = new FillLevelBuilder();
                    break;
                }
                case 1: {
                    textButton.setText(R.string.translate);
                    builder = new TranslateLevelBuilder();
                    break;
                }
                default:
                    break;
            }
        }

        repository = new WordRepository(getApplication());
        director = new LevelDirector(builder,repository);
        pairs = director.makeLevel(SettingsUtil.getDifficulty(),4,SettingsUtil.getLearningLanguage(), SettingsUtil.getMyLanguage());

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quizType.setRightAnswer(pairs.get(currentIndex).getAnswer());
                quizType.setUserAnswer(editTextAnswer.getText().toString());

                if(quizType.check()){

                    currentIndex = (currentIndex+1);

                    if(currentIndex == pairs.size()){
                        int endScore = quizType.getScore();
                        int maxScore = pairs.size();
                        Intent i = new Intent(QuizActivity.this, QuizSummaryActivity.class);
                        i.putExtra("endScore",endScore);
                        i.putExtra("maxScore",maxScore);
                        startActivity(i);
                        quizType.setScore(0);

                    }
                    if(currentIndex< pairs.size()) setNextQuestion();

                }
                else {
                    Toast.makeText(QuizActivity.this,"Zla odpowiedz",Toast.LENGTH_SHORT).show();
                }
                editTextAnswer.setText("");

            }
        });
        textViewQuestion.setText(pairs.get(currentIndex).getQuestion());
    }
    public void setNextQuestion(){
        textViewQuestion.setText(pairs.get(currentIndex).getQuestion());
    }

}