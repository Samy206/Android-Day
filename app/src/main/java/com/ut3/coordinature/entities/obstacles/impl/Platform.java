package com.ut3.coordinature.entities.obstacles.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ut3.coordinature.entities.obstacles.PlatformInterface;

public class Platform implements PlatformInterface {

    private final Rect hitBox;
    private int color;
    private final int SPEED = 4;

    public Platform(Rect source) {
        this.hitBox = source;
        color = Color.BLACK;
    }

    public Platform(int left, int top, int right, int bottom) {
        this.hitBox = new Rect(left, top, right, bottom);
        color = Color.BLACK;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void draw (Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(hitBox, paint);
    }

    @Override
    public boolean detectCollision(Rect dangerHitBox) {
        return (dangerHitBox != null) && hitBox.intersect(dangerHitBox);
    }

    @Override
    public void move() {
        this.hitBox.offset(SPEED, 0);
    }
}
