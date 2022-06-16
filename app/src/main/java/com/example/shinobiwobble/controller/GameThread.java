package com.example.shinobiwobble.controller;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private static final String TAG = GameThread.class.getSimpleName();

    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }


    @SuppressLint("WrongCall")

    @Override
    public void run() {
        Canvas canvas;
        long tickCount = 0L;
        Log.d(TAG, "Game loop started");
        while (running) {
            canvas = null;
            try {
                canvas = gameView.getHolder().lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.testCollision();
                    this.gameView.onDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }
}
