package com.hepeng.main;

/**
 * Created by admin on 2016/8/18.
 */
public abstract class Beverage {
    public enum Size {TALL, GRANDE, VENTI};
    Size size  = Size.TALL;

    public void setSize(Size size) {
        this.size = size;
    }

    public Size getSize() {

        return size;
    }

    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
