package edu.hitsz.aircraft;

import edu.hitsz.aircraft.shoot.ShootStrategy;
import edu.hitsz.aircraft.shoot.StraightShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    static private int maxHp = 800;
    private volatile static HeroAircraft instance = null;
    public Thread heroStateThread;
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp,int
                         power,int direction) {
        super(locationX, locationY, speedX, speedY, hp, power,direction);
        strategy = new StraightShoot();
    }
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }
    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return strategy.doShoot(HeroAircraft.instance);
    }

    public static synchronized HeroAircraft getInstance()
    {
        if (instance == null){
            synchronized (HeroAircraft.class){
                if (instance == null){
                    instance = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, HeroAircraft.maxHp,40,-1);
                }
            }
        }
        return instance;
    }
}
