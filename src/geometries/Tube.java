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

    public  Tube(Ray a, double r)
    {
        if(r < 0)
            throw new IllegalArgumentException("ERROR: the radius less then zero");
        this.axisRay = a;
        this.radius = r;
    }
    @Override
    public Vector getNormal(Point p) {
       Vector vector = p.subtract(axisRay.getP0());
       double t = axisRay.getDir().dotProduct(vector);
       Point center = axisRay.getP0().add(axisRay.getDir().scale(t));
        Vector result = p.subtract(center);
        if(result.length() != radius)
            throw new IllegalArgumentException("the point is not on the sphere");
        return result.normalize();
    }
}