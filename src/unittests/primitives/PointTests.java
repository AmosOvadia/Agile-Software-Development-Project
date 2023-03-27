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
        // TC01: test add different signs
        assertEquals(new Point(0, 0, 0), p1.add(new Vector(-1, -2, -3)), "ERROR: Point + Vector does not work correctly");

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

    @Test
    void distance() {
    }

    @Test
    void distanceSquared() {
    }
}
