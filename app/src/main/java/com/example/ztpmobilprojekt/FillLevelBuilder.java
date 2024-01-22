package com.example.ztpmobilprojekt;

import static com.example.ztpmobilprojekt.WordDatabase.getDatabase;

import java.util.ArrayList;
import java.util.List;

public class FillLevelBuilder implements ILevelBuilder {
    List<Pair> result = new ArrayList<Pair>();
    WordRepository wordRepository;
    List<Word> candidates;
    Word lastSelected;
    String learnLanguage;


    @Override
    public void setDatabase(WordRepository wordRepository) {
        this.wordRepository=wordRepository;
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
    public void createPair(String question, String answer) {
result.add(new Pair(question,answer));
    }
    @Override
    public String createQuestion(Difficulty difficulty) {
        if(candidates==null) candidates = wordRepository.findWordsByDifficulty(difficulty.ordinal());
        if (!candidates.isEmpty()) {
            int randomIndex = (int) (Math.random() * candidates.size());
            Word selectedWord = candidates.get(randomIndex);
            candidates.remove(randomIndex);
            lastSelected = selectedWord;
            String questionPreMod;
            if ("polish".equalsIgnoreCase(learnLanguage)) { // if new language add there
                questionPreMod = selectedWord.getPolish();
            } else if ("english".equalsIgnoreCase(learnLanguage)) {
                questionPreMod = selectedWord.getEnglish();
            } else {
                return "Unsupported language: " + learnLanguage;
            }
            double percentageToHide = 0.35; // 30% of letters will be hidden
            int lettersToHide = (int) (questionPreMod.length() * percentageToHide);
            List<Integer> indicesToHide = new ArrayList<>();
            while (indicesToHide.size() < lettersToHide) {
                int randomIndexToHide = (int) (Math.random() * questionPreMod.length());
                if (!indicesToHide.contains(randomIndexToHide)) {
                    indicesToHide.add(randomIndexToHide);
                }
            }
            // Create a masked version of the word with underscores at specified positions
            StringBuilder maskedWord = new StringBuilder(questionPreMod);
            for (int index : indicesToHide) {
                maskedWord.setCharAt(index, '_');
            }
            String question = maskedWord.toString();
            return question;
        } else {
            return "No words available for the specified difficulty.";
        }
    }
    @Override
    public void setLanguage(String learn, String my){
        this.learnLanguage = learn;
    }
    @Override
    public List<Pair> getList() {
        return result;
    }
}
