package com.example.ztpmobilprojekt;
import java.util.List;


public interface ILevelBuilder
{
    public String createQuestion(Difficulty difficulty, String language1);
public String createAnswer(String language2);
// public Pair createPair(String question, String answer);
public List<Pair> getList();
}
