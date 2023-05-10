package scene;
import geometries.*;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * a class for scene
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<>();

    /**
     * constructor for scene
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }


    /**
     * add geometries to the scene
     * @param background the background color of the scene
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * add ambient light to the scene
     * @param ambientLight the ambient light of the scene
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * add geometries to the scene
     * @param geometries the geometries of the scene
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * add lights to the scene
     * @param lights the lights of the scene
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
