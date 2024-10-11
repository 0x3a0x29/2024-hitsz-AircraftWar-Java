package edu.hitsz.aircraft;

import edu.hitsz.aircraft.shoot.ShootStrategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;//最大生命值
    protected int hp;//现在的生命值
    protected int power;
    protected int direction;
    protected ShootStrategy strategy;
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY,
                            int hp,int power,int direction) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.power = power;
        this.direction = direction;
    }//构造方法

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }//掉血
    public void increaseHp(int increase){
        hp += increase;
        if(hp > maxHp){
            hp=maxHp;
        }
    }//加血

    public int getHp() {return hp;}//获取生命值
    public int getMaxHp() {return maxHp;}//获取生命值
    public int getPower(){return power;}
    public int getDirection(){return direction;}
    public void setStrategy(ShootStrategy strategy) {
        this.strategy = strategy;
    }
    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();
}


