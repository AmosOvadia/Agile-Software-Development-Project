package lighting;

import primitives.*;

/**
 * interface for all the light sources
 */
public interface LightSource {
    /**
     * gets the intensity of the light at a point
     * @param p point
     * @return Color
     */
    public Color getIntensity(Point p);

    /**
     * gets the vector from the light source to the point on the geometry
     * @param p point
     * @return Vector
     */
    public Vector getL(Point p);
    public double getDistance(Point point);
}
