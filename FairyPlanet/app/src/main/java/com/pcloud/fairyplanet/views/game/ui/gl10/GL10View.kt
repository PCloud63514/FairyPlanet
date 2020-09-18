package com.pcloud.fairyplanet.views.game.ui.gl10

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import com.pcloud.fairyplanet.data.Frame
import com.pcloud.fairyplanet.data.SpriteSheetConfigInformation
import com.pcloud.fairyplanet.utils.texturePacker.extractSpriteSheetConfigInformation

open class GL10View(context: Context, domain:String):GLSurfaceView(context) {
    val mSpriteSheetConfigInformation: SpriteSheetConfigInformation
    val mGL10Renderer:GL10Renderer

    init {
        mSpriteSheetConfigInformation = extractSpriteSheetConfigInformation(context, domain)!!
        context.resources.assets.open(domain + "/data.png").let {
            var bitmap: Bitmap = BitmapFactory.decodeStream(it)
            it.close()
            holder.setFormat(PixelFormat.TRANSLUCENT)
            mGL10Renderer = GL10Renderer(context, bitmap, mSpriteSheetConfigInformation.meta)
            setRenderer(mGL10Renderer)
            renderMode = RENDERMODE_CONTINUOUSLY
            isFocusableInTouchMode = true
        }
    }
}