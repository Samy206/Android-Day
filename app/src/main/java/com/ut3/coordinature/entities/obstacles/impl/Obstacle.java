package com.ut3.coordinature.entities.obstacles.impl;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.GameObject;
import com.ut3.coordinature.entities.Movable;
import com.ut3.coordinature.entities.obstacles.PlatformInterface;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements Movable, Collidable, GameObject {

    // An obstacle is an alignment of one or two platforms
    private final List<PlatformInterface> platforms;
    // Platform gap to detect if the player has been in it
    private Rect platformsGap;

    //
    private final Long lastDisplayed;
    private final Long VISIBILITY_DELAY = 3000000000L;
    private int maxHeight;

    public Obstacle(Platform bottomPlatform, Platform topPlatform, int windowHeight) {
        platforms = new ArrayList<>();
        platforms.add(bottomPlatform);
        platforms.add(topPlatform);
        maxHeight = windowHeight;
        lastDisplayed = 0L;
        setupPlatformGap();
    }

    public Obstacle(Platform singlePlatform) {
        platforms = new ArrayList<>();
        platforms.add(singlePlatform);
        lastDisplayed = 0L;
    }

    public Rect getPlatformsGap() {
        return platformsGap;
    }

    private void setupPlatformGap() {
        Rect platformZero = platforms.get(0).getHitBox();
        int platformWidth = platformZero.width();
        if(platforms.size() == 2) {

            // Second platform will always be the top one
            Rect platformOne = platforms.get(1).getHitBox();

            // bottom left top platform
            int leftX = platformOne.left;
            int leftY = platformOne.bottom;

            // gap height between two platforms
            int gap = platformOne.bottom - platformOne.top;

            platformsGap = new Rect(leftX, leftY, leftX + platformWidth, leftY - gap);
        }
        else {

            /* if there is only one platform in an obstacle, it will start from the bottom and not
            reach the max height */
            platformsGap = new Rect(platformZero.left, maxHeight,
                    platformZero.left + platformWidth, platformZero.top);

        }
    }

    @Override
    public boolean detectCollision(Rect dangerHitBox) {
        if(platforms.size() == 2) {
            return ( platforms.get(0).detectCollision(dangerHitBox) && platforms.get(1).detectCollision(dangerHitBox));
        }
        else {
            return platforms.get(0).detectCollision(dangerHitBox);
        }
    }



    public void displayObstacle() {
        for(PlatformInterface platformInterface : platforms) {
            platformInterface.setVisibility(true);
        }
    }

    @Override
    public void updateGameObject() {

        boolean visibleObstacle = platforms.get(0).getVisibility();
        boolean delayOver = false;

        if(visibleObstacle) {
            Long now = System.nanoTime();
            delayOver = ( (now - lastDisplayed) > VISIBILITY_DELAY);
        }

        for(PlatformInterface platform : platforms) {
            platform.setVisibility(!delayOver);
            move();
        }

    }


    @Override
    public void move() {
        for(PlatformInterface platform : platforms) {
            platform.move();
        }
    }

    @Override
    public void drawGameObject(Canvas canvas) {
        for(PlatformInterface platform : platforms) {
            platform.drawGameObject(canvas);
        }
    }

}
