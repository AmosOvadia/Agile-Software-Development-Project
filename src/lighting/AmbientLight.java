package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light {

    /**
     * ctor
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(new Color(
            Ia.getColor().getRed() * Ka.getD1(),
            Ia.getColor().getGreen() * Ka.getD2(),
            Ia.getColor().getBlue() * Ka.getD3()
            ));
    }


    //public AmbientLight(double Ka) {
      //  intensity.scale(Ka);
    //}

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public Color getIntensity() {
        return super.getIntesity();
    }
}
