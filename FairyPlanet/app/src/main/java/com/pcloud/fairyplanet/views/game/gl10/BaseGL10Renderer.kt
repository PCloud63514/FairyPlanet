package com.pcloud.fairyplanet.views.game.gl10

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.opengl.GLU
import com.pcloud.fairyplanet.data.Frame
import com.pcloud.fairyplanet.data.SpriteSheetConfigInformation
import com.pcloud.fairyplanet.utils.global.isElapsed
import com.pcloud.fairyplanet.utils.global.onFPSInitialize
import com.pcloud.fairyplanet.utils.texturePacker.extractSpriteSheetConfigInformation
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class BaseGL10Renderer(var context: Context, var unitName: String): GLSurfaceView.Renderer {
    var mBaseGL10Shape:BaseGL10Shape? = null
    var mSpriteSheetConfigInformation: SpriteSheetConfigInformation? = null
    var frameIndex:Int = -1

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mBaseGL10Shape?.destroy()
        mSpriteSheetConfigInformation = extractSpriteSheetConfigInformation(context, unitName)

        context.resources.assets.open(unitName + "/data.png").let{
            var bitmap:Bitmap = BitmapFactory.decodeStream(it)
            it.close()
            mBaseGL10Shape = BaseGL10Shape(context)
            mBaseGL10Shape!!.initialize(gl!!, bitmap!!)
        }

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

        mBaseGL10Shape?.draw(gl!!)
        gl.apply {
            if(isElapsed())
                glTranslatef((400f * 1f)/2800.0f,(400 * 1) / 400f,0f)
        }
    }
}