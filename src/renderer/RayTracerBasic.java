package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * a class for basic ray tracer
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * constructor for RayTracerBasic class
     * @param scene scene
     */
    public RayTracerBasic(Scene scene)
    {
        super(scene);
    }

    /**
     * calculate the color with intensity of the point
     * @param intersection the point
     * @param ray the ray
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection,ray));//geoPoint.geometry.getEmission());
    }
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, Math.abs(nl))),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * calculate the specular of the point
     * @param material the material of the geometry
     * @param n the normal of the geometry
     * @param l the vector from the light source to the point
     * @param nl the dot product of n and l
     * @param v the vector of the ray
     * @return Double3 the specular of the point
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        double num = 0 ;
        try {
            Vector r = l.subtract(n.scale(nl*2));
            num = Math.max(0,v.dotProduct(r)*(-1));
            double totalNum = Math.pow(num,material.nShininess);
            Double3 d3 = material.kS.scale(totalNum);
            if(d3.lowerThan(Double3.ZERO))
                throw new IllegalArgumentException("less then zero");
            return d3;

        }
      catch (IllegalArgumentException ex)
      {
          return Double3.ZERO;
      }
    }

    /**
     * calculate the diffusive of the point
     * @param material the material of the geometry
     * @param nl the dot product of n and l
     * @return Double3 the diffusive of the point
     */
    private Double3 calcDiffusive(Material material, double nl) {

        try {
             Double3 d3 = material.kD.scale(nl);
             if(d3.lowerThan(Double3.ZERO))
                 throw new IllegalArgumentException("less then zero");
             return d3;
        }
       catch (IllegalArgumentException ex)
       {
           return Double3.ZERO;
       }
    }

    /**
     * trace ray
     * @param ray the ray
     * @return the color that the ray hits
     */
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if( intersections == null)
            return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray);
    }

}
