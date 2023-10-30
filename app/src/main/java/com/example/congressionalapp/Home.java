package com.example.congressionalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button quizButton;
    private Button scheduleButton;
    private Button waterSavedButton;
    private Button tipsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        quizButton = (Button) findViewById(R.id.quizBtn);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuiz();
            }
        });

        scheduleButton = (Button) findViewById(R.id.scheduleBtn);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSchedule();
            }
        });

        waterSavedButton = (Button) findViewById(R.id.waterSavedBtn);
        waterSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWaterSaved();
            }
        });

        tipsButton = (Button) findViewById(R.id.tipsBtn);
        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTips();
            }
        });
    }

    public void openQuiz() {
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }

    public void openSchedule() {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void openWaterSaved() {
        Intent intent = new Intent(this, WaterSaved.class);
        startActivity(intent);
    }

    public void openTips() {
        Intent intent = new Intent(this, Tips.class);
        startActivity(intent);
    }
}
