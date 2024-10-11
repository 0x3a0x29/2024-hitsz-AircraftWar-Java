package edu.hitsz.aircraft.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class RingShoot implements ShootStrategy{
    public List<BaseBullet> doShoot(AbstractAircraft aircraft){
        List<BaseBullet> res= new LinkedList<>();
        int direction = aircraft.getDirection();
        int shootNum = 20;
        int power = aircraft.getPower();
        int x=aircraft.getLocationX();
        int y=aircraft.getLocationY() +direction*2;
        int[] bulletSpeedX = new int[]{0,4,6,8,9,10,9,8,6,4,0,-4,-6,-8,-9,-10,-9,-8,-6,-4};
        int[] bulletSpeedY = new int[]{-10,-9,-8,-6,-4,0,4,6,8,9,10,9,8,6,4,0,-4,-6,-8,-9};
        int speedX = aircraft.getSpeedX();
        int speedY = aircraft.getSpeedY() +direction*5;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x, y, speedX + bulletSpeedX[i], speedY + bulletSpeedY[i], power);
            }else{
                bullet = new EnemyBullet(x, y, speedX + bulletSpeedX[i], speedY + bulletSpeedY[i], power);
            }
            res.add(bullet);
        }
        return res;
    }
}
