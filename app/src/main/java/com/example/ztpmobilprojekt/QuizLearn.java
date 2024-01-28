package com.example.ztpmobilprojekt;

public class QuizLearn extends QuizTemplate{

    public boolean nextWord(){

        if(verify()){
            return true;
        }
        return false;
    }
}
