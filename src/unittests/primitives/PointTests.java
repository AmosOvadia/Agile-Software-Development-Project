package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Point class
 */
public class PointTests {

    /**
     * Test method for the add method in primitives.Point
     */
    @Test
    void testAdd() {

        Point p1 = new Point(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: test add same signs
        assertEquals(new Point(2, 4, 6), p1.add(new Vector(1, 2, 3)), "ERROR: Point + Vector does not work correctly");
        // TC02: test add different signs
        assertEquals(new Point(0, 0, 0), p1.add(new Vector(-1, -2, -3)), "ERROR: Point + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC03: test add zero vector

        Point p2 = new Point(0, 0, 0);
        assertEquals(new Point(1, 2, 3), p2.add(new Vector(1, 2, 3)), "ERROR: Point + Vector does not work correctly");
        // TC04: test add zero point


    }

    /**
     * Test method for the subtract method in primitives.Point
     */
    @Test
    void testSubtract() {
        Point p1 = new Point(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: test subtract
        assertEquals(new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p1), "ERROR: Point - Point does not work correctly");
    }


    /**
     * Test method for the length method in primitives.Point
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test distance
        assertEquals(2, new Point(1, 0, 0).distance(new Point(3, 0, 0)), "ERROR: distance between two points does not work correctly");
        // TC02: test distance negative



        // =============== Boundary Values Tests ==================
        // TC02: test distance zero
        assertEquals(0, new Point(1, 0, 0).distance(new Point(1, 0, 0)), "ERROR: distance between two points does not work correctly");



    }

    /**
     * Test method for the lengthSquared method in primitives.Point
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test distance squared
        assertEquals(4, new Point(1, 0, 0).distanceSquared(new Point(3, 0, 0)), "ERROR: distance squared between two points does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC02: test distance squared zero
        assertEquals(0, new Point(1, 0, 0).distanceSquared(new Point(1, 0, 0)), "ERROR: distance squared between two points does not work correctly");

    }
}
