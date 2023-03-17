package com.ut3.coordinature.entities.obstacles.impl;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.Movable;
import com.ut3.coordinature.entities.obstacles.PlatformInterface;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements Movable, Collidable {

    // An obstacle is an alignment of one or two platforms
    private final List<PlatformInterface> platforms;

    public Obstacle(Platform bottomPlatform, Platform topPlatform) {
        platforms = new ArrayList<>();
        platforms.add(bottomPlatform);
        platforms.add(topPlatform);
    }

    public Obstacle(Platform singlePlatform) {
        platforms = new ArrayList<>();
        platforms.add(singlePlatform);
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

    @Override
    public void move() {
        for(PlatformInterface platform : platforms) {
            platform.move();
        }
    }

    public void changeColor(int color) {
        for(PlatformInterface platform : platforms) {
            platform.setColor(color);
        }
    }

    public void draw (Canvas canvas) {
        for(PlatformInterface platform : platforms) {
            platform.draw(canvas);
        }
    }

}
