package com.pcloud.fairyplanet.views.game.gl20

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class BaseGLShape(var mContext: Context,var mBitmap: Bitmap) {
    //region Code
    val vertexShaderCode:String =
        "attribute vec4 a_position;" +
                "attribute vec2 a_texture_position;" +
                "varying vec2 v_texture_position;" +
                "uniform mat4 u_matrix;" +
                "uniform mat u_texture_matrix;" +
                "void main() {" +
                "v_texture_position = (u_texture_matrix * vec4(a_texture_position, 0.0, 1.0)).xy;" +
                "gl_Position = u_matrix * a_position;" +
                "}"
    val fragmentShaderCode:String =
        "precision mediump float;" +
                "varying vec2 v_texture_position;" +
                "uniform sampler2D u_texture_unit;" +
                "void main() {" +
                "gl_FragColor = texture2D(u_texture_unit, v_texture_position);" +
                "}"

    val mVertexShaderCode:String =
                "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute vec2 a_texCoord;" +
                "varying vec2 v_texCoord;" +
                "void main() {" +
                "gl_Position = uMVPMatrix * vPosition;" +
                "v_texCoord = a_texCoord;" +
                "}"
    val mFragmentShaderCode:String =
               "precision mediump float;" +
                "varying vec2 v_texCoord;" +
                "uniform sampler2D s_texture;" +
                "void main() {" +
                "gl_FragColor = texture2D(s_texture, v_texCoord);" +
                "}"

    //endregion
    //1.0
    //region Field
    val mVertexCoords:FloatArray = floatArrayOf(
        -1.0f,  1.0f, 0.0f,
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f,
        1.0f,  1.0f, 0.0f)
    val mDrawOrder:ShortArray = shortArrayOf(
        0, 1, 2, 0, 2, 3)
    val mUvs:FloatArray = floatArrayOf(
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 0.0f
    )

    var mVertexBuffer:FloatBuffer
    var mDrawListBuffer:ShortBuffer
    var mUvBuffer:FloatBuffer

    val mViewMatrix:FloatArray = FloatArray(16)
    var mBitmapHandle:Int = -1
    var mPositionHandle:Int = -1
    val mProgram:Int

    init {
        mVertexBuffer = arrayToBuffer(mVertexCoords)
        mDrawListBuffer = arrayToBuffer(mDrawOrder)
        mUvBuffer = arrayToBuffer(mUvs)

        Matrix.setIdentityM(mViewMatrix, 0)

        mProgram = createProgram()
        mBitmapHandle = initTexture(mBitmap)
        mBitmap.recycle()
    }
//https://qastack.kr/gamedev/37510/sprite-animation-in-opengl
    //endregion

    //region testField
    var texCoords:FloatArray = floatArrayOf(
        0.0f, 0.0f,
        1.0f, 0.0f,
        0.5f, 1.0f
    )
    //endregion

    fun draw() {
        GLES20.glUseProgram(mProgram)
//        Matrix.setIdentityM(mViewMatrix, 0)
//        Matrix.translateM(mViewMatrix, 0, 0f, )
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer)

        var texCoordLoc = GLES20.glGetAttribLocation(mProgram, "a_texCoord")
        GLES20.glEnableVertexAttribArray(texCoordLoc)
        GLES20.glVertexAttribPointer(texCoordLoc, 2, GLES20.GL_FLOAT, false, 0, mUvBuffer)

        var matrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, mViewMatrix, 0)

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_DST_COLOR, GLES20.GL_ZERO)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mBitmapHandle)



        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mDrawOrder.size, GLES20.GL_UNSIGNED_SHORT, mDrawListBuffer)
        GLES20.glDisable(GLES20.GL_BLEND)
        GLES20.glDisableVertexAttribArray(mPositionHandle)
        GLES20.glDisableVertexAttribArray(texCoordLoc)
    }

    fun initTexture(bitmap:Bitmap):Int {
        var name:IntArray = IntArray(1)
        GLES20.glGenTextures(1, name, 0)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, name.get(0))
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        return name.get(0)
    }

    fun createProgram(): Int {
        var program = GLES20.glCreateProgram()
        var vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, mVertexShaderCode)
        var fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, mFragmentShaderCode)

        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)
        GLES20.glLinkProgram(program)

        return program
    }

    fun loadShader(type: Int, shaderCode: String): Int {
        var shader:Int = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }

    fun arrayToBuffer(f:FloatArray): FloatBuffer {
        var buf: ByteBuffer = ByteBuffer.allocateDirect(f.size * 4)
        buf.order(ByteOrder.nativeOrder())
        var fbuf: FloatBuffer = buf.asFloatBuffer()
        fbuf.put(f)
        fbuf.position(0)

        return fbuf
    }

    fun arrayToBuffer(s:ShortArray):ShortBuffer {
        var buf: ByteBuffer = ByteBuffer.allocateDirect(s.size * 4)
        buf.order(ByteOrder.nativeOrder())
        var sbuf: ShortBuffer = buf.asShortBuffer()
        sbuf.put(s)
        sbuf.position(0)

        return sbuf
    }

    fun destroy() {

    }

}