package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * a class representation of a tube in space
 */
public class Tube extends RadialGeometry{
    /**
     * the ray representation of the tube (direction and length)
     */
    protected Ray axisRay;

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
