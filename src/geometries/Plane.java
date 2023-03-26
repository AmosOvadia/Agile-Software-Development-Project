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
       Point point1 = p1.subtract(p2);
       Point point2 = p3.subtract(p2);
       Vector vector1 = new Vector(point1.getXyz().getD1()  ,point1.getXyz().getD2(),point1.getXyz().getD3());
       Vector vector2 = new Vector(point2.getXyz().getD1()  ,point2.getXyz().getD2(),point2.getXyz().getD3());
        normal = vector1.crossProduct(vector2).normalize();
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
        return normal;
    }

    /**
     * get the normal of the plain
     * @return Vector
     */

    public Vector getNormal() {
        return normal;
    }
}
