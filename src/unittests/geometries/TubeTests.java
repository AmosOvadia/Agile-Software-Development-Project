package unittests.geometries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Tube class
 */
public class TubeTests {
    /**
     * Test method for the getNormal method in geometries.Tube
     */
    @Test
    void testGetNormal() {
        Tube tube = new Tube(new Ray(new Point(0,0,0),new Vector(1,1,0)),2);
        Point pos = new Point(4,4,1);

        Vector expected = new Vector(-0.696311,-0.696311,0.174078);
        assertTrue( expected.equals(tube.getNormal(pos), 0.001));





    }
}
