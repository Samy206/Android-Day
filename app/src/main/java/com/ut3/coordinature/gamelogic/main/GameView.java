package com.ut3.coordinature.gamelogic.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ut3.coordinature.entities.characters.impl.Player;
import com.ut3.coordinature.entities.obstacles.impl.Obstacle;
import com.ut3.coordinature.entities.obstacles.impl.Platform;
import com.ut3.coordinature.gamelogic.utilities.ScoreCalculator;

import java.util.concurrent.ArrayBlockingQueue;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {


    //Entities
    private Player player;
    private ArrayBlockingQueue<Obstacle> obstacles;

    //Utilities
    private ScoreCalculator scoreCalculator;

    // Commons
    private final GameThread thread;
    private TextView currentScore;
    private SharedPreferences sharedPreference;

    public GameView(Context context, SharedPreferences sharedPreferenceScore) {
        super(context);
        getHolder().addCallback(this);
        sharedPreference = sharedPreferenceScore;
        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    private void initEntities(){
        //Init all entitites needed
        obstacles = new ArrayBlockingQueue<>(1);
        Platform p1 = new Platform(100, 0, 200, 200);
        Platform p2 = new Platform(100, 400, 200, getHeight());

        obstacles.add(new Obstacle(p1, p2, getHeight()));

    }

    private void initUtilities(){
        //Init all utilities needed
        scoreCalculator = new ScoreCalculator(sharedPreference);
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
        scoreCalculator.updateScore(player.getObstaclePassed().size());
    }

    @Override
    public synchronized void draw(Canvas canvas) {
        super.draw(canvas);
        player.drawGameObject(canvas);

        for(Obstacle obstacle : obstacles) {
            obstacle.drawGameObject(canvas);
        }
    }

    public void clearGame(){
        //TODO : Put all lists.clear
    }

    private void detectCollisions() {
        for(Obstacle obstacle : obstacles) {
            player.obstaclePassedCheck(obstacle);
        }
    }
}
