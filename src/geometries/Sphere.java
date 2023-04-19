package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

import static primitives.Util.alignZero;

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

    /**
     * Find the intersections of the ray with the sphere.
     * @param ray The ray to intersect with.
     * @return A list of points where the ray intersects the sphere.
     */
    public List<Point> findIntersections(Ray ray) {
        Vector u;
        try
        {
             u = center.subtract(ray.getP0());
        }
        catch (IllegalArgumentException e)
        {
            return List.of(ray.getP0().add(ray.getDir().scale(radius)));
        }

        double tm = u.dotProduct(ray.getDir());

        double dcheck = u.lengthSquared() - tm * tm;
        if(dcheck < 0)
            return null;
        double d = Math.sqrt(dcheck);
        if (d >= radius)
            return null;
        double th = Math.sqrt(radius * radius - d * d);
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if(t1 <= 0 && t2 <= 0)
            return null;
        Point p1, p2;
        if (t1 <=0)
        {
            p2 = ray.getP0().add(ray.getDir().scale(t2));
            return List.of(p2);
        }

        if(t2 <= 0)
        {
            p1 = ray.getP0().add(ray.getDir().scale(t1));
            return List.of(p1);
        }

        p1 = ray.getP0().add(ray.getDir().scale(t1));
        p2 = ray.getP0().add(ray.getDir().scale(t2));

        return List.of(p1,p2);

    }
}

