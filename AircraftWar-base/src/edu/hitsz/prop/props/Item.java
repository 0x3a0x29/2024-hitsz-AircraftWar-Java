package edu.hitsz.prop.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.basic.BasicObserver;
import edu.hitsz.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;

public abstract class Item extends AbstractFlyingObject {
    protected ArrayList observers = new ArrayList();
    public Item(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }
    public void attachObserver(BasicObserver observer){
        observers.add(observer);
    }
    public void detachObserver(BasicObserver observer){
        observers.remove(observer);
    }
    public abstract void itemActive(HeroAircraft heroAircraft);
}
