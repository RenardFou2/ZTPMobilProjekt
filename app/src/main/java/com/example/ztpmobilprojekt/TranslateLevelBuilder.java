package com.example.ztpmobilprojekt;

import java.util.ArrayList;
import java.util.List;

public class TranslateLevelBuilder implements ILevelBuilder{
    List<Pair> result = new ArrayList<Pair>();
    WordRepository wordRepository;
    List<Word> candidates;
    Word lastSelected;
    String learnLanguage;
    String myLanguage;

    @Override
    public void setDatabase(WordRepository wordRepository) {
        this.wordRepository=wordRepository;
    }
    @Override
    public String createQuestion(Difficulty difficulty) {
        if (candidates == null)
            candidates = wordRepository.findWordsByDifficulty(difficulty.ordinal());
        if (!candidates.isEmpty()) {
            int randomIndex = (int) (Math.random() * candidates.size());
            Word selectedWord = candidates.get(randomIndex);
            candidates.remove(randomIndex);
            lastSelected = selectedWord;
            if ("polish".equalsIgnoreCase(myLanguage)) { // if new language add there
                return selectedWord.getPolish();
            } else if ("english".equalsIgnoreCase(myLanguage)) {
                return selectedWord.getEnglish();
            }
        }
            return "Unsupported language: " + myLanguage;
    }
    @Override
    public void createPair(String question, String answer) {
        result.add(new Pair(question,answer));
    }
    @Override
    public String createAnswer() {
        String answer;
        if ("polish".equalsIgnoreCase(learnLanguage)) { // if new language add there
            answer = lastSelected.getPolish();
        } else if ("english".equalsIgnoreCase(learnLanguage)) {
            answer = lastSelected.getEnglish();
        } else {
            return "Unsupported language: " + learnLanguage;
        }
        return answer;
    }

    @Override
    public void setLanguage(String learn, String my){
        this.myLanguage = my;
        this.learnLanguage = learn;
    }
    @Override
    public List<Pair> getList() {
        return result;
    }
}
