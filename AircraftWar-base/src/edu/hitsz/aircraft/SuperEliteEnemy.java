package edu.hitsz.aircraft;

import edu.hitsz.aircraft.shoot.SeparateShoot;
import edu.hitsz.aircraft.shoot.ShootStrategy;
import edu.hitsz.aircraft.shoot.StraightShoot;
import edu.hitsz.application.Main;
import edu.hitsz.basic.BasicObserver;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class SuperEliteEnemy extends AbstractEnemy{
    public SuperEliteEnemy(int locationX,int locationY,int speedX,int speedY,int hp,int power,int direction) {
        super(locationX,locationY,speedX,speedY,hp,power,direction);
        strategy = new SeparateShoot();
    }
    public List<BaseBullet> shoot() {
        return strategy.doShoot(this);
    }

    @Override
    public void response() {
        int d= Math.min(this.getHp(), getMaxHp() / 2);
        decreaseHp(d);
    }
}
