package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.BasicObserver;

public abstract class AbstractEnemy extends AbstractAircraft implements BasicObserver {
    public AbstractEnemy(int locationX, int locationY, int speedX,
                         int speedY, int hp,int power,int direction){
        super(locationX, locationY, speedX, speedY, hp, power, direction);
    }
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
