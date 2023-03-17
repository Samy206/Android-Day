package com.ut3.coordinature.entities.characters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.GameObject;
import com.ut3.coordinature.entities.Movable;
import com.ut3.coordinature.entities.characters.BitmapCharacter;
import com.ut3.coordinature.entities.obstacles.impl.Obstacle;

import java.util.ArrayList;
import java.util.List;
import com.ut3.coordinature.gamelogic.main.GameView;

public class Player extends BitmapCharacter implements Collidable, Movable, GameObject {
    private static final float VELOCITY = 1f;

    private final int ROW_LEFT_TO_RIGHT = 0;
    private final Bitmap[] leftToRight;

    private final Rect hitBox;

    private boolean canMove;
    private int colUsing;


    private final List<Obstacle> obstaclePassed;

    private long lastDrawnNanoTime = -1;

    private final Matrix movementMatrix;
    private long lastAnimationTime = -1;

    private final int PLAYER_SCALE = 3;

    private final GameView gameView;

    private int direction = 0;

    public Player(GameView gameView, Bitmap spriteSheet, int xPos, int yPos) {
        super(spriteSheet, 1, 2, xPos, yPos);
        this.obstaclePassed = new ArrayList<>();
        this.leftToRight = new Bitmap[colCount];

        for(int col = 0; col < getColCount(); col++){
            this.leftToRight[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
        }

        this.hitBox = new Rect(xPos, yPos, xPos + PLAYER_SCALE * this.SPRITE_WIDTH, yPos + PLAYER_SCALE *this.SPRITE_HEIGHT);

        this.canMove = false;
        this.colUsing = 0;

        this.gameView = gameView;

        movementMatrix = new Matrix();
    }

    public Bitmap getCurrentMoveBitmap() {
        Bitmap[] bitmaps = this.leftToRight;
        return bitmaps[this.colUsing];
    }

    private void movementMatrixUpdate(){
        movementMatrix.reset();
        movementMatrix.postScale(PLAYER_SCALE, PLAYER_SCALE);
        movementMatrix.postTranslate(xPos, yPos);

    }

    private void setTimers(long now){
        //If never drawn
        if(lastDrawnNanoTime == -1){
            lastDrawnNanoTime = now;
        }
        if(lastAnimationTime == -1){
            lastAnimationTime = now;
        }
    }

    @Override
    public void updateGameObject(){

        //Get current time
        long now = System.nanoTime();

        setTimers(now);

        int deltaTimeAnimation = (int) ((now - lastAnimationTime) / 1000000);
        if(deltaTimeAnimation > 200){
            this.colUsing = (this.colUsing + 1) % this.colCount;
            lastAnimationTime = now;
        }
        if(canMove){
            int deltaTime = (int) ((now - lastDrawnNanoTime) / 1000000);
            //Distance moves
            float distance = VELOCITY * deltaTime;
            int offsetY = (int)(direction * distance);

            this.yPos += offsetY;
            int reposition = yPos - this.hitBox.top;

            if(yPos <= 0 ){
                yPos = 0;
                this.hitBox.offset(0, reposition );
            }else if(yPos >= this.gameView.getHeight()- SPRITE_HEIGHT * (PLAYER_SCALE)){
                yPos = this.gameView.getHeight() - SPRITE_HEIGHT * PLAYER_SCALE;
                this.hitBox.offset(0, reposition);
            }else{
                this.hitBox.offset(0, offsetY);
            }

            //setCanMove(false);
        }

    }
    @Override
    public void drawGameObject(Canvas canvas){
        Bitmap bitmap = this.getCurrentMoveBitmap();
        movementMatrixUpdate();
        canvas.drawBitmap(bitmap, movementMatrix, null);

        //canvas.drawBitmap(bitmap, xPos, yPos, null);

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

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setCanMove(boolean b) {
        this.canMove = b;
    }
}
