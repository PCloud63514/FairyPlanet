package com.pcloud.fairyplanet_re.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.pcloud.fairyplanet_re.Meta;
import com.pcloud.fairyplanet_re.domain.FairyInfo;
import com.pcloud.fairyplanet_re.domain.UnitInfo;

import org.json.JSONObject;

/**
 * Created by PCloud on 2018-05-30.
 */
    //일단 Preferences로 저장
    //나중에는 Preferences와 googleService에서 드라이브에 저장.

public class SaveManager {
    private static final String TAG = "SaveManager";
    public SaveManager() {
    }

    public static void save() throws Exception {
        SharedPreferences pref = MyApplication.getAppContext().getSharedPreferences("Game", Context.MODE_PRIVATE);
        JSONObject jsonObject = getPreferencesJsonObject(pref,"unitData");

//        jsonObject.put("unitNo", FairyInfo.getInstance().getUnitNo());
        jsonObject.put("unitName",FairyInfo.getInstance().getUnitName());
        jsonObject.put("hunger",FairyInfo.getInstance().getHunger());
        jsonObject.put("health",FairyInfo.getInstance().getHealth());
        jsonObject.put("tired",FairyInfo.getInstance().getTired());
        jsonObject.put("feelings",FairyInfo.getInstance().getFeelings());
        jsonObject.put("unitAction",FairyInfo.getInstance().getUnitActionToString());
        jsonObject.put("previous",FairyInfo.getInstance().getPrevious());

        pref.edit().putString("unitData",jsonObject.toString()).commit();
        Log.d(TAG,jsonObject.toString());
    }


    public static void save(JSONObject jsonObject) throws Exception {
        SharedPreferences pref = MyApplication.getAppContext().getSharedPreferences("Game", Context.MODE_PRIVATE);
        pref.edit().putString("unitData",jsonObject.toString()).commit();
        Log.d(TAG,"First Save!!!!");
        Log.d(TAG,jsonObject.toString());
    }

    public static FairyInfo load() throws Exception {
        SharedPreferences pref = MyApplication.getAppContext().getSharedPreferences("Game",Context.MODE_PRIVATE);
        JSONObject jsonObject = getPreferencesJsonObject(pref,"unitData");
        return new FairyInfo(jsonObject.getString("unitName"),
                jsonObject.getInt("health"),
                jsonObject.getDouble("hunger"),
                jsonObject.getDouble("tired"),
                jsonObject.getInt("feelings"),
                jsonObject.getString("unitAction"),
                jsonObject.getDouble("previous"));
    }

    private static JSONObject getPreferencesJsonObject(SharedPreferences pref, String s) throws Exception {
        String jsonData = pref.getString(s,"");
        if("".equals(jsonData)) {
            jsonData = emptyUnitData();
        }
        //TODO 이거 jsonData 수정을 안해줘서 에러나는 거였음
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject;
    }

    private static String emptyUnitData() throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("unitNo","a000");
        jsonObject.put("unitName","Alpha");
        jsonObject.put("hunger", Meta.HUNGER_POINT_MAX);
        jsonObject.put("health",Meta.HEALTH_NOMAL);
        jsonObject.put("tired",Meta.TIRED_POINT_MAX);
        jsonObject.put("feelings",Meta.FEELINGS_NOMAL);
        jsonObject.put("unitAction","idle");
        jsonObject.put("previous",System.currentTimeMillis());
        save(jsonObject);
        return jsonObject.toString();
    }
}
