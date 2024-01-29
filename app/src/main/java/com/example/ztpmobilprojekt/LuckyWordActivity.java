package com.example.ztpmobilprojekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.Manifest;

import java.util.List;

public class LuckyWordActivity extends AppCompatActivity {

    private TextView luckyWordLearn;
    private TextView luckyWordMy;
    private TextView luckyWordTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private LocationManager locationManager;
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
        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);

        wordRepository = new WordRepository(getApplication());
        words = wordRepository.findWordsByDifficulty(SettingsUtil.getDifficulty().ordinal());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(LuckyWordActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(LuckyWordActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LuckyWordActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1 );
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                longitudeTextView.setText(String.valueOf(location.getLongitude()));
                latitudeTextView.setText(String.valueOf(location.getLatitude()));
            }
        });

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