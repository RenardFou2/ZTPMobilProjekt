package com.example.ztpmobilprojekt;

public class Pair {
    private String Question;
    private String Answer;

    public Pair(String question, String answer)
    {
       Question=question;
       Answer=answer;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getAnswer() {
        return Answer;
    }

    public String getQuestion() {
        return Question;
    }
}
