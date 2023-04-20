package unittests;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Geometries class
 */
class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Geometries geometries = new Geometries();
        // TC01: empty list
        assertNull(geometries.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 1, 0))),
                "empty list");

        // TC02: no intersection
        geometries.add(new Sphere(new Point(1, 0, 0), 1d),
                new Plane(new Point(1, 0, 0), new Point(0,1,0),new Point(0,0,1)),
                new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(0, 0, 0)),
              /*  new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1, 1),
                new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1),*/
                new Polygon( new Point(0, 0, 0),new Point(1, 0, 0),new Point(1, 1, 0), new Point(0, 1, 0)));

        assertNull(geometries.findIntersections(new Ray(new Point(4, 4, 4), new Vector(1, 1, 1))),
                "no Intersections");

        // TC03: one shape is cut out
        Ray ray = new Ray(new Point(-1, -1, 4), new Vector(-1, -1, 0));
        assertEquals(1, geometries.findIntersections(ray).size(), "");

        // TC04: part of the shapes is cut out
         ray = new Ray(new Point(0.2, -0.2, 0.4), new Vector(0, 0, 2));
        assertEquals(2, geometries.findIntersections(ray).size(), "part of the shapes is cut out");



        // TC05: All shapes are cut
        ray = new Ray(new Point(0.25, 0.25, -0.5), new Vector(0, 0, 1));
        assertEquals(4, geometries.findIntersections(ray).size(),
                "All shapes are cut");


    }
}