package com.example.congressionalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Results extends AppCompatActivity {
    Button schedButton;
    Button tipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView gallonVal = findViewById(R.id.gallonVal);
        schedButton = findViewById(R.id.schedBtn);
        tipButton = findViewById(R.id.tipBtn);

        // Retrieve the passed value from the Intent
        int waterUsed = getIntent().getIntExtra("waterUsed", 0);

        gallonVal.setText(String.valueOf(waterUsed) + " gallons");

        schedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSched();
            }
        });

        tipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTip();
            }
        });
    }

    private void openTip() {
        Intent intent = new Intent(this, Tips.class);
        startActivity(intent);
    }

    private void openSched() {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }
}
