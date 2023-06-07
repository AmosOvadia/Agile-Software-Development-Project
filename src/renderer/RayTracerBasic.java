package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.*;

/**
 * a class for basic ray tracer
 */
public class RayTracerBasic extends RayTracerBase{

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * constructor for RayTracerBasic class
     * @param scene scene
     */
    public RayTracerBasic(Scene scene)
    {
        super(scene);
    }

    /**
     * function that traces a ray
     * @param gp the intersection point
     * @param ray the ray
     * @return the color of the ray
     */
    private Color calcColor(GeoPoint gp, Ray ray)
    {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE).add(scene.ambientLight.getIntensity());
    }

    /**
     * function that traces a ray
     * @param gp the intersection point
     * @param ray the ray
     * @param level the level of the recursion
     * @param k index of refraction
     * @return the color of the ray
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * function that calculates the local effects
     * @param gp the intersection point
     * @param ray the ray
     * @param k index of refraction
     * @return the color that is added
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                //if (unshaded(gp, lightSource, l, n, nl)) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.scale(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, Math.abs(nl))),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * function that calculates the global effects
     * @param gp the intersection point
     * @param ray the ray
     * @param level the level of the recursion
     * @param k index of refraction
     * @return the color that is added
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kr = material.kR, kkr = k.product(kr);
        Ray reflectedRay = constructReflectedRay(gp,v,n);
        color = color.add(calcGlobalEffect(reflectedRay, level, kkr, kr));

        Double3 kt = material.kT, kkt = k.product(kt);
        Ray refractedRay = constructRefractedRay(gp,v,n);
        color = color.add(calcGlobalEffect(refractedRay, level, kkt, kt));

        return color;
    }

    /**
     * Calculates the color contributed by global effects (reflection or refraction) for a given ray and level.
     * @param rRay   The ray to trace for global effects.
     * @param level The recursion level.
     * @param kk     The accumulated transparency factor.
     * @param kx    The transparency factor of the current surface.
     * @return The Color representing the global effects contribution.
     */
    private Color calcGlobalEffect( Ray rRay, int level, Double3 kk, Double3 kx) {
        GeoPoint rPoint = findClosestIntersection(rRay);
        if (!kk.lowerThan(MIN_CALC_COLOR_K) && rPoint != null) {
            return calcColor(rPoint, rRay, level - 1, kk).scale(kx);
        }
        return Color.BLACK;
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
        GeoPoint closestIntersection = findClosestIntersection(ray);
        return closestIntersection == null ? scene.background
                : calcColor(closestIntersection, ray);
    }

    /**
     * trace ray with shadow
     * @param gp the point
     * @param light the light source
     * @param l the vector from the light source to the point
     * @param n the normal of the geometry
     * @param nv the dot product of n and l
     * @return the color that the ray hits
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nv)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        if(intersections == null)
            return true;
        for(GeoPoint geoPoint : intersections)
        {
            if(geoPoint.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K))// || geoPoint.geometry.getMaterial().kT == Double3.ZERO)
                return false;
        }
        return true;
    }

    /**
     * calculates the transparency of the point
     * @param gp the point
     * @param ls the light source
     * @param l the vector from the light source to the point
     * @param n the normal of the geometry at the point
     * @return the transparency of the point
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n)
    {
        if(gp.geometry.getMaterial().kT == Double3.ZERO)
            return Double3.ZERO;
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(gp.point));
        if (intersections == null)
            return Double3.ONE;
        Double3 ktr = Double3.ONE;
        //loop over intersections and for each intersection which is closer to the
        //point than the light source multiply ktr by 𝒌𝑻 of its geometry
        for(GeoPoint geoPoint : intersections)
        {
            ktr = ktr.scale(geoPoint.geometry.getMaterial().kT);
            if(ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;
    }

    /**
     * construct the reflected ray
     * @param gp the point
     * @param v the vector of the ray
     * @param n the normal of the geometry at the point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        //r = v – 2 * (v * n) * n
        double vn = v.dotProduct(n);
        Vector r;
        if(isZero(vn))
            return null;
        else
             r = v.subtract(n.scale(2 * vn));
        return new Ray(gp.point, r, n);
    }

    /**
     * construct the refracted ray
     * @param gp the point
     * @param v the vector of the ray
     * @param n the normal of the geometry at the point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point,v,n);
    }

    /**
     * finds the closest intersection point to the ray's head
     * @param ray the ray
     * @return the closest intersection point to the ray's head
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        for (GeoPoint geoPoint : intersections) {
            double distance = ray.getP0().distance(geoPoint.point);
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }
}
