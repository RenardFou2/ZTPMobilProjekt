package com.example.ztpmobilprojekt;

import java.util.List;

public class LevelDirector {
    ILevelBuilder Builder;
    LevelDirector(ILevelBuilder builder)
    {
        Builder=builder;
    }
    public List<Pair> makeLevel(Difficulty difficulty, int length,String language1, String language2)
    {
        for(int i=0; i<length;i++)
        {
            Builder.createQuestion(difficulty,language1);
            Builder.createAnswer(language2);
        }
        return Builder.getList();
    }
}
