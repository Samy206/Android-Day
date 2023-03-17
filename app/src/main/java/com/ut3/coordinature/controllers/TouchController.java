package com.ut3.coordinature.controllers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ut3.coordinature.entities.characters.impl.Player;
import com.ut3.coordinature.gamelogic.main.GameView;

public class TouchController implements View.OnTouchListener{



    // player movements application
    private final GameView gameView;

    private int direction;

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
        Player player = gameView.getPlayer();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG", "onTouch: touch move");
                //implement player move here
           case MotionEvent.ACTION_DOWN:
                Log.d("TAG", "onTouch: touch down");
                direction = (int) (y - player.getyPos()) > 0 ? 1 : -1;
                player.setDirection(direction);
                player.setCanMove(true);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG", "onTouch: touch up");
                //implement player move here

                break;

            default:
                return true;
        }

        return true;
    }
}
