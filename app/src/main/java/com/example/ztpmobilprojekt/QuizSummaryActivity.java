package com.example.ztpmobilprojekt;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizSummaryActivity extends AppCompatActivity {

    Button quizSumBtn;
    TextView quizScoreTextView;

    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_summary);

        quizSumBtn = findViewById(R.id.quiz_summary_button);
        quizScoreTextView = findViewById(R.id.quiz_summary_score);
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
             if(ContextCompat.checkSelfPermission(QuizSummaryActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                 ActivityCompat.requestPermissions(QuizSummaryActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
             }
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            score =String.valueOf(extras.getInt("endScore"))+"/"+String.valueOf(extras.getInt("maxScore"));
            quizScoreTextView.setText(score);

            score = getResources().getString(R.string.quiz_summary_text) + " " + score;
            makeNotification(getResources().getString(R.string.notification_title),score);

        }


        quizSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizSummaryActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    public void makeNotification(String notificationTitle, String notificationText){
        String channelId = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.mipmap.ic_launcher_logo)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), NotificationCompat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
            if(notificationChannel == null){

                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelId,"Some description",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        notificationManager.notify(0,builder.build());

    }

}