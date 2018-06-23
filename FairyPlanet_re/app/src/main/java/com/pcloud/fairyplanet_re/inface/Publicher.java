package com.pcloud.fairyplanet_re.inface;

import com.pcloud.fairyplanet_re.core.MenuView;
import com.pcloud.fairyplanet_re.util.Observer.DebugMonitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PCloud on 2018-06-16.
 */

public class Publicher implements IPublisher {
    protected List<Observer> observerList;

    public Publicher() {
        this.observerList = new ArrayList<Observer>();
    }

    @Override
    public void addObserver(Observer ob) {
        this.observerList.add(ob);
    }


    //TODO 앵간치해선 안쓰일 것 같다.
    @Override
    public void deleteObserver(Observer ob) {
        int i = observerList.indexOf(ob);
        this.observerList.remove(i);
        ob.stop();
    }

    @Override
    public void notifyObserver() {
        //TODO
    }
}
