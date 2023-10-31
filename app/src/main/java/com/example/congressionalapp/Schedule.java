package com.example.congressionalapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;


public class Schedule extends AppCompatActivity {
    private EditText inputLocation,Sprinkler;
    private TextView outputLabel;
    private TextView maindate, mainpercent, mainmm;
    private TextView date2, percent2, mm2;
    private TextView date3, percent3, mm3;
    private ProgressBar MainProg,Prog2,Prog3, ProgLine;
    private TextView watertext,neededtext,totaltext;
    private RelativeLayout ProgRelative;
    private TextView progtext;
    private Button done;
    private double plantNum, rainGallons,sprinklerGallons, totalGallons, sprinklerType;
    private int minutesVal, landArea, perc;
    public static int saves;
    private double totalRain;
    private CalendarView calander;

    //plantNum-water amount for garden
    //rainGallons-rain in gallons;
    //sprinklerGallons - gallons from sprinkler;
    //totalGallons - total;
    //minutesVal - minutes to reach max;
    //sprinklerType- sprinkler release how many gallons per min
    //totalRain - total rain in mm
    //landArea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button home=(Button)findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openHome();
            }
        });
        Log.d("MyApp", "This is a debug message");        inputLocation = findViewById(R.id.input_location);
        //outputLabel = findViewById(R.id.output_label);
        maindate = findViewById(R.id.main_date);
        mainpercent = findViewById(R.id.main_percent);
        mainmm = findViewById(R.id.main_mm);

        date2 = findViewById(R.id.date2);
        percent2 = findViewById(R.id.percent2);
        mm2 = findViewById(R.id.mm2);

        date3 = findViewById(R.id.date3);
        percent3 = findViewById(R.id.percent3);
        mm3 = findViewById(R.id.mm3);


        MainProg= findViewById(R.id.ProgressMain);
        Prog2= findViewById(R.id.Progress2);
        Prog3= findViewById(R.id.Progress3);

        watertext=findViewById(R.id.RainWaterText);
        totaltext=findViewById(R.id.TotalWaterText);
        neededtext=findViewById(R.id.NeededWaterText);

        //bar relative layout
        ProgRelative=findViewById(R.id.RelativeLayout4);
        ProgLine = findViewById(R.id.LineProgressBar);
        progtext=findViewById(R.id.LineBarText);

        done=findViewById(R.id.DoneButton);


        sprinklerType=Quiz.getSprinklerType();
        plantNum=Quiz.getYardSize();
        landArea=Quiz.getYardSize();


        totalRain=0.0;

        calander = findViewById(R.id.calanderID);
        calander.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date change
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                // You can use the selectedDate for further processing
            }
        });

        Button fetchButton = findViewById(R.id.fetch_button);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchWeatherData();
                Log.d("MyApp", "button clicked");        inputLocation = findViewById(R.id.input_location);

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sprinkler=findViewById(R.id.SprinklerText);
                done.setVisibility(View.INVISIBLE);
                Calculate(Integer.parseInt(Sprinkler.getText().toString()));
                Log.d("MyApp", "buttonsss");
                //to jiya code raingallons+ needed-total

            }
        });
    }

    private void fetchWeatherData() {
        String location = inputLocation.getText().toString();
        String apiKey = "e5a61fffef534d6c8ad71410230510"; // Replace with your actual API key
        String apiUrl = "https://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q=" + location + "&days=3";

        new FetchWeatherTask().execute(apiUrl);
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String apiUrl = params[0];
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    JSONObject data = new JSONObject(response);
                    JSONArray forecast = data.getJSONObject("forecast").getJSONArray("forecastday");

                    StringBuilder result = new StringBuilder();
                    //main
                    JSONObject dayData = forecast.getJSONObject(0);
                    String date = dayData.getString("date");
                    JSONObject day = dayData.getJSONObject("day");
                    double rainfallAmount = day.optDouble("totalprecip_mm", 0);
                    int percentAmount = day.optInt("daily_chance_of_rain",0);
                    totalRain += rainfallAmount;
                    String percentText =String.valueOf(percentAmount) + "%";
                    String mmText=String.valueOf(rainfallAmount) +" mm of rain";
                    maindate.setText(date.toString());
                    mainmm.setText(mmText);
                    mainpercent.setText(percentText);
                    MainProg.setProgress(percentAmount);
                    MainProg.setVisibility(View.VISIBLE);


                    //2
                    JSONObject dayData2 = forecast.getJSONObject(1);
                    String dateValue2 = dayData2.getString("date");
                    JSONObject day2 = dayData2.getJSONObject("day");
                    double rainfallAmount2 = day2.optDouble("totalprecip_mm", 1);
                    int percentAmount2 = day2.optInt("daily_chance_of_rain",1);
                    totalRain += rainfallAmount2;
                    String percentText2 =String.valueOf(percentAmount2) + "%";
                    String mmText2=String.valueOf(rainfallAmount2) +" mm of rain";
                    date2.setText(dateValue2.toString());
                    mm2.setText(mmText2);
                    percent2.setText(percentText2);
                    Prog2.setProgress(percentAmount2);
                    Prog2.setVisibility(View.VISIBLE);


                    //3
                    JSONObject dayData3 = forecast.getJSONObject(2);
                    String dateValue3 = dayData3.getString("date");
                    JSONObject day3 = dayData3.getJSONObject("day");
                    double rainfallAmount3 = day3.optDouble("totalprecip_mm", 2);
                    int percentAmount3 = day3.optInt("daily_chance_of_rain",2);
                    totalRain += rainfallAmount3;
                    String percentText3=String.valueOf(percentAmount3) + "%";
                    String mmText3=String.valueOf(rainfallAmount3) +" mm of rain"+ "\n";
                    date3.setText(dateValue3.toString());
                    mm3.setText(mmText3);
                    percent3.setText(percentText3);
                    Prog3.setProgress(percentAmount3);
                    Prog3.setVisibility(View.VISIBLE);


                    calander.setVisibility(View.VISIBLE);


                    totalGallons=totalRain*landArea;

                    //Bar Text
                    String water= "There is a total of "+totalRain+" mm of rain in the next three days";
                    watertext.setText(water);
                    minutesVal= (int)((plantNum - (totalRain))/sprinklerType);
                    String needed="You need to water your plants for "+ minutesVal+ " minutes to reach your goal of "+plantNum+" gallons";
                    neededtext.setText(needed);
                    String total= "You're plants will get a total of "+ totalGallons+" gallons of water";
                    totaltext.setText(total);

                    //Bar
                    ProgRelative.setVisibility(View.VISIBLE);
                    Sprinkler.setVisibility(View.VISIBLE);
                    done.setVisibility(View.VISIBLE);
                    perc=(int)((totalGallons/plantNum)*100.0);
                    ProgLine.setProgress(perc);
                    progtext.setText(String.valueOf(perc));

                    //result.append("Date: ").append(date).append(", Rainfall Amount (mm): ").append(rainfallAmount).append("\n");
                    //}


                    /*if (plantNum > totalRain) {
                        result.append("Prepare to water plants");
                        Log.d("MyApp", "Trauhnm");
                    } else {
                        result.append("Do not water plants");
                        System.out.println("hello");
                    }*/

                    // Set the entire text including the new result
                    //outputLabel.setText(result.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    outputLabel.setText("Error parsing API response.");
                }
            } else {
                // outputLabel.setText("Error: Unable to fetch data from the API.");
            }
        }


    }

    //value is sprinkler time person used
    public void Calculate(int value){
        saves = (int)(rainGallons+ (plantNum-totalGallons));
        totalGallons+= value * sprinklerType;
        perc=(int)((totalGallons/plantNum)*100.0);
        ProgLine.setProgress(perc);
        progtext.setText(String.valueOf(perc));
    }

    public static int getsaves(){
        return saves;
    }

    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
