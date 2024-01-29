package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.os.Bundle;
import android.os.SystemClock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import java.util.concurrent.ExecutionException;

public class SearchWordActivity extends AppCompatActivity {

    Spinner languageSpinner;
    EditText searchWordEditText;
    CheckBox easyCB, mediumCB, hardCB;
    Button searchButton;
    List<Word> queryResult;
    List<Word> helperList;
    int searchChoice;
    WordRepository wordRepository;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        languageSpinner =findViewById(R.id.searchLanguageSpinner);
        searchWordEditText = findViewById(R.id.searchWordEditText);
        easyCB = findViewById(R.id.easyCheckBox);
        mediumCB = findViewById(R.id.mediumCheckBox);
        hardCB = findViewById(R.id.hardCheckBox);
        searchButton = findViewById(R.id.search_word_button);
        wordRepository = new WordRepository(getApplication());
        listView = findViewById(R.id.search_list_view);
        queryResult = wordRepository.findWordsByDifficulty(SettingsUtil.getDifficulty().ordinal());

        helperList = wordRepository.findWordsByDifficulty(0);
        helperList.addAll(wordRepository.findWordsByDifficulty(1));
        helperList.addAll(wordRepository.findWordsByDifficulty(2));


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.search_language_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(languageSpinner.getSelectedItem().equals("Polish")){
                    searchChoice = 0;
                }
                else searchChoice = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MyAdapter listAdapter = null;
        listAdapter = new MyAdapter(this, queryResult);
        listView.setAdapter(listAdapter);

        MyAdapter finalListAdapter = listAdapter;
        searchButton.setOnClickListener(view -> {
            doAnimation(searchButton,R.anim.fadeout);
            finalListAdapter.clearData();
            listView.setVisibility(View.VISIBLE);
            try {

                Gson gson= new Gson();
                String list = gson.toJson(helperList);
                if(!searchWordEditText.getText().toString().isEmpty()){
                    for (Word word:helperList) {
                        if(word.getPolish().equals(searchWordEditText.getText().toString()) || word.getEnglish().equals(searchWordEditText.getText().toString())){
                            makeQuery();
                            break;
                        }
                    }
                }else{
                    makeQuery();
                }




                searchWordEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                searchWordEditText.setText("");


            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finalListAdapter.notifyDataSetChanged();
            doAnimation(searchButton,R.anim.fadein);
        });
    }

    public void makeQuery() throws ExecutionException, InterruptedException {
        String queryWord = searchWordEditText.getText().toString();
        queryWord = queryWord.toLowerCase();
        if (queryWord.isEmpty()) {
            if (!easyCB.isChecked() && !mediumCB.isChecked() && !hardCB.isChecked()) {

            }
            if (easyCB.isChecked()) {
                queryResult.addAll(wordRepository.findWordsByDifficulty(0));
            }
            if (mediumCB.isChecked()) {
                queryResult.addAll(wordRepository.findWordsByDifficulty(1));
            }
            if (hardCB.isChecked()) {
                queryResult.addAll(wordRepository.findWordsByDifficulty(2));
            }
        } else if (searchChoice == 0) {
            queryResult.add(wordRepository.findWordByPolish(queryWord));
        } else if (searchChoice == 1) {
            queryResult.add(wordRepository.findWordByEnglish(queryWord));
        }
    }

        // else if ((searchChoice == 0 && !wordRepository.findWordByEnglish(queryWord).getEnglish().isEmpty())) return false; //TODO nie wiem jak to naprawic

       // else if((searchChoice == 1 && !wordRepository.findWordByPolish(queryWord).getPolish().isEmpty())) return false;

//        else if (searchChoice == 0) {
//            if ((easyCB.isChecked() && wordRepository.findWordByPolish(queryWord).getDifficulty() == 0)|| (!mediumCB.isChecked() && !hardCB.isChecked()) ) {
//                queryResult.add(wordRepository.findWordByPolish(queryWord)); return true;
//            } else if ((mediumCB.isChecked() && wordRepository.findWordByPolish(queryWord).getDifficulty() == 1) || (!easyCB.isChecked() && !hardCB.isChecked())) {
//                queryResult.add(wordRepository.findWordByPolish(queryWord)); return true;
//            } else if ((hardCB.isChecked() && wordRepository.findWordByPolish(queryWord).getDifficulty() == 2) || (!easyCB.isChecked() && !mediumCB.isChecked()) ){
//                queryResult.add(wordRepository.findWordByPolish(queryWord)); return true;
//            }
//            else {
//                return false;
//            }
//        }else if(searchChoice == 1){
//            if ((easyCB.isChecked() && wordRepository.findWordByEnglish(queryWord).getDifficulty() == 0) || (!mediumCB.isChecked() && !hardCB.isChecked())) {
//                queryResult.add(wordRepository.findWordByEnglish(queryWord)); return true;
//            } else if ((mediumCB.isChecked() && wordRepository.findWordByEnglish(queryWord).getDifficulty() == 1) || (!easyCB.isChecked() && !hardCB.isChecked())) {
//                queryResult.add(wordRepository.findWordByEnglish(queryWord)); return true;
//            } else if ((hardCB.isChecked() && wordRepository.findWordByEnglish(queryWord).getDifficulty() == 2) || (!easyCB.isChecked() && !mediumCB.isChecked()) ){
//                queryResult.add(wordRepository.findWordByEnglish(queryWord)); return true;
//            }
//            else {
//                return false;
//            }
//        }
//        return false;
    public void doAnimation(Button button,int anima){

    Animation animation = AnimationUtils.loadAnimation(SearchWordActivity.this,anima);
    button.startAnimation(animation);
    //SystemClock.sleep(100); //ms
}
    public class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Word> dataList;

        public MyAdapter(Context context, List<Word> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public void clearData() {
            dataList.clear();
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.word_list_item, parent, false);
            }

            TextView textViewDifficulty = convertView.findViewById(R.id.word_difficulty);
            TextView textViewPolish = convertView.findViewById(R.id.word_polish);
            TextView textViewEnglish = convertView.findViewById(R.id.word_english);

            Word word = dataList.get(position);

            //if(helperList.contains(word)) {
                textViewDifficulty.setText(String.valueOf(word.getDifficulty()));
                textViewPolish.setText(word.getPolish());
                textViewEnglish.setText(word.getEnglish());
            //}
            //else {
                //Toast.makeText(SearchWordActivity.this,R.string.search_error,Toast.LENGTH_SHORT).show();
            //}


            return convertView;
        }
    }
}
