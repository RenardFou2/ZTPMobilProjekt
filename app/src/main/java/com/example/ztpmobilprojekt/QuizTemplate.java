package com.example.ztpmobilprojekt;

public abstract class QuizTemplate {

    private static int score = 0;
    private String rightAnswer;
    private String userAnswer;


    public boolean check(){

        if(verify()){
            addScore();
        }
        return nextWord();
    }

    public boolean verify(){

        if(rightAnswer.equals(userAnswer)) return true;

        return false;
    }

    public abstract boolean nextWord();

    public static void addScore(){
        score++;
    }
    public void setRightAnswer(String rightAnswer){
        this.rightAnswer = rightAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

}
