package primitives;

import static java.lang.Math.sqrt;

/**
 * a class representing a point in space
 */
public class Point {
    /**
     * axes of the point
     */
    protected Double3 xyz;

    public static final Point ZERO = new Point(0,0,0);

    public Double3 getXyz() {
        return xyz;
    }

    /**
     * ctor for the point
     * @param d three double numbers
     */
    Point(Double3 d) {
        xyz = d;
    }

    /**
     * ctor for the point
     * @param d1 x
     * @param d2 y
     * @param d3 z
     */
    public Point(double d1,double d2,double d3) {
        xyz = new Double3(d1,d2,d3);
    }

    public double getX() {
        return xyz.d1;
    }
    public double getY() {
        return xyz.d2;
    }

    public double getZ() {
        return xyz.d3;
    }

    /**
     * subtracts two points
     * @param obj the point that is subtracted
     * @return the result vector
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return this.xyz.equals(other.xyz);

        return false;
    }

    @Override
    public  String toString() {return "point: " + xyz.toString();}

    /**
     * adds a vector to a point
     * @param vec the vector
     * @return the result point
     */
    public Point add(Vector vec) {
        return new Point(this.xyz.add(vec.xyz));

    }

    /**
     * subtracts a vector from a point
     * @param p the vector
     * @return Vector
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract(p.xyz));
    }

    /**
     * calculates the distance between two points
     * @param p the other point
     * @return double
     */
    public double distance(Point p) {
        return sqrt(this.distanceSquared(p));
    }

    /**
     * calculates the distance squared between two points
     * @param p the other point
     * @return double
     */
    public double distanceSquared(Point p) {
        Double3 p1 = this.xyz;
        Double3 p2 = p.xyz;
        return (p1.d1 - p2.d1)*(p1.d1 - p2.d1) + (p1.d2 - p2.d2)*(p1.d2 - p2.d2) + (p1.d3 - p2.d3)*(p1.d3 - p2.d3);//more efficient than calling distance
    }
}
