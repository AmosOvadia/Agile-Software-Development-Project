package unittests.geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static primitives.Util.isZero;
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
        Cylinder cylinder1 = new Cylinder(new Ray(new Point(0,0,0), new Vector(0,1,0)), 2, 5);
        // Set the expected normal to be (1, 0, 0)
        Vector expected1 = new Vector(1, 0, 0);
        // Check if the normal vector at the point (2,3,0) on the cylinder is equal to the expected normal vector
        assertTrue(expected1.equals(cylinder1.getNormal(new Point(2,3,0)), 0.001));

        // TC02: Test for a point on the bottom cap of the cylinder
        // Create a new cylinder with axis ray starting at (0,0,0) in the direction of y-axis, radius 2 and height 4
        Cylinder cylinder2 = new Cylinder(new Ray(new Point(0,0,0), new Vector(0,1,0)), 2, 4);
        // Set the expected normal to be (0, 1, 0)
        Vector expected2 = new Vector(0, 1, 0);
        // Check if the normal vector at the point (0,0,0) on the bottom cap of the cylinder is equal to the expected normal vector
        assertTrue(expected2.equals(cylinder2.getNormal(new Point(0,0,0)), 0.001));

        // TC03: Test for a point on the top cap of the cylinder
        // Set the expected normal to be (0, -1, 0)
        Vector expected3 = new Vector(0, -1, 0);
        // Check if the normal vector at the point (0,4,0) on the top cap of the cylinder is equal to the expected normal vector
        assertTrue(expected3.equals(cylinder2.getNormal(new Point(0,4,0)), 0.001));
    }

}
