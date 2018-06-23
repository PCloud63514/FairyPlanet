package com.pcloud.fairyplanet_re.inface;

import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.pcloud.fairyplanet_re.core.view.GLView;
import com.pcloud.fairyplanet_re.domain.UnitInfo;
import com.pcloud.fairyplanet_re.inface.UnitAction;
import com.pcloud.fairyplanet_re.util.FPSManager;
import com.pcloud.fairyplanet_re.util.ResourceLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by PCloud on 2018-06-14.
 */
//일어나서 할 것.
//랜더러 기본 셋팅 하기
//UnitResource에 백터포인터 등의 셋팅 하기
//UnitInfo의 Action에 맞춰 화면 랜더링 하기(이미지 아이디 값에 여러장 보이는지 필수 확인)
//이러면 끝 나머지는 WindowManager의 캐릭터 위치를 변경하는 일만 남았다.


//UnitResource는 요정만 쓰려는게 아닌 추가적인 다른 오브젝트도 쓸 예정이였다.
//그렇기 때문에 UnitInfo를 멤버필드에 두는건 좋지않다.
public class UnitResource {
    private static final String TAG = "UnitResource";
    protected ResourceLoader resourceLoader;
    protected GLView glView;
    protected Handler handler;
    protected WindowManager windowManager;
    protected WindowManager.LayoutParams layoutParams;
    protected UnitInfo unitInfo;

    float vert[];
    byte index[];
    float texture[];
    FloatBuffer floatBuffer;
    FloatBuffer textureBuffer;
    ByteBuffer indexBuffer;
    FPSManager fpsManager; //나중에 static으로 만들어둬야함.
    int focus = 0;

    //Window Size And Pointer

    public UnitResource(UnitInfo unitInfo, GLView glView, Handler handler, WindowManager windowManager, WindowManager.LayoutParams layoutParams) {
        this.unitInfo = unitInfo;
        this.glView = glView;
        this.handler = handler;
        this.windowManager = windowManager;
        this.layoutParams = layoutParams;

        this.resourceLoader = new ResourceLoader();
        vert = new float[]{-1, 1, 0, -1, -1, 0, 1, -1, 0, 1, 1, 0};
        index = new byte[]{0, 3, 2, 0, 2, 1};
        texture = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f};
        floatBuffer = ArrayToBuffer(vert);
        indexBuffer = ByteBuffer.allocate(index.length).put(index);
        indexBuffer.position(0);
        textureBuffer = ArrayToBuffer(texture);
        fpsManager = new FPSManager();
    }

    private void init() throws Exception {
        loadResource();
        fpsManager.startFPS();
    }

    public void loadResource() throws Exception {
        Log.d(TAG, "loadResource");
    }

    public void draw(GL10 gl10) {

    }

    public FloatBuffer ArrayToBuffer(float[] f) {
        ByteBuffer buf = ByteBuffer.allocateDirect(f.length * 4);
        buf.order(ByteOrder.nativeOrder());
        FloatBuffer fbuf = buf.asFloatBuffer();
        fbuf.put(f);
        fbuf.position(0);
        return fbuf;
    }
}
