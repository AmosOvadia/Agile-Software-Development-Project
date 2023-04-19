package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

/**
 * a class representation of a tube in space
 */
public class Tube extends RadialGeometry{
    /**
     * the ray representation of the tube (direction and length)
     */
    protected Ray axisRay;

    /**
     * constructor for the tube
     * @param a
     * @param r
     */
    public  Tube(Ray a, double r)
    {
        if(r < 0)
            throw new IllegalArgumentException("ERROR: the radius less then zero");
        this.axisRay = a;
        this.radius = r;
    }

    /**
     * the normal of for the axis ray
     * @param p Point
     * @return the normal vector of the tube
     */
    @Override
    public Vector getNormal(Point p) {
        Vector vector = p.subtract(axisRay.getP0());
        double t = axisRay.getDir().dotProduct(vector);
        if(t == 0)
            return vector.normalize();
        Point center = axisRay.getP0().add(axisRay.getDir().scale(t));
        Vector result = p.subtract(center);
        double s = result.length() - radius;
        if (Math.abs(s) > 1e-10) {
            throw new IllegalArgumentException("The point is not on the tube.");
        }
        return result.normalize();

    }

    public List<Point> findIntersections(Ray ray)
    {
        return null;
    }
}
