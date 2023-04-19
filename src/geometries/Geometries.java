package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
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

    public void add(Intersectable... geometries)
    {
    }

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
