package primitives;

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
     * @param p
     * @param v
     */
   public Ray(Point p, Vector v) {
        try {
            dir.xyz = v.normalize().xyz;
        }

        //if the length of the vector is 0 we get an error for division by 0
        catch (ArithmeticException ex) {
            dir.xyz = new Double3(0, 0, 0);
        }
        p0.xyz = p.xyz;
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

}
