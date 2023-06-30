package scene;

import geometries.AABB;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Node class for Bounding Volume Hierarchy
 */
class Node {
    private Node left;
    private Node right;
    private AABB box;
    private Geometries geometries;

    /**
     * constructor for Node class
     * @param left left node
     * @param right right node
     * @param box bounding box
     */
    public Node(Node left, Node right, AABB box)
    {
        this.left = left;
        this.right = right;
        this.box = box;
    }

    /**
     * constructor for a leaf node
     * @param box  bounding box
     * @param geometries list of geometries
     */
    public Node(AABB box, Geometries geometries)
    {
        this.box = box;
        this.geometries = geometries;
    }

    /**
     * checks if the node is a leaf
     * @return true if the node is a leaf, false otherwise
     */
    public boolean isLeaf()
    {
        return geometries != null;
    }

    /**
     * getter for left node
     * @return left node
     */
    public Node getLeft() {
        return left;
    }

    /**
     * getter for right node
     * @return right node
     */
    public Node getRight() {
        return right;
    }

    /**
     * getter for bounding box
     * @return bounding box
     */
    public AABB getBox() {
        return box;
    }

    /**
     * getter for geometries
     * @return geometries
     */
    public Geometries getGeometries() {
        return geometries;
    }

}

/**
 * Bounding Volume Hierarchy
 */
public class Bvh {

    private Node root;

    /**
     * constructor for Bvh class
     * @param geometries list of geometries
     */
    public Bvh(Geometries geometries)
    {
        root = buildTree(geometries);
    }

    /**
     * builds the tree recursively
     * @param geometries list of geometries
     * @return root node
     */
    private Node buildTree(Geometries geometries)
    {
        AABB box = new AABB(geometries.getMinCoords(), geometries.getMaxCoords());
        if(geometries.size() <= 4)
        {
            return new Node(box, geometries);
        }

        Geometries newGeometries = geometries.splitAxisAligned(box);
        return new Node(buildTree(geometries), buildTree(newGeometries), box);
    }

    /**
     * returns the intersected geometries
     * @param ray ray
     * @return list of intersected geometries
     */
    public Geometries getIntersectedGeometries(Ray ray)
    {
        return getIntersectedGeometries(ray, root);
    }


    /*
    public Geometries getIntersectedGeometries(Ray ray)
    {
        if(root.isLeaf())
        {
            return root.getGeometries();
        }

        if(root.getBox().intersects(ray))
        {
            return getIntersectedGeometries(ray, root);
        }
        return null;
    }

     */

    /**
     * returns the intersected geometries
     * @param ray ray
     * @param node node
     * @return list of intersected geometries
     */
    private Geometries getIntersectedGeometries(Ray ray, Node node)
    {
        if(node.isLeaf())
        {
            return (node.getGeometries());
            //return geometries;
        }

        Geometries geometries1 = new Geometries();
        if(node.getLeft() != null && node.getLeft().getBox().intersects(ray))
        {
            Geometries geoLeft = getIntersectedGeometries(ray, node.getLeft());
            if(geoLeft != null && geoLeft.size() > 0)
                geometries1.add(geoLeft);
        }
        if(node.getRight() != null && node.getRight().getBox().intersects(ray))
        {
            Geometries geoRight = getIntersectedGeometries(ray, node.getRight());
            if(geoRight != null && geoRight.size() > 0)
                geometries1.add(geoRight);
        }
        return geometries1;
    }
}
