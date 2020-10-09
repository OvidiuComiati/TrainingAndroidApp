package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.Timer;
import java.util.TimerTask;

public class TranslationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private  MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private static SeekBar progressBar;

    public void play(View view) {
        mediaPlayer.start();
    }

    public void pause(View view) {
        mediaPlayer.pause();
    }

    private void selectSound(int position)  {
        Log.i("selector","it entered");
        mediaPlayer.reset();
        switch (position){
            case 0:
                mediaPlayer = MediaPlayer.create(this, R.raw.rainforest_ambience);
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(this, R.raw.church_chime);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(this, R.raw.labrador_barking);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(this, R.raw.large_waterfall);
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(this, R.raw.toddler_laugh);
                break;
            default:
                mediaPlayer = MediaPlayer.create(this, R.raw.rainforest_ambience);
                break;
        }
//
//        if (mediaPlayer != null) {
//            mediaPlayer.prepare();
//        }

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeControl = (SeekBar)findViewById(R.id.volumeSeekBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        });

        progressBar = null;//
        progressBar = (SeekBar) findViewById(R.id.progressSeekBar);
        progressBar.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);


        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        Log.i("selector","it started");

        Spinner spinner = findViewById(R.id.soundsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sounds,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.rainforest_ambience);

        selectSound(0);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("selector",position+"");
        selectSound(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}