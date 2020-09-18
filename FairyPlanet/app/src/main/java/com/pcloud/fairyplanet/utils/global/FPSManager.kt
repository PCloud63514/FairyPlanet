package com.pcloud.fairyplanet.utils.global

private var fpsStartTime:Long = 0
private var timeElapsed:Double = 0.0
private val thetaFrame:Float = 0.3f

fun onFPSInitialize() {
    fpsStartTime = System.currentTimeMillis()
}

fun isElapsed():Boolean {
    val fpsEndTime:Long = System.currentTimeMillis()
    val timeDelta:Float = (fpsEndTime - fpsStartTime) * 0.001f

    timeElapsed += timeDelta

    if(timeElapsed >= thetaFrame) {
        timeElapsed = 0.0
        fpsStartTime = System.currentTimeMillis()
        return true
    }

    return false
}