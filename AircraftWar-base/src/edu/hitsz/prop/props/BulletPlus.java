package edu.hitsz.prop.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.shoot.RingShoot;
import edu.hitsz.aircraft.shoot.SeparateShoot;
import edu.hitsz.aircraft.shoot.StraightShoot;

public class BulletPlus extends Item {
    private int bulletPlus = 0;
    public BulletPlus(int locationX, int locationY, int speedX, int speedY, int bulletPlus) {
        super(locationX, locationY, speedX, speedY);
        this.bulletPlus = bulletPlus;
    }
    public void itemActive(HeroAircraft heroAircraft){
        //System.out.println("BulletPlusSupply active!");
        Runnable r = () -> {
            heroAircraft.setStrategy(new RingShoot());
            try {
                Thread.sleep(bulletPlus*1000);
            } catch (InterruptedException e) {
                return;
            }
            heroAircraft.setStrategy(new StraightShoot());
        };
        if (heroAircraft.heroStateThread != null) {heroAircraft.heroStateThread.interrupt();}
        heroAircraft.heroStateThread=new Thread(r);
        heroAircraft.heroStateThread.start();
    }
    public int getBullet() { return bulletPlus; }
}
