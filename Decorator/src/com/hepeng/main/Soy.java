package com.hepeng.main;

/**
 * Created by admin on 2016/8/18.
 */
public class Soy extends CondimentDecorator {
    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }

    public double cost() {

        double costs = beverage.cost();
        if(beverage.getSize() == Size.GRANDE){
            costs+=.15;
        }else if(beverage.getSize()==Size.TALL) {
            costs += .20;
        }else if(beverage.getSize() == Size.VENTI){
            costs += .10;
        }

        return costs;
    }
}
