package edu.hitsz.aircraft.enemyfactory;

import edu.hitsz.aircraft.AbstractEnemy;

public interface EnemyFactory {
    public AbstractEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
                                     int power,int direction);
}
