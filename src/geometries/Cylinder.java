package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * represents a geometric cylinder
 */
    public class Cylinder extends Tube{
    /**
     * represents the height of the cylinder
     */
    private double height;

    public Cylinder(Ray a, double r) {
        super(a, r);
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
