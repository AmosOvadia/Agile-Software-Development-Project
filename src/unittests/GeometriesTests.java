package unittests;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

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
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1)),
                new Triangle(new Point(0, 1, 0), new Point(1, 1, 0), new Point(0, 1, 1)),
                new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1, 1),
                new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1),
                new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 1, 0), new Point(0, 1, 0)));

    }
}