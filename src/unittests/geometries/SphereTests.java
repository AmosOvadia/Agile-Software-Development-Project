package unittests.geometries;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {

    /**
     * Test method for the getNormal method in geometries.Sphere
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad

        Sphere sphere = new Sphere(new Point(1,1,1),5);
        Point pos = new Point(1,1,6);
        Vector expacted = new Vector(0,0,1);
        assertEquals(expacted,sphere.getNormal(pos));
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere( new Point (1, 0, 0), 1d);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)

        sphere = new Sphere( new Point (1, 0, 0), 2);
        Ray ray = new Ray(new Point(0,0,1), new Vector(1,1,-0.5));
        result = sphere.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        Point intersection = new Point(1.821367205045918,1.821367205045918,0.08931639747704101);
        assertEquals(intersection, result.get(0), "wrong intersection point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(9, 3, 1), new Vector(-1, 0, 0))),
                "Ray should not intersect the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point(3,0,0), new Vector(-1,-0.5,0.5));
        result = sphere.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        intersection = new Point(0.3333333333333326, -1.3333333333333337, 1.3333333333333337);
        assertEquals(intersection, result.get(0), "wrong intersection point");

        // TC12: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(3,0,0), new Vector(1,-0.5,0.5));
        assertNull(sphere.findIntersections(ray), "Ray should not intersect the sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        ray = new Ray(new Point(4,0,0), new Vector(-1,0,0));
        result = sphere.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        List<Point> expected = List.of(new Point(3,0,0), new Point(-1,0,0));
        if (result.get(0).getX() < result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(expected, result, "wrong intersection points");

        // TC14: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point(3,0,0), new Vector(-1,0,0));
        result = sphere.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-1,0,0), result.get(0), "wrong intersection point");

        // TC15: Ray starts inside (1 points)
         ray = new Ray(new Point(0,0,0), new Vector(-1,0,0));
        result = sphere.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-1,0,0), result.get(0), "wrong intersection point");

        // TC16: Ray starts at the center (1 points)
        ray = new Ray(new Point(1,0,0), new Vector(-1,0,0));
        result = sphere.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-1,0,0), result.get(0), "wrong intersection point");

        // TC17: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(-1,0,0), new Vector(-1,0,0));
        result = sphere.findIntersections(ray);
        assertNull(result, "Ray should not intersect the sphere");

        // TC18: Ray starts after sphere (0 points)
        ray = new Ray(new Point(-2,0,0), new Vector(-1,0,0));
        result = sphere.findIntersections(ray);
        assertNull(result, "Ray should not intersect the sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        ray = new Ray(new Point(-1,-1,0), new Vector(0,1,0));
        result = sphere.findIntersections(ray);
        assertNull(result, "Ray should not intersect the sphere");

        // TC20: Ray starts at the tangent point
        ray = new Ray(new Point(-1,0,0), new Vector(0,1,0));
        result = sphere.findIntersections(ray);
        assertNull(result, "Ray should not intersect the sphere");

        // TC21: Ray starts after the tangent point
        ray = new Ray(new Point(-1,1,0), new Vector(0,1,0));
        result = sphere.findIntersections(ray);
        assertNull(result, "Ray should not intersect the sphere");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        ray = new Ray(new Point(-2,0,0), new Vector(0,1,0));
        result = sphere.findIntersections(ray);
        assertNull(result, "Ray should not intersect the sphere");

    }
}
