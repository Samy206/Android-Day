package com.ut3.coordinature.entities.obstacles.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ut3.coordinature.entities.GameObject;
import com.ut3.coordinature.entities.obstacles.PlatformInterface;

public class Platform implements PlatformInterface, GameObject {

    private final Rect hitBox;
    private boolean visible;
    private final int SPEED = 4;


    public Platform(Rect source) {
        hitBox = source;
        visible = false;
    }

    public Platform(int left, int top, int right, int bottom) {
        hitBox = new Rect(left, top, right, bottom);
        visible = false;
    }

    @Override
    public void setVisibility(boolean visibility) {
        visible = visibility;
    }

    @Override
    public boolean getVisibility() {
        return visible;
    }

    @Override
    public Rect getHitBox() {
        return hitBox;
    }

    @Override
    public void drawGameObject(Canvas canvas) {
        Paint paint = new Paint();
        if(visible)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(hitBox, paint);
    }

    @Override
    public boolean detectCollision(Rect dangerHitBox) {
        return (dangerHitBox != null) && hitBox.intersect(dangerHitBox);
    }

    @Override
    public void updateGameObject() {
        move();
    }

    @Override
    public void move() {
        this.hitBox.offset(SPEED, 0);
    }


}
