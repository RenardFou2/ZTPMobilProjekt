package com.example.ztpmobilprojekt;

import java.util.ArrayList;
import java.util.List;

public class TranslateLevelBuilder implements ILevelBuilder{
    List<Pair> result = new ArrayList<Pair>();
    WordRepository wordRepository;
    List<Word> candidates;
    Word lastSelected;
    @Override
    public void setDatabase(WordRepository wordRepository) {
        this.wordRepository=wordRepository;
    }
    @Override
    public String createQuestion(Difficulty difficulty, String language1) {
        if (candidates == null)
            candidates = wordRepository.findWordsByDifficulty(difficulty.ordinal());
        if (!candidates.isEmpty()) {
            int randomIndex = (int) (Math.random() * candidates.size());
            Word selectedWord = candidates.get(randomIndex);
            candidates.remove(randomIndex);
            lastSelected = selectedWord;
            if ("polish".equalsIgnoreCase(language1)) { // if new language add there
                return selectedWord.getPolish();
            } else if ("english".equalsIgnoreCase(language1)) {
                return selectedWord.getEnglish();
            }
        }
            return "Unsupported language: " + language1;
    }
    @Override
    public void createPair(String question, String answer) {
        result.add(new Pair(question,answer));
    }
    @Override
    public String createAnswer(String language2) {
        String answer;
        if ("polish".equalsIgnoreCase(language2)) { // if new language add there
            answer = lastSelected.getPolish();
        } else if ("english".equalsIgnoreCase(language2)) {
            answer = lastSelected.getEnglish();
        } else {
            return "Unsupported language: " + language2;
        }
        return answer;
    }
    @Override
    public List<Pair> getList() {
        return result;
    }
}
