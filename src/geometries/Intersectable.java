package geometries;
import java.util.ArrayList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * An interface for all geometries that can be intersected by a ray.
 */
public abstract class Intersectable {
    /**
     * Find the intersections of the ray with the geometry.
     *
     * @param ray The ray to intersect with.
     * @return A list of points where the ray intersects the geometry.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }


    /**
     * A class that represents a point on a geometry.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * constructor for GeoPoint
         * @param geometry the geometry
         * @param point the point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * subtracts two points
         * @param obj the point that is subtracted
         * @return the result vector
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint other)
                return (this.point.equals(other.point))&&(this.geometry.equals(other.geometry));

            return false;
        }
        @Override
        public  String toString() {
            return this.point.toString()+  " geometry: " + this.geometry + "\n";}

    }

    /**
     * finds the intersected points of a ray in the geometry
     * @param ray The ray to intersect with
     * @return A list of points where the ray intersects the geometry
     */
    public List<GeoPoint> findGeoIntersections(Ray ray)
    {
          return findGeoIntersectionsHelper(ray);
    }
     protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
