package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * a abstract class for ray tracers
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * constructor for ray tracer base
     * @param scene scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * abstract function for tracing a ray
     * @param ray ray
     * @return Color
     */
    public abstract Color traceRay(Ray ray);
}
