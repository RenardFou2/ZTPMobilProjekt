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
                    .allowMainThreadQueries()
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
                // Difficulty 0 (Easy)
                Word word1 = new Word(0, "kot", "cat");
                Word word2 = new Word(0, "pies", "dog");
                Word word3 = new Word(0, "słońce", "sun");
                Word word4 = new Word(0, "drzewo", "tree");
                Word word5 = new Word(0, "woda", "water");
                Word word6 = new Word(0, "szczęśliwy", "happy");
                Word word7 = new Word(0, "przyjaciel", "friend");
                Word word8 = new Word(0, "dzień", "day");
                Word word9 = new Word(0, "książka", "book");
                Word word10 = new Word(0, "niebieski", "blue");

                // Difficulty 1 (Medium)
                Word word11 = new Word(1, "komputer", "computer");
                Word word12 = new Word(1, "ogród", "garden");
                Word word13 = new Word(1, "góra", "mountain");
                Word word14 = new Word(1, "szczęście", "happiness");
                Word word15 = new Word(1, "podróż", "travel");
                Word word16 = new Word(1, "język", "language");
                Word word17 = new Word(1, "ćwiczenie", "exercise");
                Word word18 = new Word(1, "przygoda", "adventure");
                Word word19 = new Word(1, "piękny", "beautiful");
                Word word20 = new Word(1, "wyzwanie", "challenge");

                // Difficulty 2 (Hard)
                Word word21 = new Word(2, "efemeryczny", "ephemeral");
                Word word22 = new Word(2, "podstępny", "surreptitious");
                Word word23 = new Word(2, "dona kichota", "quixotic");
                Word word24 = new Word(2, "szkodliwy", "pernicious");
                Word word25 = new Word(2, "powszechny", "ubiquitous");
                Word word26 = new Word(2, "idiosynkrazja", "idiosyncrasy");
                Word word27 = new Word(2, "lizus", "sycophant");
                Word word28 = new Word(2, "zaciemniać", "obfuscate");
                Word word29 = new Word(2, "kakofonia", "cacophony");
                Word word30 = new Word(2, "ezoteryczny", "esoteric");

                // Inserting into the database
                dao.insert(word1);
                dao.insert(word2);
                dao.insert(word3);
                dao.insert(word4);
                dao.insert(word5);
                dao.insert(word6);
                dao.insert(word7);
                dao.insert(word8);
                dao.insert(word9);
                dao.insert(word10);
                dao.insert(word11);
                dao.insert(word12);
                dao.insert(word13);
                dao.insert(word14);
                dao.insert(word15);
                dao.insert(word16);
                dao.insert(word17);
                dao.insert(word18);
                dao.insert(word19);
                dao.insert(word20);
                dao.insert(word21);
                dao.insert(word22);
                dao.insert(word23);
                dao.insert(word24);
                dao.insert(word25);
                dao.insert(word26);
                dao.insert(word27);
                dao.insert(word28);
                dao.insert(word29);
                dao.insert(word30);
            });
        }
    };
}
