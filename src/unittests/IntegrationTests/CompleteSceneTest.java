package unittests.IntegrationTests;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class CompleteSceneTest {

    private Geometry[] room = {
            new Polygon(new Point(-100, -100, -1000), new Point(-100, 100, -1000), new Point(100, 100, -1000), new Point(100, -100, -1000)),
            new Polygon(new Point(-100, -100, -1000), new Point(-100, -100, 0), new Point(100, -100, 0), new Point(100, -100, -1000)),
            new Polygon(new Point(-100, 100, -1000), new Point(-100, 100, 0), new Point(100, 100, 0), new Point(100, 100, -1000)),
            new Polygon(new Point(100, -100, -1000), new Point(100, -100, 0), new Point(100, 100, 0), new Point(100, 100, -1000)),

            new Polygon(new Point(-100, -100, -1000), new Point(-100, -100, -650), new Point(-100, 100, -650), new Point(-100, 100, -1000)),
            new Polygon(new Point(-100, -100, -650), new Point(-100, -100, -350), new Point(-100, -50, -350), new Point(-100, -50, -650)),
            new Polygon(new Point(-100, 50, -650), new Point(-100, 50, -350), new Point(-100, 100, -350), new Point(-100, 100, -650)),
            new Polygon(new Point(-100, -100, 0), new Point(-100, -100, -350), new Point(-100, 100, -350), new Point(-100, 100, 0))

    };

    private Geometry[] table = {

            /*table surface*/
            new Polygon(new Point (-99,-40,-999), new Point(-99,-40,-700), new Point(0,-40,-700), new Point(0,-40,-999)),

            new Polygon(new Point(-99,-45,-999), new Point(-99,-45,-700), new Point(0,-45,-700), new Point(0,-45,-999)),
            /*table sides*/
            new Polygon(new Point(-99,-40,-999), new Point(-99,-45,-999), new Point(-99,-45,-700), new Point(-99,-40,-700)),
            new Polygon(new Point(0,-40,-999), new Point(0,-45,-999), new Point(0,-45,-700), new Point(0,-40,-700)),
            new Polygon(new Point(-99,-40,-999), new Point(-99,-45,-999), new Point(0,-45,-999), new Point(0,-40,-999)),
            new Polygon(new Point(-99,-40,-700), new Point(-99,-45,-700), new Point(0,-45,-700), new Point(0,-40,-700)),

            /*legs*/
            new Polygon(new Point(-95,-43,-995), new Point(-95,-100,-995), new Point(-90,-100,-995), new Point(-90,-43,-995)),
            new Polygon(new Point(-95,-43,-705), new Point(-95,-100,-705), new Point(-90,-100,-705), new Point(-90,-43,-705)),
            new Polygon(new Point(-5,-43,-995), new Point(-5,-100,-995), new Point(-10,-100,-995), new Point(-10,-43,-995)),
            new Polygon(new Point(-5,-43,-705), new Point(-5,-100,-705), new Point(-10,-100,-705), new Point(-10,-43,-705))
    };
    private Scene scene      = new Scene("Test scene").setBackground(new Color(135, 206, 235));
    private Camera camera     = new Camera(new Point(0, 0, 700), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(1000)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    Geometries setObjectsHelper()
    {
        Geometries geometries = new Geometries();
        for(int i = 0; i < 6 ; i++)
        {
            int j = i*60 -650;
            geometries.add(new Polygon(new Point(-100, 50, j), new Point(-100, 50, j + 20), new Point(-100, -50,  j + 20), new Point(-100, -50, j))
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));
        }
        for (Geometry i : room) {
            i.setMaterial(new Material().setKd(0.8).setKs(1).setShininess(10)).setEmission(new Color(10,10,10));
            geometries.add(i);
        }
        for (Geometry i : table) {
            i.setMaterial(new Material().setKd(0.8).setKs(1).setShininess(50)).setEmission(new Color(164,116,73));
            scene.geometries.add(i);
        }
        geometries.add(new Sphere(new Point(0, 90, -500),7d)
                        .setEmission(new Color(WHITE))
        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0.25)));

        //add mirror
        geometries.add(
        new Polygon(new Point(99, 40, -750), new Point(99, 40, -350), new Point(99, -100, -350), new Point(99, -100, -750))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKr(1)));

        geometries.add(
                new Sphere(new Point(60,-75,-600),25)
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));
        return geometries;
    }

    /** Helper function for the tests in this module */
    void sphereTriangleHelper(String pictName, Point spotLocation) {

        scene.lights.add( //
        //new PointLight(new Color(253,251,211), spotLocation) //
        new PointLight(new Color(223,251,211), spotLocation) //
                        .setKl(1E-5).setKq(1.5E-7));

        scene.lights.add( //
                new DirectionalLight(new Color(253,251,211), new Vector(3,-1,0)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE),0.2));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a sphere and triangle with point light and shade */
    @Test
    public void TestFullRoom() {

        long startTime = System.currentTimeMillis();

        scene.geometries.add(setObjectsHelper());
        sphereTriangleHelper("fullRoom", //
                new Point(0, 90, -500));
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total render time: " + totalTime / 1000.0 + " seconds");
    }

    @Test
    public void TestFullRoomWithAcceleration() {
        long startTime = System.currentTimeMillis();


        scene.setBvh(setObjectsHelper());

        long endFirstTime = System.currentTimeMillis();
        long firstTime = endFirstTime - startTime;
        System.out.println("BVH build time: " + firstTime/1000.0 + " seconds");

        //camera.setMultithreading(2).setDebugPrint(0.3);
        sphereTriangleHelper("fullRoomWithAcceleration", //
                new Point(0, 90, -500));
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total render time: " + totalTime / 1000.0 + " seconds");
    }

}
