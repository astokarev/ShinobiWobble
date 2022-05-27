package com.example.shinobiwobble;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
    private static final String TAG = GameThread.class.getSimpleName();

    private boolean running;
    private GameView gameView;


    public GameThread( GameView gameView){
        super();
        this.gameView = gameView;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas canvas;
        long tickCount = 0L;
        Log.d(TAG, "Game loop started");
        while (running){
            canvas = null;
            try{
                canvas = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()){
                    //this.gameView.update();
                    gameView.onDraw(canvas);
                }
            }finally {
                if(canvas != null){gameView.getHolder().unlockCanvasAndPost(canvas);}
            }
        }
        Log.d(TAG,"Game loop executed "+ tickCount+" times");
    }
}
