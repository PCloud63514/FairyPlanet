package com.pcloud.fairyplanet_re;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pcloud.fairyplanet_re.inface.UnitImpl;
import com.pcloud.fairyplanet_re.core.view.GLView;
import com.pcloud.fairyplanet_re.service.GameService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private UnitImpl unit;
    //Graphic
    private GLView glView;
    TextView tv_save;
    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_save = (TextView)findViewById(R.id.tv_save);
        btn_save = (Button)findViewById(R.id.btn_save);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                startService(new Intent(getApplicationContext(),GameService.class));
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            stopService(new Intent(getApplicationContext(),GameService.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleTitle("Permission required")
                .setRationaleMessage("RationaleMessage")
                .setDeniedTitle("Permission denied")
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("bla bla")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SYSTEM_ALERT_WINDOW)
                .check();
    }

    //TODO 시작
    void init() {

    }
    //TODO 기존 정보 존재 체크
    boolean isSaved() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(),GameService.class));
    }
}
