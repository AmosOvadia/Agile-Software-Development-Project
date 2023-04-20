package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> geometries;
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
    public List<Point> findIntersections(Ray ray)
    {
        boolean flag = false;
        for (Intersectable obj : geometries) {
            flag = obj.findIntersections(ray) != null;
            if(flag)
                break;
        }
        if(!flag)
            return null;

        List<primitives.Point> points = new ArrayList<>();
        for (Intersectable obj : geometries) {
            if(obj.findIntersections(ray) != null)
                points.addAll(obj.findIntersections(ray));
        }
        return points;
    }
}
