package com.example.vocabapk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button verbs, adverbs, adjectives, idioms, userList, reminder, playQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        playQuiz = findViewById(R.id.playQuiz);
        reminder = findViewById(R.id.reminder);
        verbs = findViewById(R.id.verbs);
        adverbs = findViewById(R.id.adverbs);
        adjectives = findViewById(R.id.adjectives);
        idioms = findViewById(R.id.idioms);
        userList = findViewById(R.id.userList);
        verbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Adjectives.class);
                intent.putExtra("Category", "verb");
                startActivity(intent);

            }
        });
        adverbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Adjectives.class);
                intent.putExtra("Category", "adverb");
                startActivity(intent);
            }
        });
        adjectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Adjectives.class);
                intent.putExtra("Category", "adjective");
                startActivity(intent);
            }
        });
        idioms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Adjectives.class);
                intent.putExtra("Category", "idiom");
                startActivity(intent);
            }
        });
        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Adjectives.class);
                intent.putExtra("Category", "user");
                startActivity(intent);
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Word Reminder!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();
                long threeMinutes = 1 * 60 * 1000;
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick+threeMinutes, pendingIntent);

            }
        });
        playQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("Category", "adjective");
                startActivity(intent);
            }
        });


    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name  = "ReminderChannel";
            String des = "Channel for Word Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyUserChannel", name, importance);
            channel.setDescription(des);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}