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



    public Sphere(Point p,double r)
    {
        if(r < 0)
            throw new IllegalArgumentException("ERROR: the radius less then zero");
        this.radius = r;
        center = p;
    }
    /**
     * the radius of the z axes of the sphere
     */
    @Override
    public Vector getNormal(Point p) {
        Vector result = p.subtract(center);
       if(result.length() != radius)
           throw new IllegalArgumentException("the point is not on the sphere");
       return result.normalize();
    }
}
