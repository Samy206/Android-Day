package com.ut3.coordinature.controllers;

import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.ut3.coordinature.activities.GameActivity;
import com.ut3.coordinature.gamelogic.main.GameView;

public class SensorController implements SensorEventListener{

    SensorManager sm = null;

    private GameView gameView;

    private GameActivity activity;

    public SensorController(GameActivity activity, GameView gameView){

        sm = (SensorManager) activity.getSystemService(SENSOR_SERVICE);
        this.gameView = gameView;
        this.activity = activity;
    }

    public void registerListener(){
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION
        );
        sm.registerListener(this, sensor, SensorManager.
                SENSOR_DELAY_NORMAL);
    }


    public void unregisterListener(){
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.
                TYPE_LINEAR_ACCELERATION));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;
        synchronized (this) {
            if (sensor == Sensor.TYPE_LINEAR_ACCELERATION) {
                //implement obstacles mouvement here
            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
