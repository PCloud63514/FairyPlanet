package com.pcloud.fairyplanet_re.domain;

import android.util.DisplayMetrics;
import android.util.Log;

import com.pcloud.fairyplanet_re.inface.UnitAction;
import com.pcloud.fairyplanet_re.util.MyApplication;
import com.pcloud.fairyplanet_re.util.SaveManager;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by PCloud on 2018-06-17.
 */

public class FairyInfo extends UnitInfo {
    private static FairyInfo fairyInfo;

    private String unitName;
    private int health;
    private double hunger;
    private double tired;
    private int feelings;
    private UnitAction unitAction;
    private double previous;


    private Random random;
    private int verticalFocus = 1; // -1 left 1 right
    private int horizontalFocus = 1;// 1 up -1 down

    public FairyInfo(String unitName, int health, double hunger, double tired, int feelings, String unitAction, double previous) {
        this.unitName = unitName;
        this.health = health;
        this.hunger = hunger;
        this.tired = tired;
        this.feelings = feelings;
        setUnitAction(unitAction);
        this.previous = previous;
        this.random = new Random(System.currentTimeMillis());
    }

    public static synchronized FairyInfo getInstance() {
        if(fairyInfo == null) {
            try {
                fairyInfo = SaveManager.load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fairyInfo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    public double getTired() {
        return tired;
    }

    public void setTired(double tired) {
        this.tired = tired;
    }

    public int getFeelings() {
        return feelings;
    }

    public void setFeelings(int feelings) {
        this.feelings = feelings;
    }

    public UnitAction getUnitAction() {
        return unitAction;
    }

    public String getUnitActionToString() {
        switch (this.unitAction) {
            case idle: return "idle";
            case hungry: return "hungry";
            case like: return "like";
            case move: return "move";
            case tired: return "tired";
            case seek: return "seek";
            case dislike: return "dislike";
            default: return "idle";
        }
    }
    public void setUnitAction(UnitAction unitAction) {
        this.unitAction = unitAction;
    }

    public void setUnitAction(String str) {
        switch (str) {
            case "idle": this.unitAction = UnitAction.idle;
            case "hungry": this.unitAction = UnitAction.hungry;
            case "like": this.unitAction = UnitAction.like;
            case "move": this.unitAction = UnitAction.move;
            case "tired": this.unitAction = UnitAction.tired;
            case "seek": this.unitAction = UnitAction.seek;
            case "dislike": this.unitAction = UnitAction.dislike;

            default: this.unitAction = UnitAction.idle;
        }
    }

    private String unitActionToString() {
        switch (this.unitAction) {
            case idle: return "idle";
            case hungry: return "hungry";
            case like: return "like";
            case move: return "move";
            case tired: return "tired";
            case seek: return "seek";
            case dislike: return "dislike";
            default: return "none";
        }
    }

    public int getVerticalFocus() {
        return verticalFocus;
    }


    public int getHorizontalFocus() {
        return horizontalFocus;
    }

    public double getPrevious() {
        return previous;
    }

    public void setPrevious(double previous) {
        this.previous = previous;
    }

    public void setVerticalFocus() {
        //TODO 현재 좌표가 음수인지 양수인지 구함 0일 경우 리턴
        //520/100 = 5.2
        if (0 < windowPointX) {
            //돌지않을 확률
            float turnProb = (1.0f - ((float)windowPointX / getWindowMaxX())) / 2.0f;
            Log.d("Turn",String.valueOf(turnProb));
            if (turnProb < random.nextFloat()) {
                verticalFocus = -1;
            } else {
                verticalFocus = 1;
            }

        } else if (0 > windowPointX) {
            //음수
            //돌지않을 확률
            float turnProb = (1.0f - ((float)-windowPointX / getWindowMaxX())) / 2.0f;
            Log.d("Turn",String.valueOf(turnProb));
            if (turnProb < random.nextFloat()) {
                //회전
                verticalFocus = 1;
            } else {
                //가만히
                verticalFocus = -1;
            }
        } else {
            if(random.nextFloat() <= 0.49) {
                verticalFocus = -1;
            } else {
                verticalFocus = 1;
            }
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

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");

        return new String(
                "\n==============================="
                        + "\nNow Time:" + simpleDateFormat.format(System.currentTimeMillis())
                        + "\nprevious:" + simpleDateFormat.format(this.previous)
                        + "\nunitName:" + unitName
                        + "\nhealth:" + health
                        + "\nhunger:" + String.format("%.1f", hunger)
                        + "\ntired:" + String.format("%.1f",tired)
                        + "\nfeelings:" + feelings
                        + "\nunitAction:" + unitActionToString()
                        + "\nVerticalFocus:" + getVerticalFocus()
                        + "\nWindowX:" + getWindowPointX()
                        + "\nWindowY:" + getWindowPointY()
                        + "\n===============================\n");
    }
}
