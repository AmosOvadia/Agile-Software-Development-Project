package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * a class that represents a point light
 */
public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * ctor for pointLight
     * @param intensity intensity
     * @param position position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * setter for kC
     * @param kC kC
     * @return PointLight
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return  this;
    }

    /**
     * setter for kL
     * @param kL kL
     * @return PointLight
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return  this;
    }

    /**
     * setter for kQ
     * @param kQ kQ
     * @return PointLight
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return  this;
    }

    public Color getIntensity(Point p)
    {
        Color i0 = this.getIntesity();
        double d = p.subtract(position).length();
        return i0.scale(1/( (kC+(d*kL)+(kQ*d*d) )));
    }

    public Vector getL(Point p)
    {
        try {
            return p.subtract(position).normalize();
        }
        catch (IllegalArgumentException ex)
        {
            throw new IllegalArgumentException("light is on the geometry ");
        }
    }

    /**
     * gets the distance from the light source to the point on the geometry
     * @param point point
     * @return double
     */
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
