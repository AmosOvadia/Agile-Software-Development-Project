package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * a class that represents an ambient light
 */
public class AmbientLight extends Light {

    /**
     * constructor for AmbientLight class
     * @param Ia intensity
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(new Color(
            Ia.getColor().getRed() * Ka.getD1(),
            Ia.getColor().getGreen() * Ka.getD2(),
            Ia.getColor().getBlue() * Ka.getD3()
            ));
    }
    /**
     * constructor for AmbientLight class
     * @param Ia intensity
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia, double Ka) {
        super(new Color(
                Ia.getColor().getRed() * Ka,
                Ia.getColor().getGreen() * Ka,
                Ia.getColor().getBlue() * Ka
        ));
    }


    //public AmbientLight(double Ka) {
      //  intensity.scale(Ka);
    //}

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * getter for intensity
     * @return Color
     */
    public Color getIntensity() {
        return super.getIntesity();
    }
}
