package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.BasicObserver;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemy{
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power,int direction) {
        super(locationX, locationY, speedX, speedY, hp,power,direction);
    }
    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
    @Override
    public void response() {
        vanish();
    }
}
