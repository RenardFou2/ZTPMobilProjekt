package com.example.ztpmobilprojekt;
import java.util.List;


public interface ILevelBuilder
{ public String createQuestion(Difficulty difficulty, String language1);

public String createAnswer(String language2);
public void createPair(String question, String Answer);
public void setDatabase(WordRepository wordRepository);
// public Pair createPair(String question, String answer);
public List<Pair> getList();
}

