package unittests.renderer;

import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.List;

import static java.awt.Color.BLUE;

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
    private Scene scene      = new Scene("Test scene").setBackground(new Color(35, 106, 135));
    private Camera camera     = new Camera(new Point(0, 0, 700), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(500)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    /** Helper function for the tests in this module */
    void sphereTriangleHelper(String pictName, Point spotLocation) {
        scene.geometries.add(sphere1,sphere2, sphere3);

        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
                .setLense(1000,100)
                .setAmountOfRays(25)
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a sphere and triangle with point light and shade */
    @Test
    public void sphereTriangleInitial() {
        sphereTriangleHelper("DOF", //
                new Point(-100, -100, 200));
    }
}
