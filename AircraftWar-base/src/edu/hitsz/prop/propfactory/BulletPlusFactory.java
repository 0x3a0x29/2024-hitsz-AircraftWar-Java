package edu.hitsz.prop.propfactory;

import edu.hitsz.prop.props.BulletPlus;
import edu.hitsz.prop.props.Item;

public class BulletPlusFactory implements PropFactory{
    @Override
    public Item createProp(int locationX, int locationY, int speedX, int speedY, int attribute) {
        return new BulletPlus(locationX,locationY,speedX,speedY,attribute);
    }
}
