package com.pcloud.fairyplanet_re.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.pcloud.fairyplanet_re.R;
import com.pcloud.fairyplanet_re.domain.FairyInfo;
import com.pcloud.fairyplanet_re.inface.Observer;
import com.pcloud.fairyplanet_re.util.MyApplication;

/**
 * Created by PCloud on 2018-06-16.
 */

public class MenuView implements Observer {
    private static final int NOTIFICATION_ID = 0x0001;
    private NotificationManager notificationManager;
    private Notification noti;
    private RemoteViews remoteViews;

    public MenuView() {
        notificationManager = (NotificationManager) MyApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(MyApplication.getAppContext());
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("Type");
        noti = builder.build();

        remoteViews = new RemoteViews(MyApplication.getAppContext().getPackageName(), R.layout.noti_menu);
        noti.contentView = remoteViews;
        noti.flags = Notification.FLAG_NO_CLEAR;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("food");
        intentFilter.addAction("hide");
        intentFilter.addAction("show");
        intentFilter.addAction("empty_2");

        MyApplication.getAppContext().registerReceiver(eventReceiver,intentFilter);

        setOnClickBtn(R.id.btn_food, "food");
        setOnClickBtn(R.id.btn_hide, "hide");
        setOnClickBtn(R.id.btn_show, "show");
        setOnClickBtn(R.id.btn_empty_2, "empty_2");


    }

    private void setOnClickBtn(int id, String action) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(id, pendingIntent);
    }

    @Override
    public void update() {
        remoteViews.setTextViewText(R.id.tv_hungry, String.valueOf("Hunger:" + String.format("%.1f",FairyInfo.getInstance().getHunger())));
        remoteViews.setTextViewText(R.id.tv_tired, String.valueOf("Tired:" +String.format("%.1f",FairyInfo.getInstance().getTired())));
        notificationManager.notify(NOTIFICATION_ID, noti);
    }

    @Override
    public void stop() {
        MyApplication.getAppContext().unregisterReceiver(eventReceiver);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "food":
                    double hunger = FairyInfo.getInstance().getHunger() + 20;
                    FairyInfo.getInstance().setHunger(hunger);
                    update();
                    break;
                case "hide": remoteViews.setViewVisibility(R.id.btn_hide, View.GONE);
                             remoteViews.setViewVisibility(R.id.btn_show, View.VISIBLE);
                             notificationManager.notify(NOTIFICATION_ID, noti);
                    break;
                case "show":remoteViews.setViewVisibility(R.id.btn_hide, View.VISIBLE);
                            remoteViews.setViewVisibility(R.id.btn_show, View.GONE);
                            notificationManager.notify(NOTIFICATION_ID, noti);
                    break;
                case "empty_2":
                    Log.d("EventReceiver", "Empty_2");
                    break;
                default:
                    Log.d("EventReceiver", "Switch_Default  Action:" + action);
            }
        }
    };
}
