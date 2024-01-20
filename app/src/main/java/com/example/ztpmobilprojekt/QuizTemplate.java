package com.example.ztpmobilprojekt;

public abstract class QuizTemplate {

    private String word;
    private String answer;

    public void check(){

        if(verify()== true){
            addScore();
        }
        nextWord();
    }

    public boolean verify(){

        return false;
    }

    public abstract void nextWord();

    public void addScore(){

    }
}
