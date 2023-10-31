package com.example.congressionalapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;

public class Quiz extends AppCompatActivity {

    public static int yardSize = 0;
    public int oftenPerWeek = 0;
    public static double sprinklerType = 0; //3 for fixed, 4 for rotating, 0.033 for drip irrigation
    public int sprinklerCount = 0;
    public int lengthMin = 0;
    public static int waterUsed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        CheckBox size1 = findViewById(R.id.size1);
        CheckBox size2 = findViewById(R.id.size2);
        CheckBox size3 = findViewById(R.id.size3);
        EditText size4 = findViewById(R.id.editText);

        CheckBox often1 = findViewById(R.id.often1);
        CheckBox often2 = findViewById(R.id.often2);
        CheckBox often3 = findViewById(R.id.often3);

        CheckBox type1 = findViewById(R.id.type1);
        CheckBox type2 = findViewById(R.id.type2);
        CheckBox type3 = findViewById(R.id.type3);

        CheckBox count1 = findViewById(R.id.count1);
        CheckBox count2 = findViewById(R.id.count2);
        CheckBox count3 = findViewById(R.id.count3);

        CheckBox long1 = findViewById(R.id.long1);
        CheckBox long2 = findViewById(R.id.long2);
        CheckBox long3 = findViewById(R.id.long3);

        TextView para1 = findViewById(R.id.fb1);
        TextView para2 = findViewById(R.id.fb2);
        TextView para3 = findViewById(R.id.fb3);
        TextView para4 = findViewById(R.id.fb4);
        TextView para5 = findViewById(R.id.fb5);

        Button resultsBtn = (Button) findViewById(R.id.resultsBtn);

        TextView t = findViewById(R.id.textView12);


        //SIZE BUTTONS//
        size1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yardSize = 3000; // Map the answer to a numeric value
                para1.setVisibility(View.VISIBLE);
            }
        });

        size2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yardSize = 10000; // Map the answer to a numeric value
                para1.setVisibility(View.VISIBLE);
            }
        });

        size3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yardSize = 20000; // Map the answer to a numeric value
                para1.setVisibility(View.VISIBLE);
            }
        });

        size4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text changes. Update the userAnswer variable.
                String userAnswerText = s.toString(); // Get the user's input as a String
                para1.setVisibility(View.VISIBLE);

                try {
                    yardSize = Integer.parseInt(userAnswerText);
                    // Now, the userAnswer variable contains the integer value of the user's input
                    // You can use it as an integer in your code.
                } catch (NumberFormatException e) {
                    // Handle the case where the user's input is not a valid integer
                    // For example, you can show an error message to the user.
                }

            }
        });


        //OFTEN BUTTONS//
        often1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oftenPerWeek = 7; // Map the answer to a numeric value
                para2.setVisibility(View.VISIBLE);
            }
        });

        often2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oftenPerWeek = 3; // Map the answer to a numeric value
                para2.setVisibility(View.VISIBLE);
            }
        });

        often3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oftenPerWeek = 1; // Map the answer to a numeric value
                para2.setVisibility(View.VISIBLE);
            }
        });


        //TYPE BUTTONS//
        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerType = 3; // Map the answer to a numeric value
                para3.setVisibility(View.VISIBLE);
                waterUsed = (int)(0.2*yardSize + (lengthMin*sprinklerType*sprinklerCount*oftenPerWeek));
                t.setText(String.valueOf(waterUsed));
            }
        });

        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerType = 4; // Map the answer to a numeric value
                para3.setVisibility(View.VISIBLE);
                waterUsed = (int)(0.2*yardSize + (lengthMin*sprinklerType*sprinklerCount*oftenPerWeek));
                t.setText(String.valueOf(waterUsed));
            }
        });

        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerType = 1; // Map the answer to a numeric value
                para3.setVisibility(View.VISIBLE);
                waterUsed = (int)(0.2*yardSize + (lengthMin*sprinklerType*sprinklerCount*oftenPerWeek));
                t.setText(String.valueOf(waterUsed));
            }
        });


        //COUNT BUTTONS//
        count1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerCount = 4; // Map the answer to a numeric value
                para4.setVisibility(View.VISIBLE);
            }
        });

        count2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerCount = 10; // Map the answer to a numeric value
                para4.setVisibility(View.VISIBLE);
            }
        });

        count3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerCount = 14; // Map the answer to a numeric value
                para4.setVisibility(View.VISIBLE);
            }
        });


        //LENGTH BUTTONS//
        long1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthMin = 30; // Map the answer to a numeric value
                para5.setVisibility(View.VISIBLE);
            }
        });

        long2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthMin = 45; // Map the answer to a numeric value
                para5.setVisibility(View.VISIBLE);
            }
        });

        long3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthMin = 75; // Map the answer to a numeric value
                para5.setVisibility(View.VISIBLE);
            }
        });

        resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openResults();
            }
        });
    }

    private void openResults() {
        Intent intent = new Intent(this, Results.class);
        intent.putExtra("waterUsed", waterUsed); // Pass the value of waterUsed
        startActivity(intent);
    }

    public static int getWaterUsed(){
        return waterUsed;
    }

    public static int getYardSize(){
        return yardSize;
    }

    public static double getSprinklerType(){
        return sprinklerType;
    }

    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}


