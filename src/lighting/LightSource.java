package lighting;

import primitives.*;

/**
 * Light source interface
 */
public interface LightSource {
    public Color getIntensity(Point p);
    public Vector getL(Point p);

}
