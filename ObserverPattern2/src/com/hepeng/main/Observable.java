package com.hepeng.main;

import java.util.ArrayList;

/**
 * Created by admin on 2016/8/17.
 */
public class Observable {
    private boolean isChanged;
    private ArrayList observers;

    public Observable() {
        isChanged = false;
        observers = new ArrayList();
    }

    public void setChanged(){
        isChanged = true;
    }

    public void clearChanged(){
        isChanged = false;
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void delObserver(Observer o){
        int i = observers.indexOf(o);
        if(i > 0){
            observers.remove(i);
        }
    }

    public void notifyObservers(Object o,Object arg) {
        if(isChanged){
            for(int i = 0;i<observers.size();i++){
                Observer observer = (Observer) observers.get(i);
                observer.update(o,arg);
            }
        }
    }

    public void notifyObservers(Object o){
        notifyObservers(o,null);
    }
}
