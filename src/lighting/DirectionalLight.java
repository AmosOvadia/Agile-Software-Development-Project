package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * a class that represents a directional light
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * ctor for Directional Light
     * @param color color
     * @param direction direction
     */
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction.normalize();
    }

    /**
     * getter for intensity
     * @param p point
     * @return Color
     */
    public Color getIntensity(Point p)
    {
         return this.getIntesity();
    }

    /**
     * gets the vector from the light source to the point on the geometry
     * @param p point
     * @return Vector
     */
    public Vector getL(Point p)
    {
        return  direction;
    }

}

