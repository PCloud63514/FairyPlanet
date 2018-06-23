package com.pcloud.fairyplanet_re.util.Observer;

import android.util.Log;

import com.pcloud.fairyplanet_re.domain.FairyInfo;
import com.pcloud.fairyplanet_re.domain.UnitInfo;
import com.pcloud.fairyplanet_re.inface.Observer;

/**
 * Created by PCloud on 2018-05-29.
 */

public class DebugMonitor implements Observer {
    private static final String TAG = "DebugMonitor";
    @Override
    public void update() {
        String str = new String(FairyInfo.getInstance() != null ? FairyInfo.getInstance().toString() : "FairyInfo Instance null");
        Log.d(TAG,"\nFairyInfo:" + str);
    }

    @Override
    public void stop() {
        Log.d(TAG,"Stop");
    }


}
