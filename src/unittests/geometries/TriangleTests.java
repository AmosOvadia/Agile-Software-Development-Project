package unittests.geometries;


import org.junit.jupiter.api.Test;
import primitives.Point;
import static org.junit.jupiter.api.Assertions.*;
import geometries.Triangle;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Unit tests for geometries.Triangle class
 */
public class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testPointInTriangle() {
        Triangle triangle = new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(1,0,0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Point in triangle
        Point point = new Point(0.3, 0.3, 0);
        assertTrue(triangle.PointInTriangle(point), "the point is not in the triangle");

        // TC02: Point not in triangle
        point = new Point(1, 1, 0);
        assertFalse(triangle.PointInTriangle(point), "the point is in the triangle");

        // =============== Boundary Values Tests ==================
        // TC10: Point on edge
        point = new Point(0.5, 0, 0);
        assertFalse(triangle.PointInTriangle(point), "the point is in the triangle");

        // TC11: Point on vertex
        point = new Point(0, 0, 0);
        assertFalse(triangle.PointInTriangle(point), "the point is in the triangle");

        Triangle tri = new Triangle( new Point(0,7,0),new Point(2,7,0.5),new Point(1,8,4));
        Point rr = new Point(1.0,6.8,-0.18);
        assertFalse(tri.PointInTriangle(rr), "the point is in the triangle");
    }

    @Test
    void findIntsersections() {

        Triangle triangle = new Triangle(new Point(0,0,0), new Point(0,2,0), new Point(2,0,0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Point in triangle
        Ray ray = new Ray(new Point(0,0,1), new Vector(1,1,-5));
        assertEquals(List.of(new Point(0.2, 0.2, 0)), triangle.findIntersections(ray), "the point is not in the triangle");

        // TC02: point is outside against edge
        ray = new Ray(new Point(0,0,1), new Vector(1,-1,0));
        assertNull(triangle.findIntersections(ray), "the point is in the triangle");

        // TC03: point is outside against vertex
        ray = new Ray(new Point(0,0,1), new Vector(-1,-1,0));
        assertNull(triangle.findIntersections(ray), "the point is in the triangle");

        // =============== Boundary Values Tests ==================
        // TC10: point is on vertex
        ray = new Ray(new Point(0,1,1), new Vector(0,1,-1));
        assertNull(triangle.findIntersections(ray), "the point is in the triangle");

        // TC11: point is on edge
        ray = new Ray(new Point(0,1,1), new Vector(1,0,-1));
        assertNull(triangle.findIntersections(ray), "the point is in the triangle");

        // TC12: point is on edge's continuation
        ray = new Ray(new Point(0,-1,0), new Vector(0,-1,0));
        assertNull(triangle.findIntersections(ray), "the point is in the triangle");


        Triangle tri = new Triangle( new Point(0,7,0),new Point(2,7,0.5),new Point(1,8,4));
        Ray rr = new Ray(new Point(1,1,1), new Vector(0,5,-1));
        assertNull(tri.findIntersections(rr), "the point is in the triangle");


    }
}
