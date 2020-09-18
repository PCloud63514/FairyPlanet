package com.pcloud.fairyplanet.views.game.ui.gl10

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import com.pcloud.fairyplanet.data.Frame
import com.pcloud.fairyplanet.data.Meta

import com.pcloud.fairyplanet.utils.global.isElapsed
import com.pcloud.fairyplanet.utils.global.onFPSInitialize
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GL10Renderer(var context: Context, var bitmap: Bitmap, var meta: Meta):GLSurfaceView.Renderer {
    var mGL10Shape:GL10Shape? = null
    var mFrameInfo:MutableList<Frame>? = null
    var mFrameCurrentIndex:Int = 0
    var oldX:Float = 0f
    var oldY:Float = 0f

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        mGL10Shape?.destroy()
        //크기랑 높이 보내줘야함.
        mGL10Shape = GL10Shape(context, meta)
        mGL10Shape!!.initialize(gl!!, bitmap!!)
        gl?.glClearColor(0f, 0f, 0f, 0f)
        onFPSInitialize()
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        gl.apply {
            glViewport(0, 0, width, height)
        }
    }

    override fun onDrawFrame(gl: GL10) {
        gl.apply {
            glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
            glMatrixMode(GL10.GL_TEXTURE)
        }

        mFrameInfo?.let {
            var x = (it.get(mFrameCurrentIndex).frame.x - oldX).toInt() / meta.size.w
            var y = (it.get(mFrameCurrentIndex).frame.y - oldY).toInt() / meta.size.h

            mGL10Shape?.draw(gl)
            gl.apply {
                if(isElapsed()) {
                    glTranslatef(x, y, 0f)
                    oldX = it.get(mFrameCurrentIndex).frame.x
                    oldY = it.get(mFrameCurrentIndex).frame.y
                    mFrameCurrentIndex = if (mFrameCurrentIndex < it.size - 1) mFrameCurrentIndex.plus(1) else 0
                }
            }
        }
    }
}