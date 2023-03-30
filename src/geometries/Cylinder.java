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

    public Cylinder(Ray a, double r,double h) {
        super(a, r);
        if (h < 0 )
            throw  new IllegalArgumentException("the height less then zero!");
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
        if(p.equals(axisRay.getP0().add(axisRay.getDir().scale(height))))
        {
            return axisRay.getDir().normalize();
        }
// Calculate the center point of the cylinder at the height of the given point
        double t;
        Point center;
        try {
            t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
            center = axisRay.getP0().add(axisRay.getDir().scale(t));
        }
        catch(IllegalArgumentException e)
        {
            return axisRay.getDir().normalize();
        }
// If the center point is the same as the top or bottom center of the cylinder, return the cylinder's axis direction
        if (center.equals(axisRay.getP0()) || center.equals(axisRay.getP0().add(axisRay.getDir().scale(height)))) {
            return axisRay.getDir().normalize();
        }
// Check if the given point is on the curved surface of the cylinder
        if (t <= height && t >= 0) {
            Vector result = p.subtract(center);
            double s = result.length() - radius;
// Check if the point is on the cylinder's surface by checking the distance to the center axis minus the radius
            if (Math.abs(s) > 1e-10) {
                throw new IllegalArgumentException("The point is not on the cylinder.");
            }
            return result.normalize();
        } else {
            throw new IllegalArgumentException("The point is not on the cylinder.");
        }
    }
}
