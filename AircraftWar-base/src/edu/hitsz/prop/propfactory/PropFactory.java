package edu.hitsz.prop.propfactory;
import edu.hitsz.prop.props.Item;

public interface PropFactory {
    public Item createProp(int locationX, int locationY, int speedX, int speedY, int attribute);
}
