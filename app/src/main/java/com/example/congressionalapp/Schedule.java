package com.example.congressionalapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.text.DecimalFormat;

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
    private double plantNum, rainGallons,sprinklerGallons,sprinklerType;
    public static double totalGallons;
    private int minutesVal,  perc, sprinklerCount;
    public static int saves;
    public static double totalRain,landArea;
    private CalendarView calander;

    //extra text
    private TextView textMain, text2,text3;
    private ImageView imageMain, image2,image3;

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



        Log.d("MyApp", "This is a debug message");
        //outputLabel = findViewById(R.id.output_label);
        inputLocation = findViewById(R.id.input_location);
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
        Sprinkler = findViewById(R.id.SprinklerText);

        done=findViewById(R.id.DoneButton);

        plantNum=0.0;
        rainGallons=0.0;
        sprinklerGallons=0.0;
        totalGallons=0.0;
        minutesVal=0;
        sprinklerType=0.0;
        totalRain=0;
        landArea=0;
        sprinklerCount=0;

        calander = findViewById(R.id.calanderID);

        //images
        imageMain=findViewById(R.id.imageMain);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        //extra text
        textMain=findViewById(R.id.textMain);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);


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
                Log.d("MyApp", "button clicked");
                inputLocation = findViewById(R.id.input_location);
                sprinklerType=Quiz.getSprinklerType();
                plantNum=Quiz.getWaterUsed() /3;
                landArea=Quiz.getYard3Days();
                sprinklerCount=Quiz.getSprinklerCount();


            }
        });


      done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sprinkler=findViewById(R.id.SprinklerText);
                done.setVisibility(View.INVISIBLE);
                Calculate(Integer.parseInt(Sprinkler.getText().toString()));
                Log.d("MyApp", "buttonsss");


            }
        });

        Button home=(Button)findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openHome();
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
                    textMain.setVisibility(View.VISIBLE);
                    if(percentAmount>50){
                        imageMain.setImageResource(R.drawable.cloud);
                        imageMain.setVisibility(View.VISIBLE);
                    }
                    else{
                        imageMain.setVisibility(View.VISIBLE);
                    }


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
                    text2.setVisibility(View.VISIBLE);
                    if(percentAmount2>50){
                        image2.setImageResource(R.drawable.cloud);
                        image2.setVisibility(View.VISIBLE);
                    }
                    else{
                        image2.setVisibility(View.VISIBLE);
                    }


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
                    text3.setVisibility(View.VISIBLE);
                    if(percentAmount3>50){
                        image3.setImageResource(R.drawable.cloud);
                        image3.setVisibility(View.VISIBLE);
                    }
                    else{
                        image3.setVisibility(View.VISIBLE);
                    }





                    totalGallons=(totalRain*0.0394)*landArea;
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formatted = df.format(totalGallons);
                    totalGallons = Double.parseDouble(formatted);

                    String formatted2=df.format(totalRain);
                    totalRain=Double.parseDouble(formatted2);

                    //Bar Text
                    String water= "There is a total of "+totalRain+" mm of rain in the next three days";
                    watertext.setText(water);
                    double i = (landArea-totalGallons)/(sprinklerType*sprinklerCount);
                    minutesVal= (int)(i);
                    if(minutesVal<=0){
                        minutesVal=0;
                    }
                    String needed="You need to water your plants for around "+ minutesVal+ " minutes to reach your goal of "+landArea+" gallons";
                    neededtext.setText(needed);
                    String total= "Your plants will get a total of "+ totalGallons+" gallons of water";
                    totaltext.setText(total);


                    //Bar
                    saves = (int)(totalGallons);
                    perc=(int)((totalGallons/landArea)*100.0);
                    ProgRelative.setVisibility(View.VISIBLE);
                    Sprinkler.setVisibility(View.VISIBLE);
                    done.setVisibility(View.VISIBLE);
                    ProgLine.setProgress(perc);
                    progtext.setText((String.valueOf(perc))+"%");

                    //result.append("Date: ").append(date).append(", Rainfall Amount (mm): ").append(rainfallAmount).append("\n");
                    //}*/



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
        double watered = sprinklerCount*sprinklerType*value;
        saves = (int)(plantNum - (totalGallons+(landArea-watered)));
        if(saves <=0){
            saves =0;
        }
        totalGallons+= (value * sprinklerType*sprinklerCount);
        perc=(int)((totalGallons/landArea)*100.0);
        if(perc>=99 &&perc<=101){
            perc = 100;
        }
        ProgLine.setProgress(perc);
        progtext.setText(String.valueOf(perc)+"%");
    }

    public static int getsaves(){
        return saves+(int)((totalRain*0.0394)*landArea);
    }

    public void openHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
