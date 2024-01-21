package com.example.ztpmobilprojekt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Spinner difficultySpinner, learningLanguageSpinner, myLanguageSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize Spinners
        difficultySpinner = findViewById(R.id.difficultySpinner);
        learningLanguageSpinner = findViewById(R.id.learningLanguageSpinner);
        myLanguageSpinner = findViewById(R.id.myLanguageSpinner);

        // Set up Spinners
        setUpSpinner(difficultySpinner, R.array.difficulty_options, "difficulty");
        setUpSpinner(learningLanguageSpinner, R.array.learning_language_options, "learningLanguage");
        setUpSpinner(myLanguageSpinner, R.array.my_language_options, "myLanguage");
        SettingsUtil.initialize(this);
    }

    private void setUpSpinner(Spinner spinner, int arrayResId, final String key) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set the saved value, default to the first item if not found
        String savedValue = sharedPreferences.getString(key, getResources().getStringArray(arrayResId)[0]);
        int position = adapter.getPosition(savedValue);
        spinner.setSelection(position);

        // Set listener to save selected value
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("SettingsActivity", "Difficulty: " + SettingsUtil.getDifficulty());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key, parentView.getItemAtPosition(position).toString());
                editor.apply();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }
}