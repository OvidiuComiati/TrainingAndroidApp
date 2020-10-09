package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void navigateToConnect3(View view){//element that is clicked or itneracted with appears here
        Intent intent = new Intent(this, Connect3Activity.class);
        startActivity(intent);
    }
    public void navigateToTranslator(View view){//element that is clicked or itneracted with appears here
        Intent intent = new Intent(this, TranslationActivity.class);
        startActivity(intent);
    }
    public void navigateToGuessCelebrity(View view){//element that is clicked or itneracted with appears here
        Intent intent = new Intent(this, GuessCelebrityActivity.class);
        startActivity(intent);
    }
    public void navigateToWeatherApp(View view){//element that is clicked or itneracted with appears here
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
}