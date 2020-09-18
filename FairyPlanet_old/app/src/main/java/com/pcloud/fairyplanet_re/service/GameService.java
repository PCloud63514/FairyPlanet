package com.pcloud.fairyplanet_re.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.pcloud.fairyplanet_re.Meta;
import com.pcloud.fairyplanet_re.core.MenuView;
import com.pcloud.fairyplanet_re.inface.UnitImpl;
import com.pcloud.fairyplanet_re.core.view.GLView;
import com.pcloud.fairyplanet_re.util.MyApplication;
import com.pcloud.fairyplanet_re.util.Observer.DebugMonitor;

/**
 * Created by PCloud on 2018-06-09.
 */
//GLVIew 호출된 상태. UnitImpl이 명령을 내려 모습을 바꿈 그이 마리오네트 ㅇㅈ? ㅇ ㅇㅈ

//순서도를 만들장!
//앱에서 이 유닛을 서비스 시켜라 라고 메시지를 날리고
//앱은 프로그래스바 화면을 보인다.
//서비스는 그때 동안 UnitImpl,GLView,UnitResource 등을 준비시킨다.
//전부 세이브 데이터에 정보가 저장되어 있어서 인텐트로 주고 받을 필요가 없다.
//UnitImpl이 준비되고 UnitUI는 비트맵 정보를 불러오고 GLView는 WindowManager에 추가된다.
//유닛이 스타트되어 명령을 보내주면 되는 일.
public class GameService extends Service {
    private static final String TAG = "GameService";
    private UnitImpl unit;
    //Graphic
    private GLView glView;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private DisplayMetrics displayMetrics;
    private Handler handler;

    public GameService() {
        handler = new Handler();
    }

    private void graphicInit() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        displayMetrics = getApplicationContext().getResources().getDisplayMetrics();



        int objectSize = displayMetrics.widthPixels / Meta.SIZE_NOMAL;
        layoutParams = new WindowManager.LayoutParams(objectSize,
                objectSize,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams.x = 0;
        layoutParams.y = 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        unit = new UnitImpl();
        unit.addObserver(new DebugMonitor());
        unit.addObserver(new MenuView());
        //이번엔 GL객체를 생성한다.
        try {
            graphicInit();
            glView = new GLView(this,handler,windowManager, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "GL Object Load Error");
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("hide");
        intentFilter.addAction("show");
        MyApplication.getAppContext().registerReceiver(eventReceiver,intentFilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (unit.isUnitRun() == false) {
            unit.start();
            windowManager.addView(glView, layoutParams);
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        MyApplication.getAppContext().unregisterReceiver(eventReceiver);
        unit.stop();
        windowManager.removeView(glView);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "hide":glView.setVisibility(View.INVISIBLE);
                break;
                case "show":glView.setVisibility(View.VISIBLE);
                break;
                default:break;
            }
        }
    };
}
