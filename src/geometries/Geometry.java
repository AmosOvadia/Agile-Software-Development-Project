package geometries;

import primitives.*;

import java.util.List;

/**
 * interface for the representation of geometrical shapes in space
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * setter for emisson
     * @param emission
     * @return Geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * get the light emssion color
     * @return color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * gets the geometric normal of a geometric shape
     * @param P Point
     * @return Vector
     */
  public abstract   Vector getNormal(Point P);
    
}
