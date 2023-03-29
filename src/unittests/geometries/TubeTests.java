package unittests.geometries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
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
        Ray ray = new Ray(new Point(0,0,0), new Vector(1,1,0));

        // Create a tube object with radius 2 and the defined ray
        Tube tube = new Tube(ray, 2);

        // Create two expected normal vectors
        Vector expected1 = new Vector(0, 0, 1);
        Vector expected2 = new Vector(0, 0, -1);

        // Test if the normal at point (2, 2, 2) is either the first or the second expected normal vector
        assertTrue((expected1.equals(tube.getNormal(new Point(2,2,2)), 0.00001) ||
                expected2.equals(tube.getNormal(new Point(2,2,2)), 0.00001)));
    }

}
