package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.List;

/**
 * a class representation of a triangle
 */
public class Triangle extends Polygon {
    /**
     * constructor for the triangle
     *
     * @param p1 point x
     * @param p2 point y
     * @param p3 point z
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * checks if a point is inside the triangle
     * NOTE: this method only works for a point that is on the same plane as the triangle
     * @param p
     * @return true if the point is inside the triangle, false otherwise
     */
    public boolean PointInTriangle(Point p) {

        //get the three vectors of the triangle
        Vector ab = this.vertices.get(1).subtract(this.vertices.get(0));
        Vector bc = this.vertices.get(2).subtract(this.vertices.get(1));
        Vector ca = this.vertices.get(0).subtract(this.vertices.get(2));

        //get the vectors from the point to the vertices
        Vector ap, bp, cp, crossABAP, crossBCBP, crossCACP;
        try //if the point is one of the vertices
        {
            ap = p.subtract(this.vertices.get(0));
            bp = p.subtract(this.vertices.get(1));
            cp = p.subtract(this.vertices.get(2));

            crossABAP = ab.crossProduct(ap);
            crossBCBP = bc.crossProduct(bp);
            crossCACP = ca.crossProduct(cp);
        }
        catch (IllegalArgumentException e) // point on triangle
        {
            return false;
        }

        //if the dotproduct of the vectors are negative => the vectors are of different signs and the point is outside the triangle
        return (alignZero(crossABAP.dotProduct(crossBCBP)) > 0) && (alignZero(crossBCBP.dotProduct(crossCACP)) > 0);
    }

        /*
        //get the three vectors of the triangle
        Vector v1 = this.vertices.get(0).subtract(this.vertices.get(1));
        Vector v2 = this.vertices.get(0).subtract(this.vertices.get(2));
        Vector v3 = this.vertices.get(1).subtract(this.vertices.get(2));

        //calculate the area of the triangle
        double TriangleArea = v1.crossProduct(v2).length() / 2;

        //get the vectors from the point to the vertices
        Vector pa = this.vertices.get(0).subtract(p);
        Vector pb = this.vertices.get(1).subtract(p);
        Vector pc = this.vertices.get(2).subtract(p);

        //calculate the area of the three triangles that are created by the point and the vertices of the triangle
        double Area1 = pa.crossProduct(pb).length() / 2;
        double Area2 = pb.crossProduct(pc).length() / 2;
        double Area3 = pc.crossProduct(pa).length() / 2;

        //return true if the sum of the areas is equal to the area of the triangle
        return Math.abs(Area1 + Area2 + Area3 - TriangleArea) < 0.0000001;

         */


    /**
     * finds the intersected points of a ray in a triangle
     * @param ray The ray to intersect with.
     * @return a list of the intersected points (one)
     */
    public List<Point> findIntersections(Ray ray)
    {
        //get the intersection point with the plane that the triangle is on
        Plane plane1 = new Plane(this.vertices.get(0), this.vertices.get(1), this.vertices.get(2));
        List<Point> list = plane1.findIntersections(ray);

        //check if the point is in the triangle
        if (list != null) {
            if (PointInTriangle(list.get(0))) {
                return list;
            }
        }
        return null;
    }
}

