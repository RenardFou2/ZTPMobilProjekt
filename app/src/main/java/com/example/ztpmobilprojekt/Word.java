package com.example.ztpmobilprojekt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int difficulty;
    private String polish;
    private String english;

    public Word(int difficulty, String polish, String english) {
        this.difficulty = difficulty;
        this.polish = polish;
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getPolish() {
        return polish;
    }

    public String getEnglish() {
        return english;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
