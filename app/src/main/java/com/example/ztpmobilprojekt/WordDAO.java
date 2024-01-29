package com.example.ztpmobilprojekt;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("DELETE FROM word")
    void deleteAll();

    @Query("SELECT * FROM word ORDER BY difficulty")
    LiveData<List<Word>> findAll();

    @Query("SELECT * FROM word WHERE difficulty LIKE :difficulty")
    List<Word> findWordsByDifficulty(int difficulty);

    @Query("SELECT * FROM word WHERE polish LIKE :polish")
    Word findWordByPolish(String polish);

    @Query("SELECT * FROM word WHERE english LIKE :english")
    Word findWordByEnglish(String english);


    @Query("SELECT * FROM word WHERE difficulty LIKE :difficulty AND polish LIKE :polish AND english LIKE :english")
    Word findWordByParams(int difficulty, String polish, String english);
}
