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

    private final double LIGHT_BLOCKED_LUX = 10;

    public SensorController(GameActivity activity, GameView gameView){

        sm = (SensorManager) activity.getSystemService(SENSOR_SERVICE);
        this.gameView = gameView;
        this.activity = activity;
    }

    public void registerListener(){
        Sensor acceloSensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION
        );
        Sensor luxSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        sm.registerListener(this, acceloSensor, SensorManager.
                SENSOR_DELAY_NORMAL);
        sm.registerListener(this, luxSensor, SensorManager.
                SENSOR_DELAY_NORMAL);
    }


    public void unregisterListener(){
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.
                TYPE_LINEAR_ACCELERATION));
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.
                TYPE_LIGHT));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;
        synchronized (this) {
            if (sensor == Sensor.TYPE_LINEAR_ACCELERATION) {
                //implement obstacles mouvement here
            }

            if (sensor == Sensor.TYPE_LIGHT) {
                if(values[0] < LIGHT_BLOCKED_LUX){
                    // show obstacles here
                }

            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
