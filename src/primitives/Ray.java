package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represents a ray in space
 */
public class    Ray {
    /**
     * represents the point of the ray
     */
    private Point p0;
    /**
     * represents the direction vector of the ray
     */
    private Vector dir;

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    /**
     * constructor that sets the point and vector
     * @param p point of the ray
     * @param v vector of the ray
     */
   public Ray(Point p, Vector v) {
        try {
            dir = new Vector( v.normalize().xyz);
        }

        //if the length of the vector is 0 we get an error for division by 0
        catch (ArithmeticException ex) {
            dir.xyz = new Double3(0, 0, 0);
        }
        p0 =  new Point(p.xyz);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.dir.equals(other.dir) &&  this.p0.equals(other.p0);

        return false;
    }

    @Override
    public String toString() {
        return "Ray: " + this.p0.toString() + this.dir.toString();
    }

    //(t double(getPoint Point p
    public Point getPoint(double t)
    {
        return this.p0.add(this.dir.scale(t));
    }

    /**
     * finds the closest point to the ray's p0
     * @param points list of points
     * @return a point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * finds the closest point to the ray's p0
     * @param intersections list of points
     * @return GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections)
    {
        GeoPoint closestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (GeoPoint geoPointp : intersections) { //iterates over the list of points
            double distance = this.p0.distance(geoPointp.point);
            if (distance < minDistance) {//if distance is smaller than the minimum distance we update
                minDistance = distance;
                closestPoint = geoPointp;
            }
        }
        return closestPoint;
    }
}
