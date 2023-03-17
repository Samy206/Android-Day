package com.ut3.coordinature.entities.obstacles.impl;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.GameObject;
import com.ut3.coordinature.entities.Movable;
import com.ut3.coordinature.entities.obstacles.PlatformInterface;
import com.ut3.coordinature.gamelogic.main.GameView;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements Movable, Collidable, GameObject {

    // An obstacle is an alignment of one or two platforms
    private final List<PlatformInterface> platforms;
    // Platform gap to detect if the player has been in it
    private Rect platformsGap;
    private final int windowHeight;

    private Long lastDisplayed;
    private final Long VISIBILITY_DELAY = 500000000L;
    private final GameView gameView;

    private boolean canMove = false;
    private int direction = 0;


    public Obstacle(Platform bottomPlatform, Platform topPlatform, int windowHeight, GameView view) {
        platforms = new ArrayList<>();
        platforms.add(bottomPlatform);
        platforms.add(topPlatform);
        gameView = view;
        lastDisplayed = 0L;
        this.windowHeight = windowHeight;
        setupPlatformGap();
    }

    public Obstacle(Platform singlePlatform, int windowHeight, GameView view) {
        platforms = new ArrayList<>();
        platforms.add(singlePlatform);
        gameView = view;
        lastDisplayed = 0L;
        this.windowHeight = windowHeight;
        setupPlatformGap();
    }

    public Rect getPlatformsGap() {
        return platformsGap;
    }

    public List<PlatformInterface> getPlatformInterfaces() {
        return platforms;
    }

    private void setupPlatformGap() {
        Rect platformBottom = platforms.get(0).getHitBox();

        if(platforms.size() == 2) {

            // Second platform will always be the top one
            Rect platformTop = platforms.get(1).getHitBox();

            platformsGap = new Rect(platformTop.left, platformTop.bottom,
                    platformBottom.right, platformBottom.top);
        }
        else {

            /* if there is only one platform in an obstacle, it will start from the bottom and not
            reach the max height */
            platformsGap = new Rect(platformBottom.left, 0 ,
                    platformBottom.right, platformBottom.top);

        }
    }

    @Override
    public boolean detectCollision(Rect dangerHitBox) {
        if(platforms.size() == 2) {
            return ( platforms.get(0).detectCollision(dangerHitBox) || platforms.get(1).detectCollision(dangerHitBox));
        }
        else {
            return platforms.get(0).detectCollision(dangerHitBox);
        }
    }



    public void displayObstacle() {
        lastDisplayed = System.nanoTime();
        for(PlatformInterface platformInterface : platforms) {
            platformInterface.setVisibility(true);
        }
    }

    @Override
    public void updateGameObject() {

        boolean visibleObstacle = platforms.get(0).getVisibility();
        boolean delayOver = true;

        if(visibleObstacle) {
            Long now = System.nanoTime();
            delayOver = ( (now - lastDisplayed) > VISIBILITY_DELAY);
        }

        if(!platforms.isEmpty()) {
            for(PlatformInterface platform : platforms) {
                platform.setVisibility(!delayOver);
                move();
            }
        }

    }


    @Override
    public void move() {
        if (canMove) {
            int speed = platforms.get(0).getSPEED();

            for (PlatformInterface platform : platforms) {
                platform.move();
            }
            platformsGap.offset(direction * speed, 0);

            if (platformsGap.right < 0) {
                gameView.deleteObstacle(this);
                platforms.clear();
            }
        }
    }

    @Override
    public void drawGameObject(Canvas canvas) {
        for(PlatformInterface platform : platforms) {
            platform.drawGameObject(canvas);
        }
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        for(PlatformInterface platform : platforms){
            platform.setDirection(direction);
        }
    }
}
