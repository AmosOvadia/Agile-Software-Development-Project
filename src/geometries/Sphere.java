package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.*;

/**
 * a class representation of sphere shapes
 */
public class Sphere extends RadialGeometry{
    /**
     * the center point of the sphere
     */
    private Point center;

    /**
     * constructor for the sphere
     * @param p the center point
     * @param r the radius of the sphere
     */
    public Sphere(Point p,double r)
    {
        if(r <= 0)
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
        //if(!isZero(result.length() - radius))
        // throw new IllegalArgumentException("the point is not on the sphere");
       return result.normalize();
    }

    /**
     * Find the intersections of the ray with the sphere.
     * @param ray The ray to intersect with.
     * @return A list of points where the ray intersects the sphere.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Vector u = null;
        try
        {
             u = center.subtract(ray.getP0());
        }
        catch (IllegalArgumentException e)
        {
            if(alignZero(radius - maxDistance) <= 0)
                return List.of(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(radius))));
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
            p2 = ray.getPoint(t2);
            if(alignZero(p2.distance(ray.getP0()) - maxDistance) <= 0)
                 return List.of(new GeoPoint(this, p2));
        }

        if(t2 <= 0)
        {
            p1 = ray.getPoint(t1);
            if(alignZero(p1.distance(ray.getP0()) - maxDistance) <= 0)
                return List.of(new GeoPoint(this, p1));
        }

        p1 = ray.getPoint(t1);
        p2 = ray.getPoint(t2);
        List<GeoPoint> points = new ArrayList<>();
        if(alignZero(p1.distance(ray.getP0()) - maxDistance) <= 0)
            points.add(new GeoPoint(this, p1));
        if(alignZero(p2.distance(ray.getP0()) - maxDistance) <= 0)
            points.add(new GeoPoint(this, p2));
        return points.isEmpty() ? null : points;
    }

    /**
     * finds the minimum coordinates of the sphere
     * @return the minimum coordinates of the sphere
     */
    @Override
    public Point getMinCoords() {
        return new Point(center.getX() - radius, center.getY() - radius, center.getZ() - radius);
    }

    /**
     * finds the maximum coordinates of the sphere
     * @return the maximum coordinates of the sphere
     */
    @Override
    public Point getMaxCoords() {
        return new Point(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
    }


    /**
     * returns the center point of the sphere
     * @return Point
     */
    @Override
    public Point getCenterPoint() {
        return center;
    }
}

