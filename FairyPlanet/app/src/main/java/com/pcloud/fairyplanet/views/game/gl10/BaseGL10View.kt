package com.pcloud.fairyplanet.views.game.gl10

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView

open class BaseGL10View(context: Context, unitName:String): GLSurfaceView(context) {
    val mBaseGL10Renderer:BaseGL10Renderer
    init {
        holder.setFormat(PixelFormat.TRANSLUCENT)
        mBaseGL10Renderer = BaseGL10Renderer(context, unitName)
        setRenderer(mBaseGL10Renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
        isFocusableInTouchMode = true
    }
}