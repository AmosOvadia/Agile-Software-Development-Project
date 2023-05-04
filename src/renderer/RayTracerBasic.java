package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * a class for basic ray tracer
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * constructor for RayTracerBasic class
     * @param scene
     */
    public RayTracerBasic(Scene scene)
    {
        super(scene);
    }

    /**
     * calculate the color with intensity of the point
     * @param point
     * @return the color of the point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

    /**
     * trace ray
     * @param ray
     * @return the color that the ray hits
     */
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if( intersections == null)
            return scene.background;
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

}
