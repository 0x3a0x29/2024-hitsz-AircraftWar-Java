package edu.hitsz.aircraft;

import edu.hitsz.aircraft.shoot.ShootStrategy;
import edu.hitsz.aircraft.shoot.StraightShoot;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractEnemy{
    public EliteEnemy(int locationX,int locationY,int speedX,int speedY,int hp,int power,int direction) {
        super(locationX,locationY,speedX,speedY,hp,power,direction);
        strategy = new StraightShoot();
    }
    public List<BaseBullet> shoot() {
        return strategy.doShoot(this);
    }

    @Override
    public void response() {
        vanish();
    }
}
