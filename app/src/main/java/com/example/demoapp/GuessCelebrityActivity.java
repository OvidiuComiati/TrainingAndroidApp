package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuessCelebrityActivity extends AppCompatActivity {

    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();

    int chosenCeleb = 0;
    int indexOfCeleb = 0;
    String[] celebs = new String[4];

    ImageView celebImageView;
    Button celebPick0;
    Button celebPick1;
    Button celebPick2;
    Button celebPick3;

    public void createNewQuestion() {

        Random random = new Random();
        chosenCeleb = random.nextInt(celebURLs.size());
        DownloadImage imageTask = new DownloadImage();
        Bitmap celebImage;

        try {
            celebImage = imageTask.execute(celebURLs.get(chosenCeleb)).get();
            celebImageView.setImageBitmap(celebImage);
            indexOfCeleb = random.nextInt(4);
            int incorrectAnswerLocation;
            for (int i=0; i<4; i++) {
                if (i == indexOfCeleb) {
                    celebs[i] = celebNames.get(chosenCeleb);
                } else {
                    //create the wrong answers for the rest of the buttons
                    incorrectAnswerLocation = random.nextInt(celebURLs.size());
                    while (incorrectAnswerLocation == chosenCeleb) {
                        incorrectAnswerLocation = random.nextInt(celebURLs.size());
                    }
                    celebs[i] = celebNames.get(incorrectAnswerLocation);
                }
            }
            //populate the buttons textes
            celebPick0.setText(celebs[0]);
            celebPick1.setText(celebs[1]);
            celebPick2.setText(celebs[2]);
            celebPick3.setText(celebs[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void celebChosen(View view) {
        if (view.getTag().toString().equals(Integer.toString(indexOfCeleb))) {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong! It was " + celebNames.get(chosenCeleb), Toast.LENGTH_LONG).show();
        }
        createNewQuestion();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_celebrity);

        celebImageView = findViewById(R.id.celebImageView);
        celebPick0 =  findViewById(R.id.button0);
        celebPick1 =  findViewById(R.id.button1);
        celebPick2 =  findViewById(R.id.button2);
        celebPick3 =  findViewById(R.id.button3);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("https://m.imdb.com/chart/starmeter/").get();
            String[] splitResult = result.split("<section id=\"chart-content\">");
            //classes used to filter through the web page and act as regex
            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[1]);
            Log.i("logger",splitResult[1]);
            while (m.find()) {
                celebURLs.add(m.group(1));
            }

            p = Pattern.compile("<h4> (.*?) </h4>");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        createNewQuestion();
    }
}