package com.pcloud.fairyplanet.views.game.ui.gl10

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLUtils
import com.pcloud.fairyplanet.data.Meta
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10

class GL10Shape(context: Context, meta: Meta) {

    val mVertexCoords:FloatArray = floatArrayOf(
    -1.0f, 1.0f, 0.0f,
    -1.0f, -1.0f, 0.0f,
    1.0f, -1.0f, 0.0f,
    1.0f, 1.0f, 0.0f
    )

    val mDrawOrder:ShortArray = shortArrayOf(
        0, 1, 2, 0, 2, 3
    )
    val mTexture:FloatArray = floatArrayOf(
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 0.0f
    )

    val mLookTexture:FloatArray

    var mLookTextureBuffer: FloatBuffer

    var mVertexBuffer: FloatBuffer
    var mTextureBuffer: FloatBuffer
    var mDrawOrderBuffer: ShortBuffer
    var mBitmapHandle:Int = -1
    init {
        var textureW = meta.sourceSize.w / meta.size.w
        var textureH = meta.sourceSize.h / meta.size.h
        mLookTexture = floatArrayOf(
            0.0f, 0.0f,
            0.0f, textureH,
            textureW, textureH,
            textureW, 0.0f
        )

        mVertexBuffer = arrayToBuffer(mVertexCoords)
        mTextureBuffer = arrayToBuffer(mTexture)
        mDrawOrderBuffer = arrayToBuffer(mDrawOrder)
        mLookTextureBuffer = arrayToBuffer(mLookTexture)
    }

    fun initialize(gl: GL10, bitmap: Bitmap) {
        mBitmapHandle = initTexture(gl, bitmap)
        bitmap.recycle()
    }

    fun draw(gl: GL10) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        gl.glEnable(GL10.GL_DEPTH_TEST)
        gl.glEnable(GL10.GL_COLOR_BUFFER_BIT)
        gl.glEnable(GL10.GL_TEXTURE_2D)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer)

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mLookTextureBuffer)

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mBitmapHandle)
        gl.glDrawElements(
            GL10.GL_TRIANGLES,
            mDrawOrder.size,
            GL10.GL_UNSIGNED_SHORT,
            mDrawOrderBuffer
        )
        gl.glDisable(GL10.GL_DEPTH_TEST)
        gl.glDisable(GL10.GL_COLOR_BUFFER_BIT)
        gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)

    }

    fun initTexture(gl: GL10, bitmap: Bitmap):Int {
        var name = IntArray(1)
        gl.glGenTextures(1, name, 0)
        gl.glActiveTexture(GL10.GL_TEXTURE0)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, name.get(0))
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST)
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST)
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT)
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT)
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0)
        return name.get(0)
    }

    fun destroy() {
        TODO("Not yet implemented")
    }
    fun arrayToBuffer(f: FloatArray): FloatBuffer {
        var buf: ByteBuffer = ByteBuffer.allocateDirect(f.size * 4)
        buf.order(ByteOrder.nativeOrder())
        var fbuf: FloatBuffer = buf.asFloatBuffer()
        fbuf.put(f)
        fbuf.position(0)

        return fbuf
    }

    fun arrayToBuffer(s: ShortArray): ShortBuffer {
        var buf: ByteBuffer = ByteBuffer.allocateDirect(s.size * 4)
        buf.order(ByteOrder.nativeOrder())
        var sbuf: ShortBuffer = buf.asShortBuffer()
        sbuf.put(s)
        sbuf.position(0)

        return sbuf
    }
}