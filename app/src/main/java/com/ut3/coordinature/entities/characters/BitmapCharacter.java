package com.ut3.coordinature.entities.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public abstract class BitmapCharacter {
    protected final Bitmap spriteSheet;

    protected final int rowCount;
    protected final int colCount;

    protected final int SPRITESHEET_WIDTH;
    protected final int SPRITESHEET_HEIGHT;

    protected final int SPRITE_WIDTH;
    protected final int SPRITE_HEIGHT;

    protected int xPos;
    protected int yPos;

    public BitmapCharacter(Bitmap spriteSheet, int rowCount, int colCount, int xPos, int yPos) {
        this.spriteSheet = spriteSheet;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.xPos = xPos;
        this.yPos = yPos;

        this.SPRITESHEET_WIDTH = spriteSheet.getWidth();
        this.SPRITESHEET_HEIGHT = spriteSheet.getHeight();

        this.SPRITE_WIDTH = SPRITESHEET_WIDTH / colCount;
        this.SPRITE_HEIGHT = SPRITESHEET_HEIGHT / rowCount;
    }

    protected Bitmap createSubImageAt(int row, int col){
        Bitmap subImage = Bitmap.createBitmap(spriteSheet, col * SPRITE_WIDTH, row * SPRITE_HEIGHT, SPRITE_WIDTH,  SPRITE_HEIGHT);
        return subImage;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getColCount(){
        return colCount;
    }
}
