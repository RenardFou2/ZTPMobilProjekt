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
    String findWordByPolish(String polish);

    @Query("SELECT * FROM word WHERE english LIKE :english")
    String findWordByEnglish(String english);
}
