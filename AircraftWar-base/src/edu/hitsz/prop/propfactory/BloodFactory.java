package edu.hitsz.prop.propfactory;

import edu.hitsz.prop.props.Blood;
import edu.hitsz.prop.props.Item;

public class BloodFactory implements PropFactory{
    public Item createProp(int locationX, int locationY, int speedX, int speedY, int attribute)
    {
        return new Blood(locationX,locationY,speedX,speedY,attribute);
    }
}
