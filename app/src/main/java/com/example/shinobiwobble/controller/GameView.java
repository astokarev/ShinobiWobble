package com.example.shinobiwobble.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shinobiwobble.R;
import com.example.shinobiwobble.model.Ghost;
import com.example.shinobiwobble.model.Player;
import com.example.shinobiwobble.model.Shuriken;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements Runnable,  SurfaceHolder.Callback {
    private static final String TAG = GameView.class.getSimpleName();

    private GameThread mTread;
    private Thread ghostThread = new Thread(this);
    private Player player;
    public int count = 0;

    private CopyOnWriteArrayList<Ghost> ghosts = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Shuriken> shurikens = new CopyOnWriteArrayList<>();
    public int shotX;
    public int shotY;
    private Bitmap enemies;
    TextView tw;
    LinearLayout layout;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        layout = new LinearLayout(context);
        tw = new TextView(context);
        tw.setText("test " + count);
        tw.setVisibility(View.VISIBLE);
        layout.addView(tw);

        mTread = new GameThread(getHolder(), this);
        ghostThread.start();
        enemies = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        //ghosts.add(new Ghost(enemies));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player), 300, 500, 0);
        TextView tw = new TextView(context);
        tw.append("test" + count);
        setFocusable(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        shotX = (int) event.getX();
        shotY = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            shurikens.add(createSprite(R.drawable.bullet));
            Log.d(TAG, "Coords: x: " + event.getX() + ", y: " + event.getY());
        }
        return super.onTouchEvent(event);
    }

    private Shuriken createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Shuriken(bmp, player.getX(), player.getY(), shotX, shotY);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(55);
        canvas.drawText("Score: " + count, 100, 100, paint);

        Iterator<Shuriken> i = shurikens.iterator();
        while (i.hasNext()) {
            Shuriken s = i.next();
            s.draw(canvas);
            //testCollision();
        }
        Iterator<Ghost> g = ghosts.iterator();
        while (g.hasNext()) {
            Ghost ghost = g.next();
            if (ghost.getX() >= 2000 || ghost.getX() <= 2000) {
                ghost.draw(canvas);
                //testCollision();
            } else g.remove();

        }
        //ghost.draw(canvas);
        player.draw(canvas);

        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), 10,15,null);
    }

    //

    @Override
     public void run() {
        while (true) {
            testCollision();
            try {
                ghostThread.sleep(2000);
                ghosts.add(new Ghost(enemies));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testCollision() {
        Iterator<Shuriken> b = shurikens.iterator();
        while (b.hasNext()) {
            Shuriken balls = b.next();
            Iterator<Ghost> i = ghosts.iterator();
            while (i.hasNext()) {
                Ghost enemies = i.next();
                if ((Math.abs(balls.getX() - enemies.getX()) <= (balls.getBitmap().getWidth() + enemies.getBitmap().getWidth()) / 2)
                        && (Math.abs(balls.getY() - enemies.getY()) <= (balls.getBitmap().getHeight() + enemies.getBitmap().getHeight()) / 2)) {
                    count++;
                    i.remove();
                    b.remove();
                }
            }
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mTread.setRunning(true);
        mTread.start();
        // mTread.sleep(2000);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        mTread.setRunning(false);
        while (retry) {
            try {
                // ожидание завершение потока
                mTread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}

