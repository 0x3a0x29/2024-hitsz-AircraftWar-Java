package edu.hitsz.aircraft.enemyfactory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.SuperEliteEnemy;

public class SuperEliteEnemyFactory implements EnemyFactory{
    public AbstractEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
                                     int power,int direction)
    {
        return new SuperEliteEnemy(locationX,locationY,speedX,speedY,hp,power,direction);
    }
}
