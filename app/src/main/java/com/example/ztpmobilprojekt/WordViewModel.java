package com.example.ztpmobilprojekt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordViewModel extends AndroidViewModel {

    private final WordRepository wordRepository;
    private final LiveData<List<Word>> words;

    public WordViewModel(@NonNull Application application){
        super(application);
        wordRepository = new WordRepository(application);
        words = wordRepository.findAll();
    }

    public LiveData<List<Word>> findAll() { return words; }
    public void insert(Word word){ wordRepository.insert(word);}
    public void update(Word word){ wordRepository.update(word);}
    public void delete(Word word){ wordRepository.delete(word);}
    public Word findWordByPolish(String Polish) throws ExecutionException, InterruptedException {return wordRepository.findWordByPolish(Polish);}
    public Word findWordByEnglish(String English) throws ExecutionException, InterruptedException {return wordRepository.findWordByEnglish(English);}
}
