package com.pcloud.fairyplanet.views.game.gl20

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class BaseGLRenderer(var context: Context, var unitName:String):GLSurfaceView.Renderer {
    var baseGLShape: BaseGLShape? = null

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        baseGLShape?.destroy()

        var bitmap: Bitmap? = null
        context.resources.assets.open(unitName + "/data.png").let{
            bitmap = BitmapFactory.decodeStream(it)
            it.close()
        }
        baseGLShape = BaseGLShape(context, bitmap!!)
        GLES20.glClearColor(0f, 0f, 0f, 0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        baseGLShape?.draw()
    }
}