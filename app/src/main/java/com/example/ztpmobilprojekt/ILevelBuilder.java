package com.example.ztpmobilprojekt;
import java.util.ArrayList;
import java.util.List;


public interface ILevelBuilder
{ public String createQuestion(Difficulty difficulty);
public String createAnswer();
public void createPair(String question, String Answer);
public void setDatabase(WordRepository wordRepository);
public void setLanguage(String learn, String my);
public List<Pair> getList();
}

