package unittests.primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 */
public class VectorTests {

    /**
     * Test method for the constructor in primitives.Vector
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: correct vector creation
        try {
            new Vector(1, 1, 1);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // =============== Boundary Values Tests ==================
        // TC10: test zero vector
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "ERROR: zero vector does not throw an exception");

    }

    /**
     * Test method for the add method in primitives.Vector
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        // TC01: test add different signs
        assertEquals(new Vector(-1, -2, -3), v1.add(v2), "ERROR: Vector + Vector does not work correctly");
        // TC02: test add same vector
        assertEquals(new Vector(3, 6, 9), v1.add(new Vector(2, 4, 6)), "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: test zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)), "ERROR: Vector + itself does not throw an exception");


    }

    /**
     * Test method for the subtract method in primitives.Vector
     */
    @Test
    void testSubtract() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(2, 4, 6);
        //============ Equivalence Partitions Tests ==============
        // TC01: test subtract same signs
        assertEquals(new Vector(-1,-2,-3), v1.subtract(v3), "ERROR: Vector - Vector does not work correctly");

        // TC02: test subtract different signs
        assertEquals(new Vector(3, 6, 9), v1.subtract(v2), "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: test vector - itself
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "ERROR: Vector - itself does not throw an exception");
    }

    /**
     * Test method for the scale method in primitives.Vector
     */
    @Test
    void testScaling() {
        Vector v = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: test scale positive
        assertEquals(new Vector(2, 4, 6), v.scale(2), "ERROR: scaling a vector is does not work correctly");

        // TC02: test scale negative
        assertEquals(new Vector(-2, -4, -6), v.scale(-2), "ERROR: scaling a vector is does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC03: test scale zero
        assertThrows(IllegalArgumentException.class, () -> v.scale(0), "ERROR: scaling a vector by zero does not throw an exception");
    }

    /**
     * Test method for the crossProduct method in primitives.Vector
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // =============== Boundary Values Tests ==================
        // TC01: test vector from cross-product of co-linear vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception");

        // TC02: test length of cross-product result vector
        Vector vr = v1.crossProduct(v3);
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001,"ERROR: crossProduct() wrong result length");

        // TC03: test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)) || isZero(vr.dotProduct(v3)), "ERROR: crossProduct() result is not orthogonal to its operands");

    }
    /**
     * Test method for the lengthSquared method in primitives.Vector
     */
    @Test
    void testLengthSquared() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: test lengthSquared

        Vector v1 = new Vector(1, 2, 3);

        assertEquals(14, v1.lengthSquared(), "ERROR: lengthSquared() wrong value");

    }
    /**
     * Test method for the length method in primitives.Vector
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test length
        assertEquals(5, new Vector(0, 3, 4).length(), "ERROR: length() wrong value");

    }
    /**
     * Test method for the normalize method in primitives.Vector
     */
    @Test
    void testNormalize() {

        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // ============ Equivalence Partitions Tests ==============
        // TC01: test normalize

        // test that normalize is a unit vector
        assertEquals(1, u.length(), "ERROR: the normalized vector is not a unit vector");

        // test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one");

        // test that the vectors are not opposite
        assertTrue(v.dotProduct(u) > 0, "ERROR: the normalized vector is opposite to the original one");
    }

    /**
     * Test method for the dotProduct method in primitives.Vector
     */
    @Test
    void testDotProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Dot-Product
        assertEquals(-28, v1.dotProduct(v2), "ERROR: dotProduct() for orthogonal vectors is not zero");

        // =============== Boundary Values Tests ==================
        // TC10: test zero
        assertEquals(0, v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");

    }
}
