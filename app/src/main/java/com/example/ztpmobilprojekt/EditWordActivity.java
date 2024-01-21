package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class EditWordActivity extends AppCompatActivity {

    public static final String EXTRA_EDIT_WORD_DIFFICULTY = "com.example.EDIT_WORD_DIFFICULTY";
    public static final String EXTRA_EDIT_WORD_POLISH = "com.example.EDIT_WORD_POLISH";
    public static final String EXTRA_EDIT_WORD_ENGLISH = "com.example.EDIT_WORD_ENGLISH";

    private EditText editDifficultyEditText;
    private EditText editPolishEditText;
    private EditText editEnglishEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        editDifficultyEditText = findViewById(R.id.edit_word_difficulty);
        editPolishEditText = findViewById(R.id.edit_word_polish);
        editEnglishEditText = findViewById(R.id.edit_word_english);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if(TextUtils.isEmpty(editDifficultyEditText.getText())
                    || TextUtils.isEmpty(editPolishEditText.getText())
                    || TextUtils.isEmpty(editEnglishEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String diff = editDifficultyEditText.getText().toString();
                replyIntent.putExtra(EXTRA_EDIT_WORD_DIFFICULTY, diff);
                String polish = editPolishEditText.getText().toString();
                replyIntent.putExtra(EXTRA_EDIT_WORD_POLISH, polish);
                String english = editEnglishEditText.getText().toString();
                replyIntent.putExtra(EXTRA_EDIT_WORD_ENGLISH, english);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}