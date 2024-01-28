package com.example.ztpmobilprojekt;

import java.util.List;

public class LevelDirector {
    WordRepository wordRepository;
    ILevelBuilder Builder;
    LevelDirector(ILevelBuilder builder, WordRepository wordRepository)
    {
        Builder=builder; this.wordRepository=wordRepository;
    }

    public void setBuilder(ILevelBuilder builder) {
        Builder = builder;
    }

    public ILevelBuilder getBuilder() {
        return Builder;
    }

    public List<Pair> makeLevel(Difficulty difficulty, int length, String learnLanguage, String myLanguage)
    {
        Builder.setDatabase(wordRepository);
        Builder.setLanguage(learnLanguage,myLanguage);
        for(int i=0; i<length;i++)
        {
            Builder.createPair(
                    Builder.createQuestion(difficulty),
                    Builder.createAnswer()
            );
        }
        return Builder.getList();
    }
}
