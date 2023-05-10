package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * ctor for Directional Light
     * @param color
     * @param direction
     */
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction.normalize();
    }

    public Color getIntensity(Point p)
    {
         return this.getIntesity();
    }
    public Vector getL(Point p)
    {
        return  direction;
    }

}

