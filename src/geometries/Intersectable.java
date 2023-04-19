package geometries;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * An interface for all geometries that can be intersected by a ray.
 */
public interface Intersectable {
    /**
     * Find the intersections of the ray with the geometry.
     * @param ray The ray to intersect with.
     * @return A list of points where the ray intersects the geometry.
     */
    List<Point> findIntersections(Ray ray);
}
