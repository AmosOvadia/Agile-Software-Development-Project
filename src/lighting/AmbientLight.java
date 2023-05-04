package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    public Color intensity;

    public AmbientLight(Color Ia, Double3 Ka) {

        double r = Ia.getColor().getRed() * Ka.getD1();
        double g = Ia.getColor().getGreen() * Ka.getD2();
        double b = Ia.getColor().getBlue() * Ka.getD3();

        intensity = new Color(r, g, b);
    }

    public AmbientLight(double Ka) {

        intensity.scale(Ka);
    }

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public Color getIntensity() {
        return intensity;
    }
}
