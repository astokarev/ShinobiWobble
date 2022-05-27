package com.example.shinobiwobble;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private static final String TAG = GameView.class.getSimpleName();

    private GameThread mTread;
    private Thread ghostThread = new Thread(this);
    private Ghost ghost;
    private Player player;
    private Shuriken shuriken ;

    private SurfaceHolder surfaceHolder;
    private GameView gameView;

    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private ArrayList<Shuriken> shurikens = new ArrayList<>();
    private final int GHOST_INTERVAL = 50; // время через которое появляются астероиды (в итерациях)
    private int currentTime = 0;
    public int shotX;
    public int shotY;
    private Bitmap enemies;

    public GameView(Context context) {
        super(context);
        mTread = new GameThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                mTread.setRunning(true);
                mTread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                boolean retry = true;
                mTread.setRunning(false);
                while (retry)
                {
                    try
                    {
                        // ожидание завершение потока
                        mTread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) { }
                }
            }
        });
        ghostThread.start();
        enemies = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        ghosts.add(new Ghost(this, enemies));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player),400 , 100);

        setFocusable(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        shotX = (int) event.getX();
        shotY = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //ghost.handleActionDown((int) event.getX(), (int) event.getY());
            shurikens.add(createSprite(R.drawable.bullet));
            Log.d(TAG, "Coords: x: " + event.getX() + ", y: " + event.getY());
        }
        return super.onTouchEvent(event);
    }

    private Shuriken createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Shuriken(this, bmp, player.getX(), player.getY());
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Iterator<Shuriken> i = shurikens.iterator();

        while (i.hasNext()){
            Shuriken s = i.next();
            s.draw(canvas);
        }
        Iterator<Ghost> g = ghosts.iterator();
        while (g.hasNext()){
            Ghost ghost = g.next();
            if(ghost.getX()>=2000 || ghost.getX()<=2000){
                ghost.draw(canvas);
            } else g.remove();

        }
        //ghost.draw(canvas);
        player.draw(canvas);

        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), 10,15,null);
    }

   //

    @Override
    public void run() {
        while(true) {
            testCollision();
            Random rnd = new Random();
            try {

                ghostThread.sleep(2000);
                ghosts.add(new Ghost(this, enemies));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void testCollision() {
        Iterator<Shuriken> b = shurikens.iterator();
        while(b.hasNext()) {
            Shuriken balls = b.next();
            Iterator<Ghost> i = ghosts.iterator();
            while(i.hasNext()) {
                Ghost enemies = i.next();

                if ((Math.abs(balls.getX() - enemies.getX()) <= (balls.getBitmap().getWidth() + enemies.getBitmap().getWidth()) / 2)
                        && (Math.abs(balls.getY() - enemies.getY()) <= (balls.getBitmap().getHeight() + enemies.getBitmap().getHeight()) / 2)) {
                    i.remove();
                    b.remove();
                }
            }
        }
    }
}
