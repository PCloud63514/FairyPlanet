package com.pcloud.fairyplanet_re.inface;

import com.pcloud.fairyplanet_re.domain.UnitInfo;

import java.util.ArrayList;

/**
 * Created by PCloud on 2018-05-29.
 */
    //Unit은 정해진 정보가 있다.
    //Unit은 정해진 정보에 맞춰 행동한다.
    //Unit의 정해진 정보는 기본 정보(허기,피로도 등) 및 유닛의 위치와 시간 등이 있다.
    //TODO 일단 UI는 없음 & 로그로만 상태확인할 예정

//unit 클래스 멤버필드에 UnitInfo를 두고 갱신을 시킬 것인가 or 업데이트 때 UnitInfo 레퍼런스를 만들어서 넘겨줄 것인가.
//생각해보니 Unit은 고유 정보를 포함하고 있어야한다. 멤버필드에 두자
//주기적(짧은 시간 대개 5초 및 1분 and 긴 시간 1시간 및 5시간)으로 업데이트 처리가 되어야함 세이브 로드 및 UnitInfo 갱신 등을 해야함.

//TODO Unit을 터치 시 하이라이트 및 Unit 상단에 메뉴 등을 띄우고 싶다.
//TODO 하이라이트 상태에서 터치 시 다른 이벤트(놀아주기 및 롱 터치 시 집기 집기 상태에서는 드래그로 이동 가능 등을 원함)
//TODO 추려진 액션을 실행 하던 중 외부의 작업(Touch 등)에 의한 액션은 어떻게 해야하는가?
public interface Unit {
    //TODO 정보 초기화
    void init();
    //TODO 시작
    void start();
    void loop() throws Exception;
    void stop();
//A1[정보가 있을 시] - 정보에 맞춰 UnitInfo 를 구성
//B1[정보가 없을 시] - res에서 시트에 맞춰 UnitInfo 구성 후 정보를 만듬.(Save)
//Key - UnitInfo 정보 확인
//UnitInfo 정보에 맞춰 행동 가능한 액션을 추림
//TODO 행동 가능한 액션의 판단 기준은 무엇인가?
//추려진 액션들의 실행 확률을 계산 후 확률에 맞춰 UnitInfo의 Action을 변경
//UnitInfo 정보에 맞춰 observer 갱신.
//Key 로 돌아감.

}

