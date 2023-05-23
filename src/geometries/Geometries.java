package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * geometries class for encapsulating multiple geometric objects
 */
public class Geometries extends Intersectable {

    private List<Intersectable> geometries;

    /**
     * constructor for Geometries class
     */
    public Geometries() {
        geometries = new LinkedList<>();
    }
    public Geometries (Intersectable ... geometries)
    {

    }

    /**
     * adds a list of geometries to the list
     * @param geometries1
     */
    public void add(Intersectable... geometries1)
    {
        geometries.addAll(Arrays.asList(geometries1));

    }

    /**
     * finds the intersected points of a ray in the list of the geometries
     * @param ray The ray to intersect with.
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
        List<GeoPoint> points = null;
        //iterates over the list of geometries and finds the intersected points
        for(Intersectable obj : geometries)
        {
            List<GeoPoint> lst = filterIntersections(obj.findGeoIntersections(ray),ray,maxDistance);
            if(lst != null)//if there are intersected points we add them to the list
            {
                if(points == null)//if the list is empty we create a new list
                    points = new ArrayList<>();
                //filtering only intersections that are closer to the point of the ray than the max distance
                //points.addAll( lst.stream().filter(gp -> alignZero(gp.point.distance(ray.getP0()) - maxDistance) <= 0).toList());
                points.addAll(lst);
            }
        }
        return points;
    }
}
