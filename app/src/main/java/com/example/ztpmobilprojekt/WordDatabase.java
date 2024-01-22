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
                Word word1 = new Word(0, "Kot", "Cat");
                Word word2 = new Word(0, "Pies", "Dog");
                Word word3 = new Word(0, "Słońce", "Sun");
                Word word4 = new Word(0, "Drzewo", "Tree");
                Word word5 = new Word(0, "Woda", "Water");
                Word word6 = new Word(0, "Szczęśliwy", "Happy");
                Word word7 = new Word(0, "Przyjaciel", "Friend");
                Word word8 = new Word(0, "Dzień", "Day");
                Word word9 = new Word(0, "Książka", "Book");
                Word word10 = new Word(0, "Niebieski", "Blue");

                // Difficulty 1 (Medium)
                Word word11 = new Word(1, "Komputer", "Computer");
                Word word12 = new Word(1, "Ogród", "Garden");
                Word word13 = new Word(1, "Góra", "Mountain");
                Word word14 = new Word(1, "Szczęście", "Happiness");
                Word word15 = new Word(1, "Podróż", "Travel");
                Word word16 = new Word(1, "Język", "Language");
                Word word17 = new Word(1, "Ćwiczenie", "Exercise");
                Word word18 = new Word(1, "Przygoda", "Adventure");
                Word word19 = new Word(1, "Piękny", "Beautiful");
                Word word20 = new Word(1, "Wyzwanie", "Challenge");

                // Difficulty 2 (Hard)
                Word word21 = new Word(2, "Efemeryczny", "Ephemeral");
                Word word22 = new Word(2, "Podstępny", "Surreptitious");
                Word word23 = new Word(2, "Dona Kichota", "Quixotic");
                Word word24 = new Word(2, "Szkodliwy", "Pernicious");
                Word word25 = new Word(2, "Powszechny", "Ubiquitous");
                Word word26 = new Word(2, "Idiosynkrazja", "Idiosyncrasy");
                Word word27 = new Word(2, "Lizus", "Sycophant");
                Word word28 = new Word(2, "Zaciemniać", "Obfuscate");
                Word word29 = new Word(2, "Kakofonia", "Cacophony");
                Word word30 = new Word(2, "Ezoteryczny", "Esoteric");

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
