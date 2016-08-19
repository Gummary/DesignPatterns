package com.hepeng.main;

/**
 * Created by admin on 2016/8/17.
 */
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}

