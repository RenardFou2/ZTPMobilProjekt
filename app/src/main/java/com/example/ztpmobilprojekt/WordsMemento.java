package com.example.ztpmobilprojekt;

import java.util.ArrayList;
import java.util.List;

public class WordsMemento {
    private final List<Word> words;

    public WordsMemento(List<Word> words) {
        this.words = new ArrayList<>(words);
    }

    public List<Word> getWords() {
        return words;
    }
}
