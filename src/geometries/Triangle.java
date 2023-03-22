package geometries;

import primitives.Point;

/**
 * a class representation of a triangle
 */
public class Triangle extends Polygon{
    /**
     *constructor for the triangle
     * @param p1 point x
     * @param p2 point y
     * @param p3 point z
     */
    public Triangle(Point p1,Point p2,  Point p3) {
        super(p1,p2,p3);
    }
}
