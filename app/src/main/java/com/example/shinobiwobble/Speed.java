package com.example.shinobiwobble;

public class Speed{

private float xv;// скорость по оси X
private float yv;// скорость по оси Y

public Speed(float xv, float yv){
        this.xv= xv;
        this.yv= yv;
        }

public float getXv(){
        return xv;
        }
public void setXv(float xv){
        this.xv= xv;
        }
public float getYv(){
        return yv;
        }
public void setYv(float yv){
        this.yv= yv;
        }

// меняем направление по X
public void toggleXDirection(){
        xv=-xv;
        }

// меняем направление по Y
public void toggleYDirection(){
        yv=-yv;
        }
        }
