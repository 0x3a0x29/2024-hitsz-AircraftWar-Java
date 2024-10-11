package edu.hitsz.prop.propfactory;

import edu.hitsz.prop.props.Bullet;
import edu.hitsz.prop.props.Item;

public class BulletFactory implements PropFactory{
    @Override
    public Item createProp(int locationX, int locationY, int speedX, int speedY, int attribute) {
        return new Bullet(locationX,locationY,speedX,speedY,attribute);
    }
}
