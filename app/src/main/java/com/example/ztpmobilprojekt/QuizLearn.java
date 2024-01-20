package com.example.ztpmobilprojekt;

import android.widget.TextView;

public class QuizLearn extends QuizTemplate{

    public boolean nextWord(){

        if(verify()){
            return true;
        }
        return false;
    }
}
