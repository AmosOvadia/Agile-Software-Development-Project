package unittests.renderer;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class DOFtest {

    private Intersectable sphere1     = new Sphere(new Point(-100, 0, 200), 30d)                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private Intersectable sphere2     = new Sphere(new Point(0, 0, -300), 30d)                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private Intersectable sphere3     = new Sphere(new Point(100, 0, -900), 30d)                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private Geometry triangle = new Triangle(new Point(0,70,-300), new Point(-50,0,-300), new Point(50,0,-300))
            .setEmission(new Color(BLUE))
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));

    private Scene scene      = new Scene("Test scene").setBackground(new Color(35, 106, 135));
    private Camera camera     = new Camera(new Point(0, 0, 700), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(500)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    private Scene scene1      = new Scene("Test scene 1").setBackground(new Color(35, 106, 135));
    private Camera camera1     = new Camera(new Point(0, 0, 700), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(500)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    /** Helper function for the tests in this module */
    void sphereTriangleHelper(String pictName, Point spotLocation) {
        scene.geometries.add(sphere1,sphere2, sphere3);

        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
                .setLense(1000,50)
                .setRaySampler(100)
                .renderImage() //
                .writeToImage();
    }
    void sphereTriangleHelper1(String pictName, Point spotLocation) {
    }

    /** Produce a picture of a sphere and triangle with point light and shade */
    @Test
    public void testThreeSpheresDOF() {
        sphereTriangleHelper("DOF", //
                new Point(-100, -100, 200));
    }

    /** Produce a picture of a sphere and triangle with point light and shade
    @Test
    public void testTriangleDOF() {

        scene1.geometries.add(triangle);

        scene1.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        camera1.setImageWriter(new ImageWriter("DOF2", 400, 400)) //
                //.setLense(1000,100)
                //.setRaySampler(49)
                .renderImage() //
                .writeToImage();
    }*/

    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1, 1, 1))) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        // right
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("dof2", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene))
                .setRaySampler(100)
                .setLense(50,30);

        camera.renderImage();
        camera.printGrid(100, new Color(YELLOW));
        camera.writeToImage();
    }
}
