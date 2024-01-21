package com.example.ztpmobilprojekt;
import java.util.ArrayList;
import java.util.List;


public interface ILevelBuilder
{ public String createQuestion(Difficulty difficulty, String language1);
public String createAnswer(String language2);
public void createPair(String question, String Answer);
public void setDatabase(WordRepository wordRepository);
public List<Pair> getList();
}

