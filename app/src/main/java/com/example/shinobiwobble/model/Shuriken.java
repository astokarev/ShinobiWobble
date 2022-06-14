package com.example.shinobiwobble.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.shinobiwobble.controller.GameView;

public class Shuriken extends Character  {
    private int x;
    private int y;
    private int shotX;
    private int shotY;
    private Bitmap bitmap;
    private int speed = 15;
    public double angle;



    public Shuriken(Bitmap bmp, int x, int y, int shotX, int shotY) {
        super(bmp, x, y);
        this.x = x;
        this.y = y;
        this.shotX = shotX;
        this.shotY = shotY;
        this.bitmap = bmp;
    }

    public void update() {
        angle = Math.atan((double)(y - shotY) / (x - shotX));;
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setShotX(int shotX) {
        this.shotX = shotX;
    }

    public void setShotY(int shotY) {
        this.shotY = shotY;
    }

/*    public Shuriken(GameView gameView, Bitmap bitmap, int x, int y ) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        angle = Math.atan((double)(y - gameView.shotY) / (x - gameView.shotX));
    }*/

    public void draw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
}
