package com.ut3.coordinature.entities.obstacles;

import android.graphics.Canvas;

import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.Movable;

public interface PlatformInterface extends Movable, Collidable {

    void setColor(int color);

    void draw(Canvas canvas);

}
