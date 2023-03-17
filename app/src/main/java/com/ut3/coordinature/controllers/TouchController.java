package com.ut3.coordinature.controllers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ut3.coordinature.entities.characters.impl.Player;
import com.ut3.coordinature.gamelogic.main.GameView;

public class TouchController implements View.OnTouchListener{



    // player movements application
    private final GameView gameView;

    public TouchController(GameView gameView) {
        this.gameView = gameView;

    }

    /**
     * Uses screen controllers to move the player in a certain way
     * @param view
     * @param motionEvent
     * @return screen touch confirmation as boolean
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.d("TAG", "onTouch: touch down");
                //implement player move here
                break;
            case MotionEvent.ACTION_UP:
                //Log.d("TAG", "onTouch: touch up");
                //implement player move here
                break;
            case MotionEvent.ACTION_MOVE:
            default:
                return true;
        }

        return true;
    }
}
