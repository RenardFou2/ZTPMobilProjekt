package com.example.ztpmobilprojekt;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordRepository {
    private final WordDAO wordDao;
    private final LiveData<List<Word>> words;


    public WordRepository(Application application) {
        WordDatabase database =  WordDatabase.getDatabase(application);
        wordDao = database.wordDao();
        words = wordDao.findAll();
    }

    LiveData<List<Word>> findAll() {return words;}

    void insert(Word word){
        WordDatabase.databaseWriteExecutor.execute(() -> wordDao.insert(word));
    }
    void update(Word word){
        WordDatabase.databaseWriteExecutor.execute(() -> wordDao.update(word));
    }
    void delete(Word word){
        WordDatabase.databaseWriteExecutor.execute(() -> wordDao.delete(word));
    }

    /*
    void findWordsByDifficulty(int dif){
        WordDatabase.databaseWriteExecutor.execute(() -> wordDao.findWordsByDifficulty(dif));
    }
    */

    String findWordByPolish(String polish) throws ExecutionException, InterruptedException {
        return WordDatabase.databaseWriteExecutor.submit(() -> wordDao.findWordByPolish(polish)).get();
    }
    String findWordByEnglish(String english) throws ExecutionException, InterruptedException {
        return WordDatabase.databaseWriteExecutor.submit(() -> wordDao.findWordByEnglish(english)).get();
    }
}
