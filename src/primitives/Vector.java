package primitives;


import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;


/**
 * class representation of a vector in space
 */
public class Vector extends Point{

    /**
     * constructor that gets a Double3
     * @param d a Double3
     * @throws IllegalArgumentException if all axes are 0
     */
    Vector(Double3 d) {
        super(d);

        //if all axes are 0 we throw an error
        if(d.d1 == 0 &&  d.d2 == 0 && d.d3 == 0)
            throw new IllegalArgumentException();
    }

    /**
     * constructor that gets 3 doubles
     * @param d1 x
     * @param d2 y
     * @param d3 z
     */
    public Vector(double d1,double d2,double d3) {
       super(d1, d2, d3);

        //if all axes are 0 we throw an error
        if(d1 == 0 &&  d2 == 0 && d3 == 0)
            throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Vector other)
            return super.equals(other);

        return false;
    }
    public boolean equals(Vector other, double epsilon) {
        return Math.abs(this.xyz.getD1() - other.xyz.getD1()) < epsilon
                && Math.abs(this.xyz.getD2() - other.xyz.getD2()) < epsilon
                && Math.abs(this.xyz.getD3() - other.xyz.getD3()) < epsilon;
    }
    @Override
    public String toString() {
        return "Vector: " + super.toString();
    }

    /**
     * adds two vectors
     * @param vVal the vector that is added
     * @return the result vector
     */
    public Vector add(Vector vVal){
        return new Vector(super.add(vVal).xyz);
    }

    /**
     * scales the vectors size (makes it bigger or smaller)
     * @param d a double in which to multiply the vector
     * @return the new scaled vector
     */
    public Vector scale(double d) {
        return new Vector(this.xyz.scale(d));
    }

    /**
     * calculates the cross product of two vectors
     * @param vVal the second vector
     * @return the new vector from the result of the calculation
     */
    public Vector crossProduct(Vector vVal) {

        double d1 =  this.xyz.d2 * vVal.xyz.d3 - this.xyz.d3 * vVal.xyz.d2;
        double d2 = this.xyz.d3 * vVal.xyz.d1 - this.xyz.d1 * vVal.xyz.d3;
        double d3 = this.xyz.d1 * vVal.xyz.d2 - this.xyz.d2 * vVal.xyz.d1;

        return new Vector(alignZero(d1),alignZero(d2),alignZero(d3));
    }

    /**
     * calculates the distance between two vectors
     * @return the length of the vector squared
     */
    public double lengthSquared() {
        return this.distanceSquared(new Point(0,0,0));
    }

    /**
     * calculates the distance between two vectors
     * @return the length of the vector
     */
    public double length() {
        return sqrt(this.lengthSquared());
    }

    /**
     * normalizing the vector so its length will be 1
     * @return a normalized vector
     */
    public Vector normalize() {
        if(this.length() == 0)
            throw new ArithmeticException();
        return new Vector(this.xyz.reduce(this.length()));
    }

    /**
     * calculates the sum of the products of corresponding components
     * @param v the second vector
     * @return the scalar product
     */
    public double dotProduct(Vector v) {
        return  this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 +this.xyz.d3 * v.xyz.d3;
    }

}