package edu.hitsz.prop.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.shoot.RingShoot;
import edu.hitsz.aircraft.shoot.SeparateShoot;
import edu.hitsz.aircraft.shoot.StraightShoot;

public class Bullet extends Item {
    private int bullet = 0;
    public Bullet(int locationX, int locationY, int speedX, int speedY, int bullet) {
        super(locationX, locationY, speedX, speedY);
        this.bullet = bullet;
    }
    public void itemActive(HeroAircraft heroAircraft){
        //System.out.println("FireSupply active!");
        Runnable r = () -> {
            heroAircraft.setStrategy(new SeparateShoot());
            try {
                Thread.sleep(bullet*1000);
            } catch (InterruptedException e) {
                return;
            }
            heroAircraft.setStrategy(new StraightShoot());
        };
        if (heroAircraft.heroStateThread != null) {heroAircraft.heroStateThread.interrupt();}
        heroAircraft.heroStateThread=new Thread(r);
        heroAircraft.heroStateThread.start();
    }
    public int getBullet() { return bullet; }
}
