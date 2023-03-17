package com.ut3.coordinature.gamelogic.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final SharedPreferences sharedPreferences;

    private final GameThread thread;

    private TextView currentScore;

    //Entities

    //Utilities

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        getHolder().addCallback(this);

        this.sharedPreferences = sharedPreferences;
        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    private void initEntities(){
        //Init all entitites needed
    }

    private void initUtilities(){
        //Init all utilities needed
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        initEntities();

        initUtilities();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                Log.e("SurfaceDestroyed", e.getMessage());
            }
            retry = false;
        }
    }

    public void update() {
    }

    @Override
    public synchronized void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void clearGame(){
        //TODO : Put all lists.clear
    }
}
