package geometries;

import primitives.Point;
import primitives.Ray;

public class AABB {

    Point min;
    Point max;

    /**
     * constructor for AABB
     * @throws IllegalArgumentException if a min point coordinate is bigger than a max point coordinate
     * @param min the minimum point
     * @param max the maximum point
     */
    public AABB(Point min, Point max) {

        if(min.getX() > max.getX() || min.getY() > max.getY() || min.getZ() > max.getZ())
        {
            System.out.println("min: " + min);
            System.out.println("max: " + max);
            throw new IllegalArgumentException("min point must be smaller than max point");
        }

        this.min = min;
        this.max = max;
    }

    public boolean PointInAABB(Point p)
    {
        return (p.getX() >= min.getX() && p.getX() <= max.getX() &&
                p.getY() >= min.getY() && p.getY() <= max.getY() &&
                p.getZ() >= min.getZ() && p.getZ() <= max.getZ());
    }

    public boolean intersects(Ray ray)
    {
        double tmin = (min.getX() - ray.getP0().getX()) / ray.getDir().getX();
        double tmax = (max.getX() - ray.getP0().getX()) / ray.getDir().getX();

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        double tymin = (min.getY() - ray.getP0().getY()) / ray.getDir().getY();
        double tymax = (max.getY() - ray.getP0().getY()) / ray.getDir().getY();

        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (min.getZ() - ray.getP0().getZ()) / ray.getDir().getZ();
        double tzmax = (max.getZ() - ray.getP0().getZ()) / ray.getDir().getZ();

        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        return true;
    }

    public Point getMin() {
        return min;
    }
    public Point getMax() {
        return max;
    }

    public Point getCenterPoint()
    {
        return new Point((min.getX() + max.getX())/2, (min.getY() + max.getY())/2, (min.getZ() + max.getZ())/2);
    }

    public void setMin(Point min) {
        this.min = min;
    }
    public void setMax(Point max) {
        this.max = max;
    }
}
