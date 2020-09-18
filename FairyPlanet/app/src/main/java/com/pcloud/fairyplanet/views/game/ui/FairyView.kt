package com.pcloud.fairyplanet.views.game.ui

import android.content.Context
import android.os.Handler
import com.pcloud.fairyplanet.data.Frame
import com.pcloud.fairyplanet.utils.global.ActionEnumClass
import com.pcloud.fairyplanet.views.game.ui.gl10.GL10View
import java.util.*
import kotlin.concurrent.timer

class FairyView(context: Context, domain:String): GL10View(context, domain) {
    val mHandler: Handler
    val mRandom: Random = Random()
    init {
        onIdle()
        mHandler = Handler {
            when(it.what) {
                ActionEnumClass.Idle.actionIndex -> onIdle()
                ActionEnumClass.Move.actionIndex -> onMove()
                ActionEnumClass.Attack.actionIndex -> onAttack()
                ActionEnumClass.Hurt.actionIndex -> onHurt()
                ActionEnumClass.Jump.actionIndex -> onJump()
                ActionEnumClass.Death.actionIndex -> onDeath()
                ActionEnumClass.Death.actionIndex -> onDeath()
            }
            true
        }

        timer(period=5000) {
            val num = mRandom.nextInt(6)
            mHandler.obtainMessage(num).sendToTarget()
        }
    }

    fun onIdle() {
        mSpriteSheetConfigInformation.frames.get("Idle")?.let { setAction(it) }
    }

    fun onMove() {
        mSpriteSheetConfigInformation.frames.get("Move")?.let { setAction(it) }
    }

    fun onAttack() {
        mSpriteSheetConfigInformation.frames.get("Attack")?.let { setAction(it) }
    }

    fun onHurt() {
        mSpriteSheetConfigInformation.frames.get("Hurt")?.let { setAction(it) }
    }

    fun onJump() {
        mSpriteSheetConfigInformation.frames.get("Jump")?.let { setAction(it) }
    }

    fun onDeath() {
        mSpriteSheetConfigInformation.frames.get("Death")?.let { setAction(it) }
    }

    fun setAction(frame:MutableList<Frame>) {
        mGL10Renderer.let {
            it.mFrameCurrentIndex = 0
            it.mFrameInfo = frame
        }
    }
}