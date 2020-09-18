package com.pcloud.fairyplanet.views.game.gl20

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView

class BaseGLView(context: Context, unitName:String): GLSurfaceView(context) {
    val glRenderer: BaseGLRenderer
    init {
        setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
        holder.setFormat(PixelFormat.TRANSLUCENT)
        glRenderer = BaseGLRenderer(context, unitName)
        setRenderer(glRenderer)

        renderMode = RENDERMODE_CONTINUOUSLY
    }
}