package lighting;

import primitives.Color;

/**
 *Light CLASSSSSS
 */
abstract class Light {
    private Color intesity;

    /**
     * to get the intesity
     * @return Color
     */
    public Color getIntesity() {
        return intesity;
    }

    /**
     * ctor
     * @param intesity
     */
    protected Light(Color intesity) {
        this.intesity = intesity;
    }
}
