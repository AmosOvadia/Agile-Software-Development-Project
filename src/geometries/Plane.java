package geometries;

import primitives.Point;
import primitives.Vector;


/**
 * represents a geometrical plane in space
 */
public class Plane implements Geometry{
    /**
     * the point in the plane
     */
    private Point q0;
    /**
     * normal vector that represents the plain
     */
    private Vector normal;

    /**
     * constructor that gets 3 points
     * @param p1 x
     * @param p2 y
     * @param p3 z
     */
    public Plane(Point p1,Point p2,Point p3) {
        normal = null;
        q0 = p1;
    }

    /**
     * constructor that gets a point and a vector
     * @param p Point
     * @param v Vector
     */
    public Plane(Point p, Vector v) {
        normal = v;
        q0 = p;
    }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    /**
     * get the normal of the plain
     * @return Vector
     */

    public Vector getNormal() {
        return normal;
    }
}
