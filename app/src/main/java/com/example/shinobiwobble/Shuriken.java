package com.example.shinobiwobble;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Shuriken {
    private int x;
    private int y;
    private Bitmap bitmap;
    private int speed = 15;
    public double angle;

    public void update() {
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
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

    public Shuriken(GameView gameView, Bitmap bitmap, int x, int y ) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        Speed speed = new Speed(10, 10);
        angle = Math.atan((double)(y - gameView.shotY) / (x - gameView.shotX));
    }

    public void draw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
}
