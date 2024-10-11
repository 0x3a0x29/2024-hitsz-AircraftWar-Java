package edu.hitsz.prop.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.BasicObserver;
import edu.hitsz.music.MusicManager;

import static edu.hitsz.music.MusicManager.BOMB_EXPLOSION_MUSIC;

public class Bomb extends Item {
    private int bomb = 0;
    public Bomb(int locationX, int locationY, int speedX, int speedY, int bomb) {
        super(locationX, locationY, speedX, speedY);
        this.bomb = bomb;
    }
    public void itemActive(HeroAircraft heroAircraft){
        for (Object observer:this.observers){
            ((BasicObserver)observer).response();
        }
    }

    public int getBomb() { return bomb; }
}
