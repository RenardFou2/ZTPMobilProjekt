package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class QuizChoiceActivity extends AppCompatActivity {

    private Button learnChoiceBtn;
    private Button testChoiceBtn;
    private Spinner quizSpinner;
    int quizChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_choice);

        learnChoiceBtn = findViewById(R.id.learn_choice_btn);
        testChoiceBtn = findViewById(R.id.test_choice_btn);
        quizSpinner = findViewById(R.id.quiz_spinner);

        learnChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int modeChoice=0;
                Intent i = new Intent(QuizChoiceActivity.this, QuizActivity.class);
                i.putExtra("modeChoice",modeChoice);
                i.putExtra("quizChoice",quizChoice);
                startActivity(i);
            }
        });

        testChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("1",String.valueOf(quizChoice));
                int modeChoice=1;
                Intent i = new Intent(QuizChoiceActivity.this, QuizActivity.class);
                i.putExtra("modeChoice",modeChoice);
                i.putExtra("quizChoice",quizChoice);
                startActivity(i);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.mode_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quizSpinner.setAdapter(adapter);
        quizSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(quizSpinner.getSelectedItem().equals("Fill the blanks")){
                    quizChoice = 0;
                }
                else quizChoice =1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
