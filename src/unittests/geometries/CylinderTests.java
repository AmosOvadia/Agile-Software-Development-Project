package unittests.geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 */
public class CylinderTests {
    /**
     * Test method for the getNormal method in geometries.Cylinder
     */

    /**
     * Returns the normal to the cylinder at the specified point.
     * Throws an IllegalArgumentException if the point is not on the cylinder.
     *
     * @return the normal to the cylinder at the specified point
     * @throws IllegalArgumentException if the point is not on the cylinder
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a point on the side of the cylinder
        // Create a new cylinder with axis ray starting at (0,0,0) in the direction of y-axis, radius 2 and height 5
        Cylinder cylinder1 = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 2, 5);
        // Set the expected normal to be (1, 0, 0)
        Vector expected1 = new Vector(1, 0, 0);
        // Check if the normal vector at the point (2,3,0) on the cylinder is equal to the expected normal vector
        assertTrue(expected1.equals(cylinder1.getNormal(new Point(2, 3, 0)).scale(-1), 0.001) || expected1.equals(cylinder1.getNormal(new Point(2, 3, 0)), 0.001));

        // TC02: Test for a point on the bottom cap of the cylinder
        // Create a new cylinder with axis ray starting at (0,0,0) in the direction of y-axis, radius 2 and height 4
        Cylinder cylinder2 = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 2, 4);
        // Set the expected normal to be (0, 1, 0)
        Vector expected2 = new Vector(0, 1, 0);
        // Check if the normal vector at the point (0,0,0) on the bottom cap of the cylinder is equal to the expected normal vector
        assertTrue(expected2.equals(cylinder2.getNormal(new Point(0.1, 0, 0)).scale(-1), 0.001) || expected2.equals(cylinder2.getNormal(new Point(0.1, 0, 0)), 0.001));

        // TC03: Test for a point on the top cap of the cylinder
        // Set the expected normal to be (0, 1, 0)
        Vector expected3 = new Vector(0, 1, 0);
        // Check if the normal vector at the point (0,4,0) on the top cap of the cylinder is equal to the expected normal vector
        assertTrue(expected3.equals(cylinder2.getNormal(new Point(0.1, 4, 0)).scale(-1), 0.001) || expected3.equals(cylinder2.getNormal(new Point(0.1, 4, 0)), 0.001));

        // =============== Boundary Values Tests ==================
        // TC10: Test for a point on the side of the cylinder
        Cylinder cylinder4 = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 2, 4);
        // Set the expected normal to be (0, 1, 0)
        Vector expected4 = new Vector(0, -1, 0);
        // Check if the normal vector at the point (0,0,0) on the bottom cap of the cylinder is equal to the expected normal vector
        assertTrue(expected4.equals(cylinder4.getNormal(new Point(0, 0, 0)).scale(-1), 0.001) || expected4.equals(cylinder4.getNormal(new Point(0, 0, 0)), 0.001));

        // TC11: Test for a point on the top cap of the cylinder
        // Set the expected normal to be (0, 1, 0)
        Vector expected5 = new Vector(0, 1, 0);
        // Check if the normal vector at the point (0,4,0) on the top cap of the cylinder is equal to the expected normal vector
        assertTrue(expected5.equals(cylinder4.getNormal(new Point(0, 4, 0)).scale(-1), 0.001) || expected5.equals(cylinder4.getNormal(new Point(0, 4, 0)), 0.001));
    }

    @Test
    void testFindIntersection() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)), 1d, 2d);

        // ============ Equivalence Partitions Tests ==============

        //TC01 ray is outside and parallel to the cylinder's ray
        List<Point> result = cylinder.findIntersections(new Ray(new Point(6, 5, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        //TC02 ray starts from inside and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(1.2, 0, 1), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection points");


        //TC03 ray starts inside and parallel to the cylinder's ray
        result = cylinder.findIntersections(new Ray(new Point(2.2, 0, 1), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.2, 0, 2)), result, "Bad intersection point");


        //TC04 ray starts from outside the cylinder and doesn't cross the cylinder
        result = cylinder.findIntersections(new Ray(new Point(6, 0, 0), new Vector(1, 1, 1)));
        assertNull(result, "Wrong number of points");

        //TC05 ray starts outside and parallel to the cylinder's ray and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(2.5, 0, -8), new Vector(0, 0, 1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 0), new Point(2.5, 0, 2)), result, "Bad intersection point");

        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder
        result = cylinder.findIntersections(new Ray(new Point(-5, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");


        // =============== Boundary Values Tests ==================

        //TC07 ray is on the surface of the cylinder (not bases)
        result = cylinder.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        //TC08 ray is in center of the cylinder
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 2)), result, "Bad intersection points");

        //TC09 ray starts from the surface to inside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 1)), result, "Bad intersection point");


        //TC010 ray is perpendicular to cylinder's ray and starts from outside the tube
        result = cylinder.findIntersections(new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 1), new Point(3, 0, 1)), result, "Bad intersection points");

        //TC11 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)
        result = cylinder.findIntersections(new Ray(new Point(1.5, 0, 1), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection points");

        //TC12 ray is perpendicular to cylinder's ray and starts from the center of cylinder
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 1), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection points");

        //TC13 ray starts from the surface to inside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 1)), result, "Bad intersection point");


        //TC14 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside
        result = cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection points");

        //TC15 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        //TC16 ray starts from the surface to inside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 1)), result, "Bad intersection point");


        //TC17 ray starts from the surface to outside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 1, -2)));
        assertNull(result, "Wrong number of points");

        //TC18 ray starts from the surface to inside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 1)), result, "Bad intersection point");

        //TC19 ray starts from the center and there is one cut
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection point");

        //TC20 prolongation of ray crosses cylinder
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        //TC21 ray is on the surface starts before cylinder
        result = cylinder.findIntersections(new Ray(new Point(3, 0, -2), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");


        //TC22 ray starts from the surface to outside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 1)));
        assertNull(result, "Wrong number of points");


        //TC23 ray starts from the center and there is one cut
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection point");


        //TC24 ray is on the surface starts at bottom's base
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");


        //TC25 ray is on the surface starts on the surface
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(0, 0, 1.5)));
        assertNull(result, "Wrong number of points");


        //TC26 ray is on the surface starts at top's base
        result = cylinder.findIntersections(new Ray(new Point(1, 0, 2), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");
    }
}