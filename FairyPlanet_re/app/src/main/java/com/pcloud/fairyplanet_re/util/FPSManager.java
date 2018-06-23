package com.pcloud.fairyplanet_re.util;

import android.util.Log;

/**
 * Created by PCloud on 2018-05-29.
 */

public class FPSManager {
    long fpsStartTime = 0L;;
    double timeElapsed = 0.0;
    int frame;

    public void startFPS() {
        fpsStartTime = System.currentTimeMillis();
    }
    public boolean isElapsed(float aFrame) {
        long fpsEndTime = System.currentTimeMillis();
        float timeDelta = (fpsEndTime - fpsStartTime) * 0.001f;
        frame++;
        timeElapsed += timeDelta;

        if (timeElapsed >= aFrame) {
            frame = 0;
            timeElapsed = 0.0;
            fpsStartTime = System.currentTimeMillis();
            return true;
        }
        fpsStartTime = System.currentTimeMillis();
        return false;
    }

    public void logFPS() {
        long fpsEndTime = System.currentTimeMillis();
        float timeDelta = (fpsEndTime - fpsStartTime) * 0.001f;
        frame++;
        timeElapsed += timeDelta;

        if (timeElapsed >= 1.0f) {
            float fps = (float) (frame / timeElapsed);
            Log.d("fps", "fps:" + fps);
            frame = 0;
            timeElapsed = 0.0;
        }
        fpsStartTime = System.currentTimeMillis();
    }
}
