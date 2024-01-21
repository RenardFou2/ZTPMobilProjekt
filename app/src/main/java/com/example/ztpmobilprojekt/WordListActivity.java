package com.example.ztpmobilprojekt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class WordListActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_WORD_ACTIVITY_REQUEST_CODE = 2;
    private Word editedWord = null;

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

        FloatingActionButton addBookButton = findViewById(R.id.add_button);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordListActivity.this, EditWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.word_list_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE) {
                Word word = new Word(Integer.parseInt(data.getStringExtra(EditWordActivity.EXTRA_EDIT_WORD_DIFFICULTY)),
                        data.getStringExtra(EditWordActivity.EXTRA_EDIT_WORD_POLISH),
                        data.getStringExtra(EditWordActivity.EXTRA_EDIT_WORD_ENGLISH));
                wordViewModel.insert(word);
                Snackbar.make(findViewById(R.id.coordinator_layout),
                                getString(R.string.word_added),
                                Snackbar.LENGTH_LONG)
                        .show();
            }
            else if (requestCode == EDIT_WORD_ACTIVITY_REQUEST_CODE) {
                editedWord.setDifficulty(Integer.parseInt(data.getStringExtra(EditWordActivity.EXTRA_EDIT_WORD_DIFFICULTY)));
                editedWord.setPolish(data.getStringExtra(EditWordActivity.EXTRA_EDIT_WORD_POLISH));
                editedWord.setEnglish(data.getStringExtra(EditWordActivity.EXTRA_EDIT_WORD_ENGLISH));
                wordViewModel.update(editedWord);
                editedWord = null;
                Snackbar.make(findViewById(R.id.coordinator_layout),
                                getString(R.string.word_edited),
                                Snackbar.LENGTH_LONG)
                        .show();
            }
        }
        else
            Snackbar.make(findViewById(R.id.coordinator_layout),
                            getString(R.string.empty_not_saved),
                            Snackbar.LENGTH_LONG)
                    .show();
    }


    private class WordHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView wordDifficultyTextView;
        private TextView wordPolishTextView;
        private TextView wordEnglishTextView;
        private Word word;

        public WordHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.word_list_item, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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

        @Override
        public void onClick(View v) {
            WordListActivity.this.editedWord = this.word;
            Intent intent = new Intent(WordListActivity.this, EditWordActivity.class);
            intent.putExtra(EditWordActivity.EXTRA_EDIT_WORD_DIFFICULTY, word.getDifficulty());
            intent.putExtra(EditWordActivity.EXTRA_EDIT_WORD_POLISH, word.getPolish());
            intent.putExtra(EditWordActivity.EXTRA_EDIT_WORD_ENGLISH, word.getEnglish());
            startActivityForResult(intent, EDIT_WORD_ACTIVITY_REQUEST_CODE);
        }

        @Override
        public boolean onLongClick(View v) {
            WordListActivity.this.wordViewModel.delete(this.word);
            return true;
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