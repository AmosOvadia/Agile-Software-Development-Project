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
        List<Point> points = null;
        //iterates over the list of geometries and finds the intersected points
        for(Intersectable obj : geometries)
        {
            List<Point> lst = obj.findIntersections(ray);
            if(lst != null)//if there are intersected points we add them to the list
            {
                if(points == null)//if the list is empty we create a new list
                    points = new ArrayList<>();
                points.addAll(lst);
            }
        }
        return points;
    }
}
