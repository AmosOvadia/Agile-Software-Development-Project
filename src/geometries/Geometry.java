package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * interface for the representation of geometrical shapes in space
 */
public interface Geometry extends Intersectable {
    /**
     * gets the geometric normal of a geometric shape
     * @param P Point
     * @return Vector
     */
    Vector getNormal(Point P);
    
}
