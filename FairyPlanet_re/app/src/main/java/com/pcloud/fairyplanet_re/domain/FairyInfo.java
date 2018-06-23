package com.pcloud.fairyplanet_re.domain;

import com.pcloud.fairyplanet_re.inface.UnitAction;
import com.pcloud.fairyplanet_re.util.SaveManager;

import java.text.SimpleDateFormat;

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

    public FairyInfo(String unitName, int health, double hunger, double tired, int feelings, String unitAction, double previous) {
        this.unitName = unitName;
        this.health = health;
        this.hunger = hunger;
        this.tired = tired;
        this.feelings = feelings;
        setUnitAction(unitAction);
        this.previous = previous;
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

    public double getPrevious() {
        return previous;
    }

    public void setPrevious(double previous) {
        this.previous = previous;
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
                        + "\n===============================\n");
    }
}
