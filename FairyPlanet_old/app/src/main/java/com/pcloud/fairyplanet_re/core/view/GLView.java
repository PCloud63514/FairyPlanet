package com.pcloud.fairyplanet_re.core.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.pcloud.fairyplanet_re.core.view.renderer.GLRenderer;
import com.pcloud.fairyplanet_re.domain.UnitInfo;

/**
 * Created by PCloud on 2018-06-09.
 */
public class GLView extends GLSurfaceView {
    private final GLRenderer glRenderer;
    public GLView(Context context, Handler handler, WindowManager windowManager, WindowManager.LayoutParams layoutParams) {
        super(context);
        //TODO 렌더러 추가
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        glRenderer = new GLRenderer(this, handler, windowManager, layoutParams);
        setRenderer(glRenderer);

        setRenderMode(RENDERMODE_CONTINUOUSLY);
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
