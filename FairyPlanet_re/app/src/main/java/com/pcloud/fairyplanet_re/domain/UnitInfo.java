package com.pcloud.fairyplanet_re.domain;

import android.util.DisplayMetrics;
import android.util.Log;

import com.pcloud.fairyplanet_re.Meta;
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
}
