package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for primitives.Ray class
 */
public class RayTests {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point(1,1,1), new Vector(1,1,1));
        Point p1 = new Point(-1,-1,-1);
        Point p2 = new Point(-3,0,-5);
        Point p3 = new Point(2,3,2);

        assertEquals(p3, ray.findClosestPoint(List.of(p1, p3, p2)));

        // =============== Boundary Values Tests ==================
        // TC01: empty array
        assertNull(ray.findClosestPoint(List.of()));

        // TC02: closest point is the first point in the array
        assertEquals(p3, ray.findClosestPoint(List.of(p3, p2, p1)));

        // TC03: closest point is the last point in the array
        assertEquals(p3, ray.findClosestPoint(List.of(p1, p2, p3)));

    }

     */
}
