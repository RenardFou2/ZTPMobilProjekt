package com.example.ztpmobilprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class LuckyWordActivity extends AppCompatActivity {

    private TextView luckyWordLearn;
    private TextView luckyWordMy;
    private TextView luckyWordTextView;
    private List<Word> words;
    private WordRepository wordRepository;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_word);

        luckyWordLearn = findViewById(R.id.luckyWordLearnTextView);
        luckyWordMy = findViewById(R.id.luckyWordMyTextView);
        luckyWordTextView = findViewById(R.id.luckyWordTextView);

        wordRepository = new WordRepository(getApplication());
        words = wordRepository.findWordsByDifficulty(SettingsUtil.getDifficulty().ordinal());


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if(sensorEvent != null){

                    float xAcc = sensorEvent.values[0];
                    float yAcc = sensorEvent.values[1];
                    float zAcc = sensorEvent.values[2];

                    if(     xAcc > 12 || xAcc < -12 ||
                            yAcc > 12 || yAcc < -12 ||
                            zAcc > 12 || zAcc < -12 ){

                        int randomIndex = (int) (Math.random() * words.size());
                        Word selectedWord = words.get(randomIndex);
                        luckyWordTextView.setVisibility(View.VISIBLE);

                        if(SettingsUtil.getLearningLanguage().equals("English")){
                            luckyWordLearn.setText(selectedWord.getEnglish());
                            luckyWordMy.setText(selectedWord.getPolish());
                        }
                        else{
                            luckyWordLearn.setText(selectedWord.getPolish());
                            luckyWordMy.setText(selectedWord.getEnglish());
                        }

                    }

                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        sensorManager.registerListener(sensorEventListener, sensorShake, SensorManager.SENSOR_DELAY_NORMAL);

    }
}