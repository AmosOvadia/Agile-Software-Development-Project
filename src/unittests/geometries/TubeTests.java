package unittests.geometries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 */
public class TubeTests {
    /**
     * Test method for the getNormal method in geometries.Tube
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a point on the tube


        // Define the ray
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 0));

        // Create a tube object with radius 2 and the defined ray
        Tube tube = new Tube(ray, 2);

        // Create two expected normal vectors
        Vector expected1 = new Vector(0, 0, 1);
        Vector expected2 = new Vector(0, 0, -1);

        // Test if the normal at point (2, 2, 2) is either the first or the second expected normal vector
        assertTrue((expected1.equals(tube.getNormal(new Point(2, 2, 2)), 0.00001) ||
                expected2.equals(tube.getNormal(new Point(2, 2, 2)), 0.00001)));

        // ================== Boundary Values Tests ==================
        // TC10: Test for a point parallel to the tubes point of the ray

        assertTrue((expected1.equals(tube.getNormal(new Point(0, 0, 2)), 0.00001) ||
                expected2.equals(tube.getNormal(new Point(0, 0, 2)), 0.00001)));
    }

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============

        // **** Group: Ray has intersections
        // TC01: two intersections, ray is not parallel nor orthogonal to the tube
        Tube tube = new Tube(new Ray(new Point(1, 1, 1), new Vector(1, -1, 2)), 2);
        Ray ray = new Ray(new Point(-1, -1, -1), new Vector(1, 1, 0.5));
        List<Point> expectedPoints = List.of(new Point(-0.2490598180669, -0.2490598180669, -0.6245299090334), new Point(2.409059818067, 2.409059818067, 0.7045299090334));
        List<Point> result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(expectedPoints, result, "Ray crosses sphere");

        // TC02: ray is orthogonal to the tube
        tube = new Tube(new Ray(new Point(0, 1, 1), new Vector(1, -1, 2)), 2);
        ray = new Ray(new Point(-3, -1, 2), new Vector(1, 1, 0));
        expectedPoints = List.of(new Point(-1.654700538379, 0.3452994616207, 2), new Point(0.6547005383793, 2.654700538379, 2));
        result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(expectedPoints, result, "Ray crosses sphere");

        // TC04: ray starts inside the tube
        tube = new Tube(new Ray(new Point(5, 1, 0), new Vector(1, 2, 0)), 2);
        ray = new Ray(new Point(0, -6, 1), new Vector(0, 0.5, 1));
        expectedPoints = List.of(new Point(0, -5.801163617271, 1.397672765459));
        result = tube.findIntersections(ray);
        assertEquals(1, result.size(), "Ray does not cross sphere");
        assertEquals(expectedPoints, result, "Ray does not cross sphere");

        // TC05: ray starts inside the tube perpendicular to the tube's vector
        tube = new Tube(new Ray(new Point(5, 1, 0), new Vector(1, 2, 0)), 2);
        ray = new Ray(new Point(0, -6, 1), new Vector(-2, 1, 0));
        expectedPoints = List.of(new Point(-0.3491933384830, -5.825403330759, 1));
        result = tube.findIntersections(ray);
        assertEquals(1, result.size(), "Ray does not cross sphere");
        assertEquals(expectedPoints, result, "Ray does not cross sphere");


        // **** Group: Ray does not intersect
        // TC06: no intersections and ray not perpendicular nor parallel
        ray = new Ray(new Point(0, 6, 1), new Vector(-2, 3, 1));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");

        // TC03: no intersections and ray is orthogonal to the tube
        ray = new Ray(new Point(-3, -1, 5), new Vector(1, 1, 0));
        assertNull(tube.findIntersections(ray), "Ray does not cross sphere");

        // TC07: no intersections and ray parallel outside the tube
        ray = new Ray(new Point(0, 6, 1), new Vector(2, 4, 0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");

        // TC08: no intersections and ray parallel inside the tube
        tube = new Tube(new Ray(new Point(5, 1, 0), new Vector(1, 2, 0)), 2);
        ray = new Ray(new Point(0, -6, 1), new Vector(2, 4, 0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");

        // ================== Boundary Values Tests ==================
        // **** Group: Ray's p0 same as tubes p0
        // TC09: one intersection, ray and tube have the same p0 and are not perpendicular nor parallel
        tube = new Tube(new Ray(new Point(5, 1, 0), new Vector(1, 2, 0)), 2);
        ray = new Ray(new Point(5, 1, 0), new Vector(-2, 5, 1));
        expectedPoints = List.of(new Point(4.035514355659, 3.411214110852, 0.4822428221704));
        result = tube.findIntersections(ray);
        assertEquals(1, result.size(), "Ray does not cross sphere");
        assertEquals(expectedPoints, result, "Ray does not cross sphere");

        // TC10: one intersection, ray and tubes vectors are orthogonal and have the same p0
        tube = new Tube(new Ray(new Point(5, 1, 0), new Vector(1, 2, 1)), 3);
        ray = new Ray(new Point(5, 1, 0), new Vector(2, -4, 6));
        expectedPoints = List.of(new Point(5.801783725737, -0.6035674514745, 2.405351177212));
        result = tube.findIntersections(ray);
        assertEquals(1, result.size(), "Ray does not cross sphere");
        assertEquals(expectedPoints, result, "Ray does not cross sphere");

        // TC11: no intersections, ray has the same point and the same vector;

        // **** Group: Ray's vector goes through tubes p0
        // TC12: two intersections, ray goes through the tubes p0
        ray = new Ray(new Point(7,-6,3), new Vector(-2,7,-3));
        result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }
        expectedPoints = List.of(new Point(4.138450209659, 4.015424266195, -1.292324685512), new Point(5.861549790341, -2.015424266195, 1.292324685512));
        assertEquals(expectedPoints, result, "Ray crosses tube");

        // TC13: two intersections, ray goes through the tubes p0 and ray orthogonal to tube's vector
        tube = new Tube(new Ray(new Point(5, 1, 0), new Vector(1, 0, 0)), 3);
        ray = new Ray(new Point(5,-6,0), new Vector(0,1,0));
        result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        expectedPoints = List.of(new Point(5, -2, 0), new Point(5, 4, 0));

        // TC14: no intersections, ray goes through the tubes p0 and ray parallel to tube's vector
        ray = new Ray(new Point(-5,1,0), new Vector(3,0,0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");

        // TC15: one intersection, ray goes through the tubes p0 and starts inside the tube
        ray = new Ray(new Point(1,0,2), new Vector(4,1,-2));
        result = tube.findIntersections(ray);
        expectedPoints = List.of(new Point(10.36656314600, 2.341640786500, -2.683281573000));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(expectedPoints, result, "Ray crosses tube");

        // TC16: one intersection, ray goes through the tubes p0 and starts inside the tube, and orthogonal to tube
        ray = new Ray(new Point(5,-1,0), new Vector(0,1,0));
        result = tube.findIntersections(ray);
        expectedPoints = List.of(new Point(5, 4, 0));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(expectedPoints, result, "Ray crosses tube");
        // TC17:

        // **** Group: Ray's vector goes is opposite directions to tube's vector (no intersections)
        // TC18: ray and tube have the same point and vectors are 180 from each other
        tube = new Tube(new Ray(new Point(5, 1, 0), new Vector(0, -1, 0)), 3);
        ray = new Ray(new Point(5, 1, 0), new Vector(0, -1, 0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");

        // TC19: ray inside the tube and vectors are 180 from each other
        ray = new Ray(new Point(4, -2, 1), new Vector(0, -1, 0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");
        // TC20: ray outside the tube and vectors are 180 from each other
        ray = new Ray(new Point(1,-3,1), new Vector(0, -1, 0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");

        // **** Group: Ray is on the tubes   (no intersections)
        // TC21: ray on the tube and same direction
        ray = new Ray(new Point(2,-3,0), new Vector(0,-1,0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");

        // TC22: ray on tube and opposite direction
        ray = new Ray(new Point(2,-3,0), new Vector(0,1,0));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");






        // **** Group C: bva of Δp
        // ***** sub group: Δp perpendicular to tube's vector and ray's p0 outside the tube
        // TC23: the vector of the two points(Δp) is perpendicular to tubes vector two intersections and not parallel nor orthogonal
        ray = new Ray(new Point(-1,1,0), new Vector(1,-1,0.5));
        result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        expectedPoints = List.of(new Point(2.6,-2.6,1.8), new Point(5,-5,3));
        if(result.get(0).getX() > result.get(1).getX()){
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(expectedPoints, result, "wrong intersection points");

        // TC24: the vector of the two points(Δp) is orthogonal to rays vector two intersections
        ray = new Ray(new Point(-1,1,0), new Vector(1,0,0));
        result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        expectedPoints = List.of(new Point(2,1,0), new Point(8,1,0));
        if(result.get(0).getX() > result.get(1).getX()){
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(expectedPoints, result, "wrong intersection points");

        // TC25: the vector of the two points(Δp) is orthogonal to rays vector and crosses the tube's p0
        ray = new Ray(new Point(-1,1,-1), new Vector(1,0,0));
        result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        expectedPoints = List.of(new Point(2.171572875254, 1, -1), new Point(7.828427124746,1,-1));
        if(result.get(0).getX() > result.get(1).getX()){
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(expectedPoints, result, "wrong intersection points");

        // TC25: the vector of the two points(Δp) no intersections and ray's vector not orthogonal nor parallel to tube's vector
        ray = new Ray(new Point(-1,1,-1), new Vector(1,0,10));
        assertNull(tube.findIntersections(ray), "Ray shouldnt cross the tube");
        // TC26: the vector of the two points(Δp) no intersections and ray's vector parallel to tube's vector


        // **** Group: bva of Δp, intersects once
        // TC23: the vector of the two points(Δp) is perpendicular to tubes vector

        // TC24: the vector of the two points(Δp) is orthogonal to rays vector
        // TC25: the vector of the two points(Δp) is orthogonal to both vectors
        // TC25: the vector of the two points(Δp) is orthogonal to tubes vector and on the line of the rays vector
        // TC25: the vector of the two points(Δp) is orthogonal to tubes vector and on the lUsing the debugger, set a breakpoint inine of the rays vector

        // **** Group: bva of Δp, does not intersect
        // TC23: the vector of the two points(Δp) is perpendicular to tubes vector
        // TC24: the vector of the two points(Δp) is orthogonal to rays vector
        // TC25: the vector of the two points(Δp) is orthogonal to both vectors
        // TC25: the vector of the two points(Δp) is orthogonal to tubes vector and on the line of the rays vector
        // TC25: the vector of the two points(Δp) is orthogonal to tubes vector and on the line of the rays vector



        // **** Group: intersection is on the tube (no intersections)
        // TC25: vectors not perpendicular and intersection on the tube
        // TC26: vectors perpendicular and intersection on the tube

        // **** Group: ray's p0 is on the tube
        // TC27 ray's p0 is on the tube and no intersection
        // TC28: ray's p0 is on the tube and no intersection
    }
}

