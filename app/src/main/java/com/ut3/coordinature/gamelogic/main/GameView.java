package com.ut3.coordinature.gamelogic.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ut3.coordinature.R;
import com.ut3.coordinature.entities.characters.impl.Player;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final SharedPreferences sharedPreferences;

    private final GameThread thread;

    private TextView currentScore;

    //Entities
    private Player player;
    //Utilities

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        getHolder().addCallback(this);
        this.sharedPreferences = sharedPreferences;
        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    private void initEntities(){
        Bitmap playerSheet = BitmapFactory.decodeResource(this.getResources(), R.drawable.bat);
        int playerHeight =(int) (this.getHeight() * 0.5);
        player = new Player(this, playerSheet, 0, playerHeight);
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
        player.updateGameObject();
    }

    @Override
    public synchronized void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas != null){
            player.drawGameObject(canvas);
        }
    }

    public void clearGame(){
        //TODO : Put all lists.clear
    }
}
