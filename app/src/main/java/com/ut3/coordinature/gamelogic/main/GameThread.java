package com.ut3.coordinature.gamelogic.main;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends  Thread{
    public final SurfaceHolder surfaceHolder;

    private final GameView gameView;

    private boolean running;

    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run(){
        long startTime = System.nanoTime();
        while (running) {
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            long now = System.nanoTime();
            // Interval to redraw game
            // Change nanoseconds to milliseconds
            long waitTime = (now - startTime) / 1000000;
            try {
                if (waitTime < 30)
                    sleep(30 - waitTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
