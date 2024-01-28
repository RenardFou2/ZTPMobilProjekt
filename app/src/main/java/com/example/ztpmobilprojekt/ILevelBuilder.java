package com.example.ztpmobilprojekt;
import com.example.ztpmobilprojekt.Difficulty;
import com.example.ztpmobilprojekt.Pair;
import com.example.ztpmobilprojekt.WordRepository;

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

