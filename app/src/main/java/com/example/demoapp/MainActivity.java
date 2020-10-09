package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Activity mainActivity;
    private boolean checkCredentials(String username, String password){
        //this method will pass data to the db for checking
        // for now using admin, admin as values
        if(username.equals(new String("admin")) && password.equals(new String("admin"))){
            return true;
        }
        return false;
    }
    public void login(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void clickFunction(View view){

        final EditText editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText editTextTextPassword = findViewById(R.id.editTextTextPassword);
        if(checkCredentials(editTextTextPersonName.getText().toString(), editTextTextPassword.getText().toString())){
            ImageView statusImage = findViewById(R.id.statusImageView);
            statusImage.setImageResource(android.R.drawable.presence_online);
            TextView statusText = findViewById(R.id.helloWorldTextView);
            statusText.setBackgroundColor(Color.parseColor("#B9EC95"));
            Log.i("Info", editTextTextPersonName.getText().toString() + editTextTextPassword.getText().toString());
            new java.util.Timer().schedule(
                    new java.util.TimerTask(){
                        @Override
                        public void run() {
                            mainActivity.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this,"Welcome "+editTextTextPersonName.getText().toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    },1000
            );
            ScheduledThreadPoolExecutor scheduler =
                    (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);

            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    login();
                }
            },1500, TimeUnit.MILLISECONDS);
        }else{
             Toast.makeText(MainActivity.this,"Cannot authenticate ", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}