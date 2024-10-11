package edu.hitsz.prop.propfactory;

import edu.hitsz.prop.props.Bomb;
import edu.hitsz.prop.props.Item;

public class BombFactory implements PropFactory{
    public Item createProp(int locationX, int locationY, int speedX, int speedY, int attribute)
    {
        return new Bomb(locationX,locationY,speedX,speedY,attribute);
    }
}
