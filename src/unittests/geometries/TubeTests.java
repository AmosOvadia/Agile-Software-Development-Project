package unittests.geometries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        Ray ray = new Ray(new Point(0,0,0), new Vector(1,1,0));

        // Create a tube object with radius 2 and the defined ray
        Tube tube = new Tube(ray, 2);

        // Create two expected normal vectors
        Vector expected1 = new Vector(0, 0, 1);
        Vector expected2 = new Vector(0, 0, -1);

        // Test if the normal at point (2, 2, 2) is either the first or the second expected normal vector
        assertTrue((expected1.equals(tube.getNormal(new Point(2,2,2)), 0.00001) ||
                expected2.equals(tube.getNormal(new Point(2,2,2)), 0.00001)));

        // ================== Boundary Values Tests ==================
        // TC10: Test for a point parallel to the tubes point of the ray

        assertTrue((expected1.equals(tube.getNormal(new Point(0,0,2)), 0.00001) ||
                expected2.equals(tube.getNormal(new Point(0,0,2)), 0.00001)));
    }

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: two intersections, ray is not parallel nor orthogonal to the tube
        Tube tube = new Tube(new Ray(new Point(1,1,1), new Vector(1,-1,2)),2);
        Ray ray = new Ray(new Point(-1,-1,-1), new Vector(1,1,0.5));
        List<Point> expectedPoints = List.of(new Point(-0.2490598180669, -0.2490598180669, -0.6245299090334) , new Point(2.409059818067, 2.409059818067, 0.7045299090334));
        List<Point> result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        if(result.get(0).getX() > result.get(1).getX()){
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(expectedPoints, result, "Ray crosses sphere");

        // TC02: ray is orthogonal to the tube
        tube = new Tube(new Ray(new Point(0,1,1), new Vector(1,-1,2)),2);
        ray = new Ray(new Point(-3,-1,2), new Vector(1,1,0));
        expectedPoints = List.of(new Point(-1.654700538379,0.3452994616207,2) , new Point(0.6547005383793, 2.654700538379, 2));
        result = tube.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        if(result.get(0).getX() > result.get(1).getX()){
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(expectedPoints, result, "Ray crosses sphere");

        // TC03: ray is orthogonal to the tube and does not cross the tube
        tube = new Tube(new Ray(new Point(0,1,1), new Vector(1,-1,5)),2);
        assertNull(tube.findIntersections(ray), "Ray does not cross sphere");

    }

}
