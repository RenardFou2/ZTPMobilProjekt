package com.example.ztpmobilprojekt;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordViewModel extends AndroidViewModel {

    private final WordRepository wordRepository;
    private LiveData<List<Word>> words;
    private Word wordTemp = null;
    private List<Word> currentWordState;
    private final List<WordOperation> operationHistory;

    public WordViewModel(@NonNull Application application){
        super(application);
        wordRepository = new WordRepository(application);
        words = wordRepository.findAll();
        currentWordState = new ArrayList<>();
        operationHistory = new ArrayList<>();

        words.observeForever(new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> newWords) {
                currentWordState.clear();
                currentWordState.addAll(newWords);
            }
        });
    }

    public LiveData<List<Word>> findAll() { return words; }
    public void insert(Word word) throws ExecutionException, InterruptedException {
        wordRepository.insert(word);

        wordTemp = findWordByParams(word.getDifficulty(), word.getPolish(), word.getEnglish());
        Log.d("database", "Inserted word with ID "+wordTemp.getId());
        operationHistory.add(new WordOperation(WordOperation.Type.ADD, wordTemp));
        updateCurrentState();
    }
    public void update(Word word, Word oldWord) throws ExecutionException, InterruptedException {
        wordRepository.update(word);
        wordTemp = findWordByParams(word.getDifficulty(), word.getPolish(), word.getEnglish());
        Log.d("database", "updated word has ID "+wordTemp.getId());
        oldWord.setId(wordTemp.getId());
        operationHistory.add(new WordOperation(WordOperation.Type.MODIFY, oldWord));
        updateCurrentState();
    }
    public void delete(Word word){
        Log.d("database", "deleting word with ID "+word.getId());
        wordRepository.delete(word);
        operationHistory.add(new WordOperation(WordOperation.Type.REMOVE, word));
        updateCurrentState();
    }
    public Word findWordByPolish(String Polish) throws ExecutionException, InterruptedException {return wordRepository.findWordByPolish(Polish);}
    public Word findWordByEnglish(String English) throws ExecutionException, InterruptedException {return wordRepository.findWordByEnglish(English);}

    public Word findWordByParams(int difficulty, String polish, String english) throws ExecutionException, InterruptedException {
        return wordRepository.findWordByParams(difficulty,polish,english);
    }
    public WordsMemento saveStateToMemento() {
        return new WordsMemento(currentWordState);
    }

    public void restoreStateFromMemento(WordsMemento memento) {
        wordRepository.deleteAll();
        currentWordState.clear();
        currentWordState.addAll(memento.getWords());

        for (int i = 0; i< memento.getWords().size(); i++){
            wordRepository.insert(memento.getWords().get(i));
        }

        words = new MutableLiveData<>(currentWordState);
        updateCurrentState();
    }
    private void updateCurrentState() {
        words.getValue(); // Trigger LiveData observers
    }

    public void undo() {
        if (!operationHistory.isEmpty()) {
            WordOperation lastOperation = operationHistory.remove(operationHistory.size() - 1);
            reverseOperation(lastOperation);
        }
    }

    private void reverseOperation(WordOperation operation) {
        switch (operation.getType()) {
            case ADD:
                Log.d("database", "undoing add word with ID "+operation.getWord().getId());
                wordRepository.delete(operation.getWord());
                break;
            case REMOVE:
                Log.d("database", "undoing remove word with ID "+operation.getWord().getId());
                wordRepository.insert(operation.getWord());
                break;
            case MODIFY:
                Log.d("database", "undoing modify word with ID "+operation.getWord().getId());
                wordRepository.update(operation.getWord());
                break;
        }
    }
}
