package com.pcloud.fairyplanet_re.core.view.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.WindowManager;

import com.pcloud.fairyplanet_re.core.view.GLView;
import com.pcloud.fairyplanet_re.inface.FairyResource;
import com.pcloud.fairyplanet_re.inface.UnitResource;
import com.pcloud.fairyplanet_re.domain.UnitInfo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by PCloud on 2018-02-28.
 */

public class GLRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "GLRenderer";
    private UnitResource unitResource;
    //타입을 받아서 요정인지 음식인지 구분할 예정.
    public GLRenderer(GLView glView, Handler handler, WindowManager windowManager, WindowManager.LayoutParams layoutParams) {
        unitResource = new FairyResource(glView,handler,windowManager,layoutParams);
        //TODO 버텍스
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.4f,0.4f,0.4f,0.4f);
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glEnable(GL10.GL_TEXTURE_2D);

        gl10.glDisable(GL10.GL_CULL_FACE);

        gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //TODO 이미지 로딩 및 바인딩
        try {
            unitResource.loadResource();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        gl10.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        gl10.glLoadIdentity();//해당 설정된 메트릭스를 초기화하는 것. 메트릭스를 단위행렬로 만드는 일을 한다.
        gl10.glCullFace(GL10.GL_FRONT);
        unitResource.draw(gl10);
        unitResource.move(gl10);
    }
}
