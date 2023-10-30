package com.example.congressionalapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

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
        CheckBox size4 = findViewById(R.id.size4);

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

        TextView test1 = findViewById(R.id.textView7);
        TextView test2 = findViewById(R.id.textView8);
        TextView test3 = findViewById(R.id.textView13);
        TextView test4 = findViewById(R.id.textView14);
        TextView test5 = findViewById(R.id.textView15);
        TextView test6 = findViewById(R.id.textView16);

        //SIZE BUTTONS//
        size1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yardSize = 3000; // Map the answer to a numeric value
                test1.setText(String.valueOf(yardSize));
            }
        });

        size2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yardSize = 10000; // Map the answer to a numeric value
                test1.setText(String.valueOf(yardSize));
            }
        });

        size3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yardSize = 20000; // Map the answer to a numeric value
                test1.setText(String.valueOf(yardSize));
            }
        });

        size4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sizeBoxText = size2.getText().toString();
                test1.setText(String.valueOf(yardSize));

                try {
                    yardSize = Integer.parseInt(sizeBoxText);
                    // integerValue now contains the integer value of the text
                } catch (NumberFormatException e) {
                    // Handle the case where the textNumber is not a valid integer
                    // For example, if textNumber is not a number, this block will be executed
                }
            }
        });

        //OFTEN BUTTONS//
        often1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oftenPerWeek = 7; // Map the answer to a numeric value
                test2.setText(String.valueOf(oftenPerWeek));
            }
        });

        often2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oftenPerWeek = 3; // Map the answer to a numeric value
                test2.setText(String.valueOf(oftenPerWeek));
            }
        });

        often3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oftenPerWeek = 1; // Map the answer to a numeric value
                test2.setText(String.valueOf(oftenPerWeek));
            }
        });

        //TYPE BUTTONS//
        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerType = 3; // Map the answer to a numeric value
                test3.setText(String.valueOf(sprinklerType));
            }
        });

        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerType = 4; // Map the answer to a numeric value
                test3.setText(String.valueOf(sprinklerType));
            }
        });

        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerType = 1; // Map the answer to a numeric value
                test3.setText(String.valueOf(sprinklerType));
            }
        });

        //COUNT BUTTONS//
        count1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerCount = 4; // Map the answer to a numeric value
                test4.setText(String.valueOf(sprinklerCount));
            }
        });

        count2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerCount = 10; // Map the answer to a numeric value
                test4.setText(String.valueOf(sprinklerCount));
            }
        });

        count3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprinklerCount = 14; // Map the answer to a numeric value
                test4.setText(String.valueOf(sprinklerCount));
            }
        });

        //LENGTH BUTTONS//
        long1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthMin = 30; // Map the answer to a numeric value
                test5.setText(String.valueOf(lengthMin));

                waterUsed = (int)(0.2*yardSize + lengthMin*sprinklerType*sprinklerCount*oftenPerWeek);
                test6.setText(String.valueOf(waterUsed));
            }
        });

        long2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthMin = 45; // Map the answer to a numeric value
                test5.setText(String.valueOf(lengthMin));

                waterUsed = (int)(0.2*yardSize + lengthMin*sprinklerType*sprinklerCount*oftenPerWeek);
                test6.setText(String.valueOf(waterUsed));
            }
        });

        long3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthMin = 75; // Map the answer to a numeric value
                test5.setText(String.valueOf(lengthMin));

                waterUsed = (int)(0.2*yardSize + lengthMin*sprinklerType*sprinklerCount*oftenPerWeek);
                test6.setText(String.valueOf(waterUsed));
            }
        });


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

}


