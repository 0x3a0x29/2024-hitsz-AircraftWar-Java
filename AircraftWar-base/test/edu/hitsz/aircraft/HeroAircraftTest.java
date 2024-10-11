package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    HeroAircraft heroAircraft=HeroAircraft.getInstance();;
    @Test
    void decreaseHp() {
        heroAircraft.increaseHp(100);
        heroAircraft.decreaseHp(30);
        assertEquals(heroAircraft.getHp(),70);
    }

    @Test
    void increaseHp() {
        heroAircraft.increaseHp(100);
        heroAircraft.decreaseHp(10);
        heroAircraft.increaseHp(20);
        assertEquals(heroAircraft.getHp(),100);
    }

    @Test
    void shoot() {
        List<BaseBullet> bullets=new LinkedList<BaseBullet>();
        bullets.addAll(heroAircraft.shoot());
        for (BaseBullet i:bullets){
            assertEquals(i.getLocationX(),heroAircraft.getLocationX());
            assertEquals(i.getLocationY(),heroAircraft.getLocationY()-2);
            assertEquals(i.getSpeedX(),heroAircraft.getSpeedX());
            assertEquals(i.getSpeedY(),heroAircraft.getSpeedY()-5);
            assertEquals(i.getPower(),30);
        }

    }
}