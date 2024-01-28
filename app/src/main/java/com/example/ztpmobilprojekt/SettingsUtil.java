package com.example.ztpmobilprojekt;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsUtil {

    private static SharedPreferences sharedPreferences;
    public static void initialize(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Difficulty getDifficulty() {
        String difficultyString = sharedPreferences.getString("difficulty", Difficulty.Easy.name());
        return Difficulty.valueOf(difficultyString);
    }

    public static String getLearningLanguage() {
        return sharedPreferences.getString("learningLanguage", "English");
    }

    public static String getMyLanguage() {
        return sharedPreferences.getString("myLanguage", "Polish");
    }
}