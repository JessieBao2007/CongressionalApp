package com.example.congressionalapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WaterSaved extends AppCompatActivity {

    TextView waterSaved, statement;
    ImageView garden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_saved);

        int w = Quiz.getWaterUsed();
        waterSaved = (TextView) findViewById(R.id.words);
        waterSaved.setText("You have saved: " + Integer.toString(w) + " gallons of water");
        garden = (ImageView) findViewById(R.id.gardenimg);
        statement = (TextView) findViewById(R.id.statement);
        statement.setText("Your garden wants to grow, it needs water!");

        if(w==0){
            garden.setImageResource(R.drawable.garden0);
            statement.setText("To grow your garden start saving some water");
        } else if (w >= 1000 && w < 2500) {
            garden.setImageResource(R.drawable.garden1);
            statement.setText("Your garden is slowly growing, perhaps it needs more resources...");
        } else if (w >= 2500 && w < 5000) {
            garden.setImageResource(R.drawable.garden2);
            statement.setText("Your garden is growing more and more leaves, it's growing quicker than ever!");
        } else if (w >= 5000 && w < 15000) {
            garden.setImageResource(R.drawable.garden3);
            statement.setText("Your garden is looking better and better! Keep up the good work!");
        } else if (w >= 15000) {
            garden.setImageResource(R.drawable.garden4);
            statement.setText("Congratulations! Your hard work has payed off and your garden has finally flowered!");
        }
    }
}

