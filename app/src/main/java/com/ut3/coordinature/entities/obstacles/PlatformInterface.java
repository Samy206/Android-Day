package com.ut3.coordinature.entities.obstacles;

import android.graphics.Canvas;

import com.ut3.coordinature.entities.Collidable;
import com.ut3.coordinature.entities.GameObject;
import com.ut3.coordinature.entities.Movable;

public interface PlatformInterface extends Movable, Collidable, GameObject {

    void setVisibility(boolean visibility);

    boolean getVisibility();

}
