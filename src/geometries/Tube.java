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
     * @param a the axis ray
     * @param r the radius of the tube
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

    /**
     * Find the intersections of the ray with the tube.
     * @param ray The ray to intersect with.
     * @return A list of points where the ray intersects the tube.
     */
   public List<Point> findIntersections(Ray ray)
    {
        throw new UnsupportedOperationException("no implementation");
    }

    /**
     * not implemented
     * Find the intersections of the ray with the tube.
     * @param ray The ray to intersect with.
     * @return A list of geo points where the ray intersects the tube.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
 /*
       public List<Point> findIntersections(Ray ray)
        {
        //credit: https://mrl.cs.nyu.edu/~dzorin/rend05/lecture2.pdf
        // the tube's equation:
        //q = is a point on the tubes surface
        //pa + v*t is the tube's ray and r is the tube's radius
        //(q - pa - (va,q - pa)va)^2 - r^2 = 0
        //
        //p+vt is the intersecting ray
        //substituting q = p+v*t :
        //(p - pa + vt - (va,p - pa + v*t)va)2 - r2 = 0
        //
        //we reduce the equation to a quadratic equation
        //At^2 + Bt + C = 2
        //
        //where A B and C are:
        //A = (v - (v,va)va)^2
        //B = 2(v - (v,va)va, Δp - (Δp,va)va)
        //C = (Δp - (Δp,va)va)^2 - r^2
        // and Δp = p - pa


        //va
        Vector dir = this.axisRay.getDir();
        //v
        Vector rayDir = ray.getDir();
        try // if the two vectors are parallel - there are no intersections
        {
            dir.crossProduct(rayDir);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
        //p
        Point p1 = ray.getP0();
        //pa
        Point pNil = this.axisRay.getP0();
        //Δp
        Vector deltaP, v, delPvava;


        double r, deltapva;

        double scale = rayDir.dotProduct(dir);//(v,va)
        if (isZero(scale))
            v = rayDir;
        else
            v = rayDir.subtract(dir.scale(scale));
        r = this.radius;

        double A, B, C;

        try
        {
            deltaP = p1.subtract(pNil);

            //
            deltapva = deltaP.dotProduct(dir);
            if(isZero(deltapva))
                delPvava = deltaP;
            else
                delPvava = deltaP.subtract(dir.scale(deltapva));
            B = 2 * v.dotProduct(delPvava);
            C = delPvava.dotProduct(delPvava) - r * r;
        }
        catch (IllegalArgumentException e)
        {
            B = 0;
            C = - r * r;
        }

        A = v.dotProduct(v);
        double t1 = 0;
        double t2 = 0;
        double delta = B * B - 4 * A * C;
        if (delta < 0)
            return null;
        if (isZero(delta))
        {
            t1 = -B / (2 * A);
            if (t1 < 0)
                return null;
            else
                return List.of(ray.getPoint(t1));
        }
        t1 = (-B + Math.sqrt(delta)) / (2 * A);
        t2 = (-B - Math.sqrt(delta)) / (2 * A);
        if (t1 < 0 && t2 < 0)
            return null;
        else if (t1 < 0)
            return List.of(ray.getPoint(t2));

        else if (t2 < 0)
            return List.of(ray.getPoint(t1));
        else
            return List.of(ray.getPoint(t1), ray.getPoint(t2));

    }
 */

}
