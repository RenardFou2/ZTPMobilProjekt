package com.example.ztpmobilprojekt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Spinner difficultySpinner, learningLanguageSpinner, myLanguageSpinner, languageSpinner;
    private Button settingsBackButton;

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
        languageSpinner = findViewById(R.id.languageSpinner);
        settingsBackButton = findViewById(R.id.settingsBackButton);

        // Set up Spinners
        setUpSpinner(difficultySpinner, R.array.difficulty_options, "difficulty");
        setUpSpinner(learningLanguageSpinner, R.array.learning_language_options, "learningLanguage");
        setUpSpinner(myLanguageSpinner, R.array.my_language_options, "myLanguage");
        setUpSpinner(languageSpinner, R.array.language_options, "appLanguage");
        SettingsUtil.initialize(this);

        settingsBackButton.setOnClickListener(view -> {
            Log.i("i",SettingsUtil.getAppLanguage());
            if(SettingsUtil.getAppLanguage().equals("EN")){
                setLocale("en");
            } else if (SettingsUtil.getAppLanguage().equals("PL")) {
                setLocale("pl");
            }

        });
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

    public void setLocale(String langCode){
        Locale myLocale = new Locale(langCode);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(myLocale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }
}