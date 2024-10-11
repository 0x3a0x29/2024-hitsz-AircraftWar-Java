package edu.hitsz.prop.props;

import edu.hitsz.aircraft.HeroAircraft;

public class Blood extends Item {
    private int blood = 20;
    public Blood(int locationX, int locationY, int speedX, int speedY, int blood) {
        super(locationX, locationY, speedX, speedY);
        this.blood = blood;
    }
    public void itemActive(HeroAircraft heroAircraft){
        heroAircraft.increaseHp(this.blood);
    }
    public int getBlood() { return blood; }
}
