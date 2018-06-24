package com.pcloud.fairyplanet_re.inface;

import android.os.Handler;
import android.util.Log;

import com.pcloud.fairyplanet_re.Meta;
import com.pcloud.fairyplanet_re.R;
import com.pcloud.fairyplanet_re.domain.FairyInfo;
import com.pcloud.fairyplanet_re.domain.UnitInfo;
import com.pcloud.fairyplanet_re.inface.Observer;
import com.pcloud.fairyplanet_re.inface.Publicher;
import com.pcloud.fairyplanet_re.inface.Unit;
import com.pcloud.fairyplanet_re.inface.UnitAction;
import com.pcloud.fairyplanet_re.util.MyApplication;
import com.pcloud.fairyplanet_re.util.SaveManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by PCloud on 2018-05-29.
 */
//unit 클래스 멤버필드에 UnitInfo를 두고 갱신을 시킬 것인가 or 업데이트 때 UnitInfo 레퍼런스를 만들어서 넘겨줄 것인가.
//생각해보니 Unit은 고유 정보를 포함하고 있어야한다. 멤버필드에 두자
//주기적(짧은 시간 대개 5초 및 1분 and 긴 시간 1시간 및 5시간)으로 업데이트 처리가 되어야함 세이브 로드 및 UnitInfo 갱신 등을 해야함.
public class UnitImpl extends Publicher implements Unit {
    private static final String TAG = "UnitImpl";
    private boolean isRun;
    private Handler handler;
    private Runnable runnable;
    //System
    private Random random;
    private List<UnitAction> unitActionList;

    //시작(observer를 받아옴)
    //정보 불러오기
    //A1[정보가 있을 시] - 정보에 맞춰 UnitInfo 를 구성
    //B1[정보가 없을 시] - res에서 시트에 맞춰 UnitInfo 구성 후 정보를 만듬.(Save)
    //Key - UnitInfo 정보 확인
    //UnitInfo 정보에 맞춰 observer 갱신.
    //UnitInfo 정보에 맞춰 행동 가능한 액션을 추림
    //TODO 행동 가능한 액션의 판단 기준은 무엇인가?
    //추려진 액션들의 실행 확률을 계산 후 확률에 맞춰 액션 실행.
    //Key 로 돌아감.
    //TODO 추려진 액션을 실행 하던 중 외부의 작업(Touch 등)에 의한 액션은 어떻게 해야하는가?
    //TODO Unit을 터치 시 하이라이트 및 Unit 상단에 메뉴 등을 띄우고 싶다.
    //TODO 하이라이트 상태에서 터치 시 다른 이벤트(놀아주기 및 롱 터치 시 집기 집기 상태에서는 드래그로 이동 가능 등을 원함)
    public UnitImpl() {
        init();
    }

    @Override
    public void init() {
        //TODO 초기화
        this.isRun = false;
        random = new Random(System.currentTimeMillis());
        unitActionList = new ArrayList<UnitAction>();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (isRun) {
                        loop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


    }

    @Override
    public void start() {
        //TODO 행동 시작.
        Log.d(TAG, "Unit_Start");
        this.isRun = true;
        handler.post(runnable);

    }

    //TODO 나중에는 날씨정보를 받아와서 기분과 피로도 소모에 영향을 줄 예정.
    //TODO 싫어하는 음식을 먹였을 시의 경우도 추가할 것 단 이것은 User Input이므로 따로 메서드를 만들 것.
    //TODO 좌측을 볼지 우측을 볼 것인지 정함.
    //
    private void propertyUpdate() throws Exception {
        //TODO 현재시간 체크
        double currentTime = System.currentTimeMillis();
        //TODO 현재시간 - 마지막 시간 = elapsed
        double previous = FairyInfo.getInstance().getPrevious();
        double elapsed = currentTime - previous;

        int count = (int) (elapsed / 60000);//1min
        if (count <= 0) {
            return;
        } else {
            //TODO  T/1분으로 나온 값 만큼 속성 값을 * 변경한다.
            double hungry = FairyInfo.getInstance().getHunger() - (count * Meta.HUNGER_POINT) > 0 ? FairyInfo.getInstance().getHunger() - (count * Meta.HUNGER_POINT) : 0;
            double tired = FairyInfo.getInstance().getTired() - (count * Meta.TIRED_POINT) > 0 ? FairyInfo.getInstance().getTired() - (count * Meta.TIRED_POINT) : 0;

            FairyInfo.getInstance().setHunger(hungry);
            FairyInfo.getInstance().setTired(tired);

            //TODO 그 뒤 마지막 시간을 현재시간으로 변경.
            FairyInfo.getInstance().setPrevious(currentTime);

            //TODO 세이브
            SaveManager.save();
        }
    }

    private void checkAction() {
<<<<<<< HEAD

=======
>>>>>>> 4d260320174275cfe21996d3b807158c21fd9e54
        unitActionList.add(UnitAction.idle);
        unitActionList.add(UnitAction.move);

        if (FairyInfo.getInstance().getHunger() < 60.0d) {
            unitActionList.add(UnitAction.hungry);
        }
        if (FairyInfo.getInstance().getTired() < 60.0d) {
            unitActionList.add(UnitAction.tired);
        }
        //-1 아픔 0 정상
        if (FairyInfo.getInstance().getHealth() == -1) {
            unitActionList.add(UnitAction.seek);
        }
        //-1기분 나쁨 / 0정상 / 1기분 좋음
        switch (FairyInfo.getInstance().getFeelings()) {
            case -1:
                unitActionList.add(UnitAction.dislike);
                break;
            case 1:
                unitActionList.add(UnitAction.like);
                break;
        }

        float f = 1f / unitActionList.size();
        float target = random.nextFloat();

        for (int i = 0; i < unitActionList.size(); i++) {
            if (target <= f * (i + 1)) {
<<<<<<< HEAD
                FairyInfo.getInstance().setVerticalFocus();
=======
>>>>>>> 4d260320174275cfe21996d3b807158c21fd9e54
                FairyInfo.getInstance().setUnitAction(unitActionList.get(i));
                break;
            }
        }
<<<<<<< HEAD

=======
>>>>>>> 4d260320174275cfe21996d3b807158c21fd9e54
        unitActionList.clear();
    }

    @Override
    public void loop() throws Exception {
        //TODO 속성 변경
        propertyUpdate();
        //TODO 할 수 있는 액션 찾기 & 액션 변경
        checkAction();
        //TODO 옵저버에게 업데이트 알림.
        notifyObserver();
        //TODO 반복
<<<<<<< HEAD
        //TODO 1~5초 사이 랜덤
        handler.postDelayed(runnable, (random.nextInt(4) + 1) * 1000);
=======
        //TODO 1~8초 사이 랜덤
        handler.postDelayed(runnable, (random.nextInt(6) + 2) * 1000);
>>>>>>> 4d260320174275cfe21996d3b807158c21fd9e54
    }

    //TODO 종료했을 때 옵저버들에게 종료됨을 알려야한다.
    @Override
    public void stop() {
        this.isRun = false;
        Iterator<Observer> iter = observerList.iterator();

        while (iter.hasNext()) {
            Observer ob = iter.next();
            ob.stop();
            iter.remove();
        }
        Log.d(TAG, "Unit_Stop");
    }

    @Override
    public void notifyObserver() {
        super.notifyObserver();
        for (Observer ob : observerList) {
            ob.update();
        }
    }

    public boolean isUnitRun() {
        return this.isRun;
    }

}
