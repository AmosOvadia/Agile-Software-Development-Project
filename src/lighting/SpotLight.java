package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * a class that represents a spot light
 */
public class SpotLight extends PointLight{
    private Vector direction;
    private double beamRadius;
    /**
     * ctor for spotLight
     * @param color color
     * @param location location
     * @param direction direction
     */
    public SpotLight(Color color, Point location, Vector direction) {
        super(color,location);
        this.direction = direction.normalize();
    }

    public Color getIntensity(Point p)
    {
        double m = Math.max(0,direction.dotProduct(getL(p)));
        m = Math.pow(m,beamRadius);
        return super.getIntensity(p).scale(m);
    }
    public Vector getL(Point p)
    {
        return super.getL(p);
    }

    public SpotLight setNarrowBeam(double beamRadius){
        this.beamRadius = beamRadius;
        return this;
    }



}
