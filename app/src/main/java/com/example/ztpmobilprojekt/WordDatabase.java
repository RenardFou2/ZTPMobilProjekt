package com.example.ztpmobilprojekt;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase databaseInstance;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public abstract WordDAO wordDao();

    static WordDatabase getDatabase(final Context context){
        if (databaseInstance == null){
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WordDatabase.class, "word_database")
                    .addCallback(roomDatabaseCallback)
                    .build();
        }
        return databaseInstance;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                WordDAO dao = databaseInstance.wordDao();
                Word word = new Word(1, "Polski", "Polish");
                Word word2 = new Word(1, "Angielski", "English");
                Word word3 = new Word(1, "Dzień dobry", "Hello");
                dao.insert(word);
                dao.insert(word2);
                dao.insert(word3);
            });
        }
    };
}
