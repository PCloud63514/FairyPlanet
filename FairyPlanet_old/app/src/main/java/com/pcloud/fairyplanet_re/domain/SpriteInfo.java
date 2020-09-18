package com.pcloud.fairyplanet_re.domain;

/**
 * Created by PCloud on 2018-01-03.
 */

public class SpriteInfo {
    private String n;
    private int x;
    private int y;
    private int w;
    private int h;
    private float pX;
    private float pY;

    public SpriteInfo(String n, int x, int y, int w, int h, float pX, float pY) {
        this.n = n;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.pX = pX;
        this.pY = pY;
    }
    public SpriteInfo() {

    }
    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
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

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public float getpX() {
        return pX;
    }

    public void setpX(float pX) {
        this.pX = pX;
    }

    public float getpY() {
        return pY;
    }

    public void setpY(float pY) {
        this.pY = pY;
    }
}
