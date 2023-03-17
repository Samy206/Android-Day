package com.ut3.coordinature.gamelogic.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ut3.coordinature.R;
import com.ut3.coordinature.entities.characters.impl.Player;
import com.ut3.coordinature.entities.obstacles.impl.Obstacle;
import com.ut3.coordinature.entities.obstacles.impl.Platform;
import com.ut3.coordinature.gamelogic.utilities.ScoreCalculator;
import com.ut3.coordinature.utils.ObstacleSpawner;

import java.util.concurrent.ArrayBlockingQueue;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {


    //Entities
    private Player player;
    private ArrayBlockingQueue<Obstacle> obstacles;
    private ObstacleSpawner obstacleSpawner;

    //Utilities
    private ScoreCalculator scoreCalculator;

    // Commons
    private final GameThread thread;
    private TextView currentScore;
    private SharedPreferences sharedPreference;

    public GameView(Context context, SharedPreferences sharedPreferenceScore) {
        super(context);
        getHolder().addCallback(this);
        this.sharedPreference = sharedPreferenceScore;
        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    private void initEntities(){
        //Init all entitites needed

        obstacles = new ArrayBlockingQueue<>(10);


        obstacles.add(obstacleSpawner.returnRandomObstacleAt(300));
        obstacles.add(obstacleSpawner.returnRandomObstacleAt(600));
        obstacles.add(obstacleSpawner.returnRandomObstacleAt(900));



        Log.d("TAG", "initEntities: " + obstacles.size());


        Bitmap playerSheet = BitmapFactory.decodeResource(this.getResources(), R.drawable.bat);
        int playerHeight =(int) (this.getHeight() * 0.5);
        player = new Player(this, playerSheet, 0, playerHeight);
    }

    private void initUtilities(){
        //Init all utilities needed
        scoreCalculator = new ScoreCalculator(sharedPreference);
        obstacleSpawner = new ObstacleSpawner(getHeight(), this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        initUtilities();
        initEntities();



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
        player.updateGameObject();


        for(Obstacle obstacle : obstacles) {
            obstacle.move();
        }


        if(obstacles.size() < 4){
            obstacles = obstacleSpawner.spawnNewObstacle(obstacles);
        }




        detectCollisions();

    }

    @Override
    public synchronized void draw(Canvas canvas) {
        super.draw(canvas);

        if(canvas != null){
            player.drawGameObject(canvas);

            for(Obstacle obstacle : obstacles) {
                obstacle.drawGameObject(canvas);
            }

        }
    }

    public void clearGame(){
        //TODO : Put all lists.clear
    }

    public Player getPlayer() {
        return this.player;
    }


    private void detectCollisions() {
        for(Obstacle obstacle : obstacles) {
            player.obstaclePassedCheck(obstacle);
        }
    }

    public void deleteObstacle(Obstacle obstacle) {
        if(obstacle != null)
            obstacles.remove(obstacle);
    }
}
