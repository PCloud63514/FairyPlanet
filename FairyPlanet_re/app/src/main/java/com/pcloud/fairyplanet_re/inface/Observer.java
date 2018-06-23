package com.pcloud.fairyplanet_re.inface;

import com.pcloud.fairyplanet_re.domain.UnitInfo;

/**
 * Created by PCloud on 2018-05-28.
 */
//TODO ObserverList
//Save&Load - 현재 정보
//UI - 현재 정보(액션)
//Sound - 현재 정보(액션)
//Debug - 현재 정보 옵저버들이 원하는 데이터가 일관되서 그냥 Unit 정보를 직렬화 시켜서 보내는게 좋을 듯
public interface Observer {
    void update();
    void stop();
}
