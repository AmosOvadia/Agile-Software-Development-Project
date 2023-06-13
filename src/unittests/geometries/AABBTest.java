package unittests.geometries;

import geometries.AABB;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class AABBTest {

    @Test
    void ConstructorTest()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: min point is smaller than max point
        Point min = new Point(1,1,1);
        Point max = new Point(2,2,2);
        assertDoesNotThrow(() -> new AABB(min, max), "min point is smaller than max point");

        // TC02: min point is bigger than max point
        assertThrows(IllegalArgumentException.class, () -> new AABB(max, min), "min point is bigger than max point");
    }

    @Test
    void intersects() {
        // ============ Equivalence Partitions Tests ==============
        // EPT 2D
        // TC01: Ray intersects the box
        AABB box = new AABB(new Point(7,1,0), new Point(9,3,0));
        Ray ray = new Ray(new Point(0,-1,0), new Vector(2,1,0));

        assertTrue(box.intersects(ray), "Ray intersects the box");

        // TC02: Ray doesn't intersect the box
        ray = new Ray(new Point(0,-1,0), new Vector(1,1,0));
        assertFalse(box.intersects(ray), "Ray doesn't intersect the box");

        // TC03: Ray starts inside the box
        ray = new Ray(new Point(8,2,0), new Vector(1,1,0));
        assertTrue(box.intersects(ray), "Ray starts inside the box");

        // EPT 3D
        // TC04: Ray intersects the box
        box = new AABB(new Point(4,1,0), new Point(9,5,2));
        ray = new Ray(new Point(0,-1,-11), new Vector(1,1,3));
        assertTrue(box.intersects(ray), "Ray intersects the box");

        // TC05: Ray doesn't intersect the box
        ray = new Ray(new Point(0,-1,-11), new Vector(1,1,1));
        assertFalse(box.intersects(ray), "Ray doesn't intersect the box");

        // TC06: Ray starts inside the box

        ray = new Ray(new Point(5,2,1), new Vector(1,1,1));
        assertTrue(box.intersects(ray), "Ray starts inside the box");

    }

}