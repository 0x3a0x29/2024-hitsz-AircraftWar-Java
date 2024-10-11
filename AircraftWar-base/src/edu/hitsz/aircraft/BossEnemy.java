package edu.hitsz.aircraft;

import edu.hitsz.aircraft.shoot.RingShoot;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import java.util.List;

public class BossEnemy extends AbstractEnemy{
    public BossEnemy(int locationX,int locationY,int speedX,int speedY,int hp,int power,int direction) {
        super(locationX,locationY,speedX,speedY,hp,power,direction);
        strategy = new RingShoot();
    }
    public List<BaseBullet> shoot() {
        return strategy.doShoot(this);
    }

    @Override
    public void response() {
        return;
    }
}
