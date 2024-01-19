package com.example.ztpmobilprojekt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordAdapter adapter = new WordAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.findAll().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                adapter.setWords(words);
            }
        });
    }


    private class WordHolder extends RecyclerView.ViewHolder {
        private TextView wordDifficultyTextView;
        private TextView wordPolishTextView;
        private TextView wordEnglishTextView;
        private Word word;

        public WordHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.word_list_item, parent, false));

            wordDifficultyTextView = itemView.findViewById(R.id.word_difficulty);
            wordPolishTextView = itemView.findViewById(R.id.word_polish);
            wordEnglishTextView = itemView.findViewById(R.id.word_english);
        }

        public void bind(Word word) {
            this.word = word;
            wordDifficultyTextView.setText(Integer.toString(word.getDifficulty()));
            wordPolishTextView.setText(word.getPolish());
            wordEnglishTextView.setText(word.getEnglish());
        }
    }

    private class WordAdapter extends RecyclerView.Adapter<WordHolder> {
        private List<Word> words;

        @NonNull
        @Override
        public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new WordHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull WordHolder holder, int position) {
            if (words != null) {
                Word book = words.get(position);
                holder.bind(book);
            }
            else
                Log.d("WordListActivity", "No words");
        }

        @Override
        public int getItemCount() {
            if (words != null)
                return words.size();
            return 0;
        }

        void setWords(List<Word> words) {
            this.words = words;
            notifyDataSetChanged();
        }
    }
}