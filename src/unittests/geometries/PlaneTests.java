package unittests.geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Plane;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for geometries.PlaneTests class
 */
public class PlaneTests {
    /**
     * Test method for the getNormal method in geometries.PlaneTests
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad

        Plane plane = new Plane(new Point(1, 1, 3), new Point(4, -2, -2), new Point(5, 5, 5));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(0, 0, 1)), "");

        // generate the test result
        Vector result1 = plane.getNormal(new Point(0, 8, 1));
        // ensure |result| = 1
        assertEquals(1, result1.length(), 0.00000001, "plane's normal is not a unit vector");

       Vector expected1 = new Vector(0.367912,-0.683265,0.630706);
       Vector expected2 = new Vector(-0.367912,0.683265,-0.630706);
       assertTrue(result1.equals(expected1, 0.00001) || result1.equals(expected2, 0.00001));
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntsersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects but not orthogonal to plane
        Plane plane = new Plane(new Point(0,0,0), new Vector(0,0,1));
        Ray ray = new Ray(new Point(0,0,2), new Vector(-1,-1,-1));

        assertEquals(List.of(new Point(-2,-2,0)), plane.findIntersections(ray), "Wrong intersection point");

        // TC02: ray does not intersect plane
        ray = new Ray(new Point(0,0,2), new Vector(1,1,1));
        assertNull(plane.findIntersections(ray), "intersection points should be null");

        // =============== Boundary Values Tests ==================
        // TC03: ray is parallel to plane
        ray = new Ray(new Point(0,1,2), new Vector(1,2,0));
        assertNull(plane.findIntersections(ray), "intersection points should be null");

        // TC04: ray is in plane
        ray = new Ray(new Point(0,8,0), new Vector(3,1,0));
        assertNull(plane.findIntersections(ray), "intersection points should be null");

        // TC05: ray is orthogonal to plane and before plane
        ray = new Ray(new Point(1,1,-2), new Vector(0,0,2));
        assertEquals(List.of(new Point(1,1,0)), plane.findIntersections(ray), "Wrong intersection point");

        // TC06: ray is orthogonal to plane and begins in plane
        ray = new Ray(new Point(1,1,0), new Vector(0,0,2));
        assertNull(plane.findIntersections(ray), "intersection points should be null");

        // TC07: ray is orthogonal to plane and after the plane
        ray = new Ray(new Point(1,1,2), new Vector(0,0,2));
        assertNull(plane.findIntersections(ray), "intersection points should be null");

        // TC08: ray begins in plane and not parallel or orthogonal to plane
        ray = new Ray(new Point(1,1,0), new Vector(1,1,1));
        assertNull(plane.findIntersections(ray), "intersection points should be null");

        // TC09: ray begins in plane and not parallel or orthogonal to plane and has the same point as the plane
        ray = new Ray(new Point(0,0,0), new Vector(1,1,1));
        assertNull(plane.findIntersections(ray), "intersection points should be null");

    }
}
