package unittests.geometries;

import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;



import geometries.Plane;
import static org.junit.jupiter.api.Assertions.*;




import static primitives.Util.isZero;


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
       assertTrue(result1.equals(expected1, 0.1) || result1.equals(expected2, 0.1));


    }
}
