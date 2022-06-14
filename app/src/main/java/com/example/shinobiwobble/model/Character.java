package com.example.shinobiwobble.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Iterator;

public class Character {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed;

    public Character(Bitmap bmp, int x, int y, int speed){
        this.bitmap = bmp;
        this.x  =x;
        this.y = y;
        this.speed = speed;
    }

    public Character(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Character(Character ch){
        bitmap = ch.bitmap;
        x = ch.getX();
        y = ch.getY();
        speed = ch.speed;
    }

    public Character(Bitmap bmp, int x, int y) {
        this.bitmap = bmp;
        this.x = x;
        this.y = y;
    }


    public void update() {

    }

    public void draw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x , y, null);
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

    /*private void testCollision(Character ch1, Character ch2) {
        Iterator<Shuriken> b = shurikens.iterator();
        while(b.hasNext()) {
            Shuriken balls = b.next();
            Iterator<Ghost> i = ghosts.iterator();
            while(i.hasNext()) {
                Ghost enemies = i.next();
                if ((Math.abs(balls.getX() - enemies.getX()) <= (balls.getBitmap().getWidth() + enemies.getBitmap().getWidth()) / 2)
                        && (Math.abs(balls.getY() - enemies.getY()) <= (balls.getBitmap().getHeight() + enemies.getBitmap().getHeight()) / 2)) {
                    count++;
                    i.remove();
                    b.remove();
                }
            }
        }
    }*/
}
