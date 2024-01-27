package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DictionaryActivity extends AppCompatActivity {

    private EditText wordInput;
    private Button dictionaryButton;
    private TextView wordTextView;
    private TextView phoneticTextView;
    private TextView partOfSpeechTextView;
    private TextView definitionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        wordInput = findViewById(R.id.wordInput);
        dictionaryButton = findViewById(R.id.dictionaryButton);
        wordTextView = findViewById(R.id.wordTextView);
        phoneticTextView = findViewById(R.id.phoneticsTextView);
        partOfSpeechTextView = findViewById(R.id.partOfSpeechTextView);
        definitionTextView = findViewById(R.id.definitionTextView);


        dictionaryButton.setOnClickListener(view -> {

            wordInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
            DictionaryDataService dictionaryDataService = new DictionaryDataService(DictionaryActivity.this);

            dictionaryDataService.makeRequest(wordInput.getText().toString(), new DictionaryDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(DictionaryActivity.this,message,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        wordInput.setText("");
                        wordTextView.setText(dictionaryDataService.getWord());
                        phoneticTextView.setText(dictionaryDataService.getPhonetic());
                        partOfSpeechTextView.setText(dictionaryDataService.getPartOfSpeech());
                        definitionTextView.setText(dictionaryDataService.getDefinition());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            });



        });

    }



}