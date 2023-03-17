package com.ut3.coordinature.entities.characters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.ut3.coordinature.R;
import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.GameObject;
import com.ut3.coordinature.entities.Movable;
import com.ut3.coordinature.entities.characters.BitmapCharacter;
import com.ut3.coordinature.entities.obstacles.impl.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class Player extends BitmapCharacter implements Collidable, Movable, GameObject {
    private static final float VELOCITY = 0.5f;

    private final int ROW_LEFT_TO_RIGHT = 1;
    private final Bitmap[] leftToRight;

    private final Rect hitBox;

    private final boolean canMove;
    private int colUsing;

    private List<Obstacle> obstaclePassed;

    private long lastDrawnNanoTime = -1;

    public Player(Bitmap spriteSheet, int xPos, int yPos) {
        super(spriteSheet, 1, 2, xPos, yPos);
        this.obstaclePassed = new ArrayList<>();
        this.leftToRight = new Bitmap[colCount];

        for(int col = 0; col < getColCount(); col++){
            this.leftToRight[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
        }

        this.hitBox = new Rect(xPos, yPos, xPos + this.SPRITE_WIDTH, yPos + this.SPRITE_HEIGHT);

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

    public Rect gethitBox() { return hitBox;}


    @Override
    public boolean detectCollision(Rect dangerhitBox) {
        return (dangerhitBox != null) && hitBox.intersect(dangerhitBox);
    }

    @Override
    public void move() {

    }

    public List<Obstacle> getObstaclePassed() {
        return obstaclePassed;
    }

    public boolean obstaclePassedCheck(Obstacle obstacle) {
        if(obstaclePassed.contains(obstacle))
            return false;

        if(detectCollision(obstacle.getPlatformsGap())) {
            obstaclePassed.add(obstacle);
            return true;
        }
        return false;
    }
}
