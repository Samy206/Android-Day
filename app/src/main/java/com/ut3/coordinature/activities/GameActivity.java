package com.ut3.coordinature.activities;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ut3.coordinature.R;
import com.ut3.coordinature.controllers.MainController;
import com.ut3.coordinature.gamelogic.main.GameView;
//import com.ut3.coordinature.entities.character.impl.Player;
//import com.ut3.coordinature.game.logic.main.GameView;

import java.io.File;
import java.io.IOException;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    SensorManager sm = null;

    File audioFile = null;

    private MediaRecorder mRecorder = null;

    private MainController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGameView();

        setContentView(createRootPanel());

        setupActionBar();

        this.controller = new MainController(this, this.gameView);
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }


    private void initGameView(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainMenuActivity.SHARED_PREF, MODE_PRIVATE);
        gameView = new GameView(this, sharedPreferences);
        gameView.setZOrderOnTop(true);
        gameView.getHolder().setFormat(PixelFormat.TRANSPARENT);
    }


    @Override
    protected void onResume() {
        super.onResume();
        controller.getSensorController().registerListener();
        /*


        if (mRecorder == null) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

                //Creating file
                File dir = Environment.getExternalStorageDirectory();
                try {
                    audioFile = File.createTempFile("sound", ".3gp", dir);
                } catch (IOException e) {
                    Log.e("ERROR", e.getMessage());
                    return;
                }

                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(audioFile.getAbsolutePath());
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mRecorder.start();

            }


        }
        */
    }

    @Override
    protected void onStop() {
        controller.getSensorController().unregisterListener();
        /*
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.
                TYPE_LINEAR_ACCELERATION));

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        */
        super.onStop();


    }




    public double getAmplitude() {
        if (mRecorder != null)
            return  mRecorder.getMaxAmplitude();
        else
            return -1;

    }



    private RelativeLayout createRootPanel(){
        // Setup your ImageView
        ImageView bgImagePanel = new ImageView(this);

        // Use a RelativeLayout to overlap both SurfaceView and ImageView
        RelativeLayout.LayoutParams fillParentLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout rootPanel = new RelativeLayout(this);

        rootPanel.setLayoutParams(fillParentLayout);
        //rootPanel.addView(gameView, fillParentLayout);
        rootPanel.addView(bgImagePanel, fillParentLayout);

        return rootPanel;
    }

    public void returnToMenuActivity() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public MainController getController() {
        return controller;
    }
}