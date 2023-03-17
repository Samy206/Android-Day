package com.ut3.coordinature.controllers;

import com.ut3.coordinature.activities.GameActivity;
import com.ut3.coordinature.gamelogic.main.GameView;

public class MainController {

    private SensorController sensorController;
    private TouchController touchController;

    public MainController(GameActivity gameActivity, GameView gameView){
        this.sensorController = new SensorController(gameActivity, gameView);
        this.touchController = new TouchController(gameView);
    }


    public SensorController getSensorController() {
        return sensorController;
    }

    public TouchController getTouchController() {
        return touchController;
    }
}
