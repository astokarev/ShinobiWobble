package com.example.shinobiwobble;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class Ghost {

    private Bitmap bitmap;
    private int x;
    private int y;
    private boolean touched;
    private int speed;
    public GameView gameView;


    public Ghost(GameView gameView, Bitmap bitmap) {
        this.gameView = gameView;
        this.bitmap = bitmap;

        Random rnd = new Random();
        this.x = 2000;
        this.y = rnd.nextInt(300);
        this.speed = rnd.nextInt(10);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void draw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x , y, null);
    }



    public void update() {
            x -= speed;
    }

    public int getSpeed() {
        return speed;
    }

}
