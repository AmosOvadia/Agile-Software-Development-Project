package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * a class representation of sphere shapes
 */
public class Sphere extends RadialGeometry{
    /**
     * the center point of the sphere
     */
    private Point center;

    /**
     * the radius of the z axes of the sphere
     */
    private double radius;

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
