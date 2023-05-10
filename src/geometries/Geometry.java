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

    /**
     * sets the material of the geometry
     * @param material the material to set
     * @return Geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * setter for emission
     * @param emission color
     * @return Geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * get the light emission color
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
