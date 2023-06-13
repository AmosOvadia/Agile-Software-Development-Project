package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import java.util.Collection;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * constructor for Geometries class
     * @param geometries list of geometries
     */
    public Geometries(Intersectable ... geometries)
    {
        for(Intersectable obj : geometries)
            this.geometries.add(obj);
    }

    /**
     * constructor for Geometries class
     * @param geometries list of geometries
     */
    private Geometries(List<Intersectable> geometries)
    {
        this.geometries = geometries;
    }

    /**
     * adds a list of geometries to the list
     * @param geometries1 list of geometries
     */
    public void add(Intersectable... geometries1)
    {
        geometries.addAll(Arrays.asList(geometries1));

    }

    public void add(Geometries geometries1)
    {
        geometries.addAll(geometries1.geometries);
    }

    /**
     * finds the intersected points of a ray in the list of the geometries
     * @param ray The ray to intersect with.
     * @return A list of points where the ray intersects the geometry.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
        List<GeoPoint> points = null;
        //iterates over the list of geometries and finds the intersected points
        for(Intersectable obj : geometries)
        {
            List<GeoPoint> lst = obj.findGeoIntersections(ray, maxDistance);
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

    /**
     * finds the minimum coordinates of the geometries
     * @return the minimum coordinates
     */
    @Override
    public Point getMinCoords() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        for (Intersectable geo : geometries) {
            Point geoMin = geo.getMinCoords();
            if (geoMin.getX() < minX) {
                minX = geoMin.getX();
            }
            if (geoMin.getY() < minY) {
                minY = geoMin.getY();
            }
            if (geoMin.getZ() < minZ) {
                minZ = geoMin.getZ();
            }
        }
        return new Point(minX, minY, minZ);
    }

    /**
     * finds the maximum coordinates of the geometries
     * @return the maximum coordinates
     */
    @Override
    public Point getMaxCoords()
    {
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Intersectable geo : geometries) {
            Point geoMax = geo.getMaxCoords();
            if (geoMax.getX() > maxX) {
                maxX = geoMax.getX();
            }
            if (geoMax.getY() > maxY) {
                maxY = geoMax.getY();
            }
            if (geoMax.getZ() > maxZ) {
                maxZ = geoMax.getZ();
            }
        }
        return new Point(maxX, maxY, maxZ);
    }


    public int size() {
        return geometries.size();
    }

    @Override
    public Point getCenterPoint() {
        Point min = getMinCoords();
        Point max = getMaxCoords();
        return new Point((min.getX() + max.getX()) / 2, (min.getY() + max.getY()) / 2, (min.getZ() + max.getZ()) / 2);
    }

    public Geometries splitAxisAligned(AABB box)
    {
        //not akshually left and right
        List<Intersectable> left = new ArrayList<>();
        List<Intersectable> right = new ArrayList<>();
        Point min = box.getMin();
        Point max = box.getMax();

        //this is genius

        if(max.getX() - min.getX() > max.getY() - min.getY() && max.getX() - min.getX() > max.getZ() - min.getZ())
        {
            geometries = geometries.stream().sorted(Comparator.comparingDouble(a -> a.getCenterPoint().getX())).toList();
             //geometries.sort((a, b) -> (new SortByX()).compare(a, b));
            //geometries.sort((a, b) -> (int)(a.getCenterPoint().getX() - b.getCenterPoint().getX()));
            //geometries.sort(new SortByX());
        }
        else if(max.getY() - min.getY() > max.getX() - min.getX() && max.getY() - min.getY() > max.getZ() - min.getZ())
        {
            geometries = geometries.stream().sorted(Comparator.comparingDouble(a -> a.getCenterPoint().getY())).toList();
            //geometries.sort((a, b) -> (new SortByY()).compare(a, b));
            //geometries.sort((a, b) -> (int)(a.getCenterPoint().getY() - b.getCenterPoint().getY()));
            //geometries.sort(new SortByY());
        }
        else
        {
            geometries = geometries.stream().sorted(Comparator.comparingDouble(a -> a.getCenterPoint().getZ())).toList();
            //geometries.sort((a, b) -> (new SortByZ()).compare(a, b));
            //geometries.sort((a, b) -> (int)(a.getCenterPoint().getZ() - b.getCenterPoint().getZ()));
            //geometries.sort(new SortByZ());
        }
        for (int i = 0; i < geometries.size()/2; i++) {
                left.add(geometries.get(i));
        }
        for(int i = geometries.size()/2; i < geometries.size(); i++) {
            right.add(geometries.get(i));
        }

        this.geometries = left;
        return new Geometries(right);
    }
}

class SortByX implements Comparator<Intersectable>
{
    public int compare(Intersectable a, Intersectable b)
    {
        return (int)(a.getCenterPoint().getX() - b.getCenterPoint().getX());
    }
}

class SortByY implements Comparator<Intersectable>
{
    public int compare(Intersectable a, Intersectable b)
    {
        return (int)(a.getCenterPoint().getY() - b.getCenterPoint().getY());
    }
}

class SortByZ implements Comparator<Intersectable>
{
    public int compare(Intersectable a, Intersectable b)
    {
        return (int)(a.getCenterPoint().getZ() - b.getCenterPoint().getZ());
    }
}