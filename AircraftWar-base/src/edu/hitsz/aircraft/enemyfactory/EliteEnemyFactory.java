package edu.hitsz.aircraft.enemyfactory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.EliteEnemy;

public class EliteEnemyFactory implements EnemyFactory{
    public AbstractEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
                                     int power,int direction)
    {
        return new EliteEnemy(locationX,locationY,speedX,speedY,hp,power,direction);
    }
}
