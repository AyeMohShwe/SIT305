package com.ayemohshwe.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.EditText;
import android.os.Handler;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {

    TextView prev_rec, timer_rec, taskView;
    ImageButton startImageButton, pauseImageButton, stopImageButton;
    EditText user_Input;
    long seconds;
    Handler handler;
    NumberFormat timeFormatter;
    SharedPreferences sharedPreferences;
    String prevTime = "prevTime";
    String liveRunning = "liveRunning";
    String TextKey = "TextKey";
    String firstRunKey = "firstRunKey";
    boolean timerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prev_rec = findViewById(R.id.prev_rec);
        timer_rec = findViewById(R.id.timer_rec);
        taskView= findViewById(R.id.taskView);
        startImageButton = findViewById(R.id.startBtn);
        pauseImageButton = findViewById(R.id.pauseBtn);
        stopImageButton = findViewById(R.id.stopBtn);
        user_Input = findViewById(R.id.user_Input);
        handler = new Handler();
        timeFormatter = new DecimalFormat("00");
        sharedPreferences = getSharedPreferences("com.example.task41p", MODE_PRIVATE);

        boolean isRunning = false;

        DisplayLastTime(sharedPreferences.getLong(prevTime, 0), sharedPreferences.getString(TextKey, ""));

        if(savedInstanceState != null && !savedInstanceState.getBoolean(firstRunKey, true))
        {
            seconds = savedInstanceState.getLong(prevTime, 0);
            isRunning = savedInstanceState.getBoolean(liveRunning, false);
        }

        timer_rec.setText(GetTimeText(seconds));

        if(isRunning)
        {
            Start();
        }

        startImageButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Start();
            }
        });

        pauseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Pause();
            }
        });

        stopImageButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Stop();
            }
        });
    }

    private void DisplayLastTime(long lastSeconds, String prev_taskRec)
    {
        prev_rec.setText("You spent " + GetTimeText(lastSeconds) + " on " +  prev_taskRec + "  last time");
    }

    private void Start()
    {
        if(timerRunning)
        {
            return;
        }
        Timer.run();
        timerRunning = true;
    }

    private void Pause()
    {
        PauseTime();
        timerRunning = false;
    }

    private void Stop()
    {
        DisplayLastTime(seconds, user_Input.getText().toString());
        PauseTime();
        SaveData();
        seconds = 0;
        timer_rec.setText(GetTimeText(seconds));
        timerRunning = false;
    }

    private void SaveData()
    {
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putLong(prevTime, seconds);
        editor.putString(TextKey, user_Input.getText().toString());
        editor.apply();
        timerRunning = false;
    }

    private void PauseTime()
    {
        handler.removeCallbacks(Timer);
    }

    private void IncreaseTime()
    {
        seconds++;
        timer_rec.setText(GetTimeText(seconds));
    }

    private String GetTimeText(long time)
    {
        int hour = (int)time / 3600;
        int minute = ((int)time % 3600) / 60;
        int second = (int)time % 60;
        return timeFormatter.format(hour) + ":" + timeFormatter.format(minute) + ":" + timeFormatter.format(second);
    }

    Runnable Timer = new Runnable() {
        @Override public void run() {
            try {
                IncreaseTime();
            }
            finally {
                handler.postDelayed(Timer, 1000);
            }
        }
    };

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong(prevTime, seconds);
        savedInstanceState.putBoolean(liveRunning, timerRunning);
        savedInstanceState.putString(TextKey, user_Input.getText().toString());
        savedInstanceState.putBoolean(firstRunKey, false);
    }
}