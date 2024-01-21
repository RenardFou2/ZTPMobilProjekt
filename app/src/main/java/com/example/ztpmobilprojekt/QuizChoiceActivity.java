package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class QuizChoiceActivity extends AppCompatActivity {

    private Button learnChoiceBtn;
    private Button testChoiceBtn;
    private Spinner modeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_choice);

        learnChoiceBtn = findViewById(R.id.learn_choice_btn);
        testChoiceBtn = findViewById(R.id.test_choice_btn);
        modeSpinner = findViewById(R.id.mode_spinner);

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

   /* private void setUpSpinner(Spinner spinner){
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.mode_options,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner.setAdapter(adapter);




        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            // An item is selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos).
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback.
        }
    }*/
}
