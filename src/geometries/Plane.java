package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;


/**
 * represents a geometrical plane in space
 */
public class Plane extends Geometry {
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
       q0 = p1;
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
     * get the normal of the plane
     * @return Vector
     */

    public Vector getNormal() {
        return normal;
    }


    /**
     * finds the intersections of a ray with the plane
     * @return List<Point>
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
        //if the ray is parallel to the planmaxDistancee
        if(isZero(ray.getDir().dotProduct(normal)))
            return null;

        //if the ray starts from the plane
        if(ray.getP0().equals(q0))
            return null;

        //calculating the point of intersection
        double t = alignZero((q0.subtract(ray.getP0())).dotProduct(normal) / (ray.getDir().dotProduct(normal)));

        //if the ray is behind the plane
        if (t <= 0)
            return null;

        Point p = ray.getPoint(t);
        //if(alignZero(p.distance(ray.getP0()) - maxDistance) <= 0)
        if(p.distance(ray.getP0()) <= maxDistance)

            return List.of(new GeoPoint(this,p));
        else return null;
    }
}
