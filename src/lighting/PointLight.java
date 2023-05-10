package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * ctor for pointLight
     * @param intesity
     * @param position
     */
    public PointLight(Color intesity, Point position) {
        super(intesity);
        this.position = position;
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return  this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return  this;
    }

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



}
