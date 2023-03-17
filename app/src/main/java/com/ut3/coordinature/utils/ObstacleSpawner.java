package com.ut3.coordinature.utils;

import android.util.Log;

import com.ut3.coordinature.entities.obstacles.impl.Obstacle;
import com.ut3.coordinature.entities.obstacles.impl.Platform;
import com.ut3.coordinature.gamelogic.main.GameView;

import java.util.concurrent.ArrayBlockingQueue;

public class ObstacleSpawner {

    private int windowHeight;

    private final int platformWidth = 100;
    private int gapSize = 200;
    private final GameView gameView;

    private final int SPACE_BETWEEN_OBSTACLES = 50;

    public ObstacleSpawner(int windowHeight, GameView gameView) {
        this.windowHeight = windowHeight;
        this.gameView = gameView;
    }


    public ArrayBlockingQueue<Obstacle> spawnNewObstacle(ArrayBlockingQueue<Obstacle> currentObstacles) {
        //Obstacle first = currentObstacles.poll();

        Obstacle newObstacle = returnRandomObstacleAt(gameView.getWidth());

        currentObstacles.add(newObstacle);
        return currentObstacles;

    }


    public Obstacle returnRandomObstacleAt(int left){

        int rand = getRandomNumber(0, 2);
        Log.d("TAG", "returnRandomObstacleAt: " + rand);
        Log.d("TAG", "returnRandomObstacleAt hight: " + windowHeight);
        if(rand == 0){
            //Create single platform
            int p1Up = getRandomNumber(gapSize, windowHeight-200);
            Log.d("TAG", "returnRandomObstacleAt p1Up: " + p1Up);
            Platform platform1 = new Platform(left , p1Up, left + platformWidth, windowHeight);
            return new Obstacle(platform1, windowHeight, gameView);
        }else{
            //Create 2 platforms
            int gabPos = getRandomNumber(150, windowHeight-100);
            Log.d("TAG", "returnRandomObstacleAt gabPos: " + gabPos);
            Platform platform1 = new Platform(left, 0, left+platformWidth, gabPos);
            Platform platform2 = new Platform(left, gabPos+gapSize, left+platformWidth, windowHeight);

            return new Obstacle(platform2, platform1, windowHeight, gameView);

        }


    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
