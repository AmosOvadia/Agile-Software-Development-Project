package unittests.geometries;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {

    /**
     * Test method for the getNormal method in geometries.Sphere
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad

        Sphere sphere = new Sphere(new Point(1,1,1),5);
        Point pos = new Point(1,1,6);
        Vector expacted = new Vector(0,0,1);
          assertEquals(expacted,sphere.getNormal(pos));
    }
}