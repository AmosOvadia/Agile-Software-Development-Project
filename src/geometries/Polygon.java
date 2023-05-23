package geometries;

import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    /**
     * checks if a point is inside the polygon
     *NOTE: this method only works for a point that is on the same plane as the polygon
     * @param p
     * @return true if the point is inside the polygon, false otherwise
     */
    public boolean PointInPolygon(Point p) {
        //get the vectors from the point to the vertices
        Vector edge, vp, crossABAP, crossBCBP;
        Point p1;
        int size = this.vertices.size();

        //get the first vector of the polygon
        edge = this.vertices.get(0).subtract(this.vertices.get(size -1));
        try //if the point is one of the vertices
        {
            vp = p.subtract(this.vertices.get(size -1));
            crossABAP = edge.crossProduct(vp);
        } catch (IllegalArgumentException e) // point on triangle
        {
            return false;
        }

        //iterating through the vertices of the polygon and checking the cross product with the point
        for (int i = 0; i < size - 1; i++) {
            //get the current vertex of the polygon
            p1 = this.vertices.get(i);
            try {
                //get the next vector of the polygon
                edge = this.vertices.get(i + 1).subtract(p1);
                //get the vector from the point to the next vertex
                vp = p.subtract(p1);
                //get the cross product of the vectors
                crossBCBP = edge.crossProduct(vp);
            } catch (IllegalArgumentException e) // point on polygon
            {
                return false;
            }
            //if the dot-product of the vectors are negative => the vectors are of different signs and the point is outside the polygon
            if (alignZero(crossABAP.dotProduct(crossBCBP)) < 0) {
                return false;
            }
            //update the cross product so there's never more than 180 degrees between the vectors
            crossABAP = crossBCBP;
        }
        return true;
    }

    /**
     * finds the intersections of a ray with the polygon
     * @param ray The ray to intersect with.
     * @return A list of the intersection point of the ray with the polygon.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        //find the intersections of the ray with the plane
        List<GeoPoint> geoList = filterIntersections(plane.findGeoIntersections(ray), ray, maxDistance);
        List<Point> intersections  = (geoList == null) ? null : geoList.stream().map(gp -> gp.point).toList();
        //if there are no intersections with the plane, return null
        if (intersections != null) {
            //if the point is inside the polygon, return the intersection point
            if (PointInPolygon(intersections.get(0))) {
                List<GeoPoint> gp = new ArrayList<>(intersections.size());
                for (var p: intersections) {
                    gp.add(new GeoPoint(this,p));
                }
                return gp;
            }
        }
        //if the point is not inside the polygon, return null
        return null;
    }

}
