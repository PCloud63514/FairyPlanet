package com.pcloud.fairyplanet_re.inface;

/**
 * Created by PCloud on 2018-05-28.
 */
//TODO Pubilsher List
//UI
//Sound
//FPS
//Unit
public interface IPublisher {
    public void addObserver(Observer ob);
    public void deleteObserver(Observer ob);
    void notifyObserver();
}
