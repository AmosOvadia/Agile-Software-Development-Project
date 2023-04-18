package geometries;

import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import primitives.Point;

/**
 * represents a geometric cylinder
 */
    public class Cylinder extends Tube {
    /**
     * represents the height of the cylinder
     */
    private double height;

    public Cylinder(Ray a, double r, double h) {
        super(a, r);
        if (h < 0)
            throw new IllegalArgumentException("the height less then zero!");
        this.height = h;
    }

    @Override
    /**

     Returns the normal to the cylinder at a given point on its surface.
     @param p the point on the cylinder's surface to calculate the normal at
     @return the normal vector to the cylinder's surface at the given point
     @throws IllegalArgumentException if the given point is not on the cylinder's surface
     */
    public Vector getNormal(Point p) throws IllegalArgumentException {
// If the point is at the top or bottom center of the cylinder, return the cylinder's axis direction
        if (p.equals(axisRay.getP0())) {
            return axisRay.getDir().scale(-1).normalize();
        }
        if (p.equals(axisRay.getP0().add(axisRay.getDir().scale(height)))) {
            return axisRay.getDir().normalize();
        }
// Calculate the center point of the cylinder at the height of the given point
        double t;
        Point center;
        try {
            t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
            if(Util.isZero(t - height)  || Util.isZero(t))
                return axisRay.getDir().normalize();
            else
                return super.getNormal(p);
        } catch (IllegalArgumentException e) {
            return axisRay.getDir().normalize();
        }
    }
}
