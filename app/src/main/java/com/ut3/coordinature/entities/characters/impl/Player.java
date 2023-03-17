package com.ut3.coordinature.entities.characters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.ut3.coordinature.R;
import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.GameObject;
import com.ut3.coordinature.entities.Movable;
import com.ut3.coordinature.entities.characters.BitmapCharacter;

public class Player extends BitmapCharacter implements Collidable, Movable, GameObject {
    private static final float VELOCITY = 0.5f;

    private final int ROW_LEFT_TO_RIGHT = 1;
    private final Bitmap[] leftToRight;

    private final Rect hitbox;

    private final boolean canMove;
    private int colUsing;

    private int obstaclePassed;

    private long lastDrawnNanoTime = -1;

    public Player(Bitmap spriteSheet, int xPos, int yPos) {
        super(spriteSheet, 1, 2, xPos, yPos);

        this.leftToRight = new Bitmap[colCount];

        for(int col = 0; col < getColCount(); col++){
            this.leftToRight[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
        }

        this.hitbox = new Rect(xPos, yPos, xPos + this.SPRITE_WIDTH, yPos + this.SPRITE_HEIGHT);

        this.canMove = false;
        this.colUsing = 0;
    }

    public Bitmap getCurrentMoveBitmap() {
        Bitmap[] bitmaps = this.leftToRight;
        return bitmaps[this.colUsing];
    }

    @Override
    public void updateGameObject(){
        this.colUsing = (this.colUsing + 1) % this.colCount;

        //Get current time
        long now = System.nanoTime();
        //If never drawn
        if(lastDrawnNanoTime == -1){
            lastDrawnNanoTime = now;
        }

        int deltaTime = (int) ((now - lastDrawnNanoTime) / 1000000);

        //Distance moves
        float distance = VELOCITY * deltaTime;
    }
    @Override
    public void drawGameObject(Canvas canvas){
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap, xPos, yPos, null);

        //Update timer
        this.lastDrawnNanoTime = System.nanoTime();
    }

    public Rect getHitbox() { return hitbox;}


    @Override
    public boolean detectCollision(Rect dangerHitBox) {
        return false;
    }

    @Override
    public void move() {

    }

    public int getObstaclePassed() {
        return obstaclePassed;
    }

    public void setObstaclePassed(int obstaclePassed) {
        this.obstaclePassed = obstaclePassed;
    }
}
