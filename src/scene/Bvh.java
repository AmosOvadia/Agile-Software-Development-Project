package scene;

import geometries.AABB;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

class Node {
    private Node left;
    private Node right;
    private AABB box;
    private Geometries geometries;

    public Node(Node left, Node right, AABB box)
    {
        this.left = left;
        this.right = right;
        this.box = box;
    }

    public Node(AABB box, Geometries geometries)
    {
        this.box = box;
        this.geometries = geometries;
    }

    public boolean isLeaf()
    {
        return geometries != null;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public AABB getBox() {
        return box;
    }

    public Geometries getGeometries() {
        return geometries;
    }
}

public class Bvh {

    private Node root;

    public Bvh(Geometries geometries)
    {
        root = buildTree(geometries);
    }

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
