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
    private final Long VISIBILITY_DELAY = 3000000000L;
    private final Long lastDisplayed;

    public Obstacle(Platform bottomPlatform, Platform topPlatform) {
        platforms = new ArrayList<>();
        platforms.add(bottomPlatform);
        platforms.add(topPlatform);
        lastDisplayed = 0L;
    }

    public Obstacle(Platform singlePlatform) {
        platforms = new ArrayList<>();
        platforms.add(singlePlatform);
        lastDisplayed = 0L;
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
    public void update() {

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
    public void draw (Canvas canvas) {
        for(PlatformInterface platform : platforms) {
            platform.draw(canvas);
        }
    }

}
