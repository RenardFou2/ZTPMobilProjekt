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
    public void insert(Word word){
        Log.d("database", "Inserting word with ID "+word.getId());
        wordRepository.insert(word);
        operationHistory.add(new WordOperation(WordOperation.Type.ADD, word));
        updateCurrentState();
    }
    public void update(Word word, Word oldWord){
        Log.d("database", "updating word with ID "+word.getId()+" old word ID is "+oldWord.getId());
        wordRepository.update(word);
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

    public WordsMemento saveStateToMemento() {
        return new WordsMemento(currentWordState);
    }

    public void restoreStateFromMemento(WordsMemento memento) {
        currentWordState.clear();
        currentWordState.addAll(memento.getWords());
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
