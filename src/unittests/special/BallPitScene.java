package unittests.special;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

import static java.awt.Color.*;
public class BallPitScene {

    public static void main(String[] args) {
        double viewPlaneDistance = 30.0; // Distance from the camera to the view plane
        double viewPlaneWidth = 20.0; // Width of the view plane
        //double aspectRatio = 16.0 / 9.0; // Aspect ratio of the rendered image (can be adjusted)
        //double viewPlaneHeight = viewPlaneWidth / aspectRatio;
        // Define camera parameters
        Point cameraLocation = new Point(0, 20, -40);
        Vector lookAtDirection = new Vector(0, -0.2, 1);
        Vector vUp = new Vector(0, 1, 0.2); // Up vector (can be adjusted)

        // Calculate vRight vector
        Camera camera = new Camera(cameraLocation, lookAtDirection, vUp);

        // Create the scene geometries
        Geometries sceneGeometries = new Geometries();

        /* Create the square pool
        Point poolCorner1 = new Point(-10, 0, -10);
        Point poolCorner2 = new Point(10, 0, -10);
        Point poolCorner3 = new Point(10, 0, 10);
        Point poolCorner4 = new Point(-10, 0, 10);
        Polygon pool = new Polygon(poolCorner1, poolCorner2, poolCorner3, poolCorner4);
        pool.setMaterial(new Material().setKs(0.5).setKs(0.5).setShininess(50)).setEmission(new Color(WHITE));
        sceneGeometries.add(pool);
         */
        Geometry[] asdf = {
                new Polygon(new Point(-20, 6, -25), new Point(-25, 6, -25), new Point(-25, 6, 35), new Point(-20, 6, 35)),
                new Polygon(new Point(-20, 6, -25), new Point(-20, 1, -25), new Point(-20, 1, 35), new Point(-20, 6, 35)),

                new Polygon(new Point(20, 6, -25), new Point(25, 6, -25), new Point(25, 6, 35), new Point(20, 6, 35)),
                new Polygon(new Point(20, 6, 35), new Point(20, 1, 35), new Point(-20, 1, 35), new Point(-20, 6, 35)),


                new Polygon(new Point(-25, 6, 35), new Point(25, 6, 35), new Point(25, 6, 40), new Point(-25, 6, 40)),
                new Polygon(new Point(20, 6, -25), new Point(20, 1, -25), new Point(20, 1, 35), new Point(20, 6, 35))
        };
        for(Geometry g : asdf)
        {
            g.setMaterial(new Material().setKs(0.5).setKs(0.5).setShininess(50)).setEmission(new Color(GRAY));
            sceneGeometries.add(g);
        }


        // Create the balls
        Random random = new Random();
        int numBalls = 2000;
        double ballRadius = 1.0;
        for (int i = 0; i < numBalls; i++) {
            // Randomly generate ball positions within the pool
            double x = random.nextDouble() * 40 - 20;
            double y = random.nextDouble() * 5 + 1;
            double z = random.nextDouble() * 60 - 25;

            // Randomly generate ball colors
            Color color;
            int numColor = random.nextInt(5);
            color = switch (numColor) {
                case 1 -> new Color(GREEN);
                case 2 -> new Color(BLUE);
                case 3 -> new Color(RED);
                case 4 -> new Color(ORANGE);
                default -> new Color(YELLOW);
            };

            // Create the ball as a sphere
            Point ballCenter = new Point(x, y, z);
            Sphere ball = new Sphere(ballCenter, ballRadius);
            ball.setMaterial(new Material() //
                    .setKd(1) // Diffuse coefficient for the ball
                    .setKs(1) // Specular coefficient for the ball
                    .setShininess(10)); // Shininess for the ball
            ball.setEmission(color);
            sceneGeometries.add(ball);
        }

        // Define the PointLight
        PointLight pointLight = new PointLight(new Color(100, 100, 100), new Point(0, 10, -10)); // Position and intensity
        DirectionalLight directionalLight = new DirectionalLight(new Color(153,151,111), new Vector(1,-0.2,1));

        Scene scene = new Scene("ballpit");
        scene.setLights(List.of(pointLight,directionalLight)).setGeometries(sceneGeometries).setBackground(new Color(50,50,100));

        scene.setBvh(sceneGeometries);
        camera.setImageWriter(new ImageWriter("ballpit", 1000, 1000))
                .setVPSize(viewPlaneWidth, viewPlaneWidth)
                .setVPDistance(viewPlaneDistance)
                .setRayTracer(new RayTracerBasic(scene));


        camera.setMultithreading(4).setDebugPrint(1);

        long startTime = System.currentTimeMillis();

        camera.renderImage().writeToImage();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total render time: " + totalTime / 1000.0 + " seconds");
    }
}
