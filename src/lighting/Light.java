package lighting;

import primitives.Color;

/**
 * abstract class for all the lights
 */
abstract class Light {
    private Color intensity;

    /**
     * to get the intensity
     * @return Color
     */
    public Color getIntesity() {
        return intensity;
    }

    /**
     * ctor for Light
     * @param intensity intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }
}
