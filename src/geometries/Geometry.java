package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface for the representation of geometrical shapes in space
 */
public interface Geometry {

    /**
     * gets the geometric normal of a geometric shape
     * @param P Point
     * @return Vector
     */
    public Vector getNormal(Point P);
}
