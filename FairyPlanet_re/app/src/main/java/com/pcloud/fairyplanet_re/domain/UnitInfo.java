package com.pcloud.fairyplanet_re.domain;

import android.util.DisplayMetrics;
<<<<<<< HEAD
import android.util.Log;

import com.pcloud.fairyplanet_re.Meta;
=======

>>>>>>> 4d260320174275cfe21996d3b807158c21fd9e54
import com.pcloud.fairyplanet_re.util.MyApplication;
import com.pcloud.fairyplanet_re.util.UnitInfoBuilder;

import java.util.Random;

/**
 * Created by PCloud on 2018-05-29.
 */
//정보갱신을 하는 시간을 어떻게 정할 것인가.
//UnitInfo는 기본적으로 모든 유닛이 공유하게 만들 것이다.
// UnitID
// Size
// CallTime
// X,Y Point

public class UnitInfo {

    private String unitNo;
    private int magn;
    private final float callTime;
<<<<<<< HEAD
    protected DisplayMetrics displayMetrics;
    protected int windowMaxX;
    protected int windowMaxY;
    protected int windowPointX;
    protected int windowPointY;



    public UnitInfo() {
        this.callTime = System.currentTimeMillis();
        this.displayMetrics = MyApplication.getAppContext().getResources().getDisplayMetrics();
        this.windowMaxX = (displayMetrics.widthPixels / 2) - Meta.VERTICAL_MARGIN;
        this.windowMaxY = (displayMetrics.heightPixels / 2) - Meta.HORIZONTAL_MARGIN;
        this.windowPointX = 0;
        this.windowPointY = 0;
        Log.d("TEST","MAX_X:" + windowMaxX + "/ MAX_Y:" + windowMaxY);
=======
    private int windowPointX;
    private int windowPointY;

    private DisplayMetrics displayMetrics;
    private Random random;
    private int verticalFocus = 1; // -1 left 1 right
    private int horizontalFocus = 1;// 1 up -1 down

    public UnitInfo() {
        this.callTime = System.currentTimeMillis();
        this.windowPointX = 0;
        this.windowPointY = 0;
        this.displayMetrics = MyApplication.getAppContext().getResources().getDisplayMetrics();
        this.random = new Random(System.currentTimeMillis());
>>>>>>> 4d260320174275cfe21996d3b807158c21fd9e54
    }


    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public int getMagn() {
        return magn;
    }

    public void setMagn(int magn) {
        this.magn = magn;
    }

    public float getCallTime() {
        return callTime;
    }

    public int getWindowPointX() {
        return windowPointX;
    }

    public void setWindowPointX(int windowPointX) {
        this.windowPointX = windowPointX;
    }

    public int getWindowPointY() {
        return windowPointY;
    }

    public void setWindowPointY(int windowPointY) {
        this.windowPointY = windowPointY;
    }

<<<<<<< HEAD
    public static UnitInfoBuilder Builder() {
        return new UnitInfoBuilder();
    }

    public synchronized int getWindowMaxX() {
        return windowMaxX;
    }

    public synchronized void setWindowMaxX(int windowMaxX) {
        this.windowMaxX = windowMaxX;
    }

    public synchronized int getWindowMaxY() {
        return windowMaxY;
    }

    public synchronized void setWindowMaxY(int windowMaxY) {
        this.windowMaxY = windowMaxY;
    }
=======
    public void setVerticalFocus() {
        int width = displayMetrics.widthPixels / 2;
        //TODO 현재 좌표가 음수인지 양수인지 구함 0일 경우 리턴
        //520/100 = 5.2
        if (0 < windowPointX) {
            float turnProb = 1.0f - ((1.0f - (windowPointX / width)) / 2.0f);
            if (random.nextFloat() <= turnProb) {
                verticalFocus = -1;
            } else {
                verticalFocus = 1;
            }

        } else if (0 > windowPointX) {
            //음수
            float turnProb = 1.0f - ((1.0f - (-windowPointX / width)) / 2.0f);
            if (random.nextFloat() <= turnProb) {
                verticalFocus = 1;
            } else {
                verticalFocus = -1;
            }
        } else {
            return;
        }
        //음수일 경우 음수 최대 양수일 경우 양수 최대를 기준으로함
        //양 MAX 500/-500 이라했을 경우 현재 좌표 50 기본 확률 0.0~0.5/0.51~0.99
        //50/(500/100) = 10/100 = 0.1  오른쪽0.4 왼쪽0.6이 나오도록 계산해야함.
        //1 - 0.1 =  0.9 / 2 = 오른쪽
        //1 - 오른쪽 = 왼쪽
        //계산끝났당.
    }

    public void setHorizontalFocus() {
        //현재 좌표가 음수인지 양수인지 구함
        //음수일 경우 음수 최대 양수일 경우 양수 최대를 기준으로함
    }

    public static UnitInfoBuilder Builder() {
        return new UnitInfoBuilder();
    }


>>>>>>> 4d260320174275cfe21996d3b807158c21fd9e54
}
