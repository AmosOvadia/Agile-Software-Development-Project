package unittests.IntegrationTests;
import  renderer.Camera;
import primitives.*;
import geometries.*;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CameraRaysIntersectionTests {
    Camera camera = new Camera(new Point(1,1,1),new Vector(0,1,0),new Vector(0,0,1));

    List<Ray> getRayList(int nx, int ny)
    {
        List<Ray> list = new LinkedList<>();
        for (int j = 0; j < ny; j++)
        {
            for (int i = 0; i < nx; i++)
            {
                list.add(camera.constructRay(3,3,j,i));
            }
        }
        return list;
    }
    int getSumIntersections(Intersectable shape, List<Ray> list)
    {
        int sum = 0;
        for (var i : list) {
            if(shape.findIntersections(i) != null) {
                sum += shape.findIntersections(i).size();
            }
        }
        return sum;
    }
    @Test
    void testCameraTriangleIntersections(){
      //  Camera camera = new Camera(new Point(1,1,1),new Vector(0,1,0),new Vector(1,0,0));
        camera.setVPSize(3,3);
        camera.setVPDistance(5);

        Triangle triangle = new Triangle( new Point(0,7,0),new Point(2,7,0.5),new Point(1,8,4));

        List<Ray> list = getRayList(3,3);
        int sum = getSumIntersections(triangle, list);
        assertEquals(2,sum,"The amount of cuts is incorrect");
    }
    @Test
    void testCameraSphereIntersections(){
        camera.setVPSize(3,3);
        camera.setVPDistance(5);

        Sphere sphere = new Sphere(new Point(2,9,2), 2);

        List<Ray> list = getRayList(3,3);

        int sum = getSumIntersections(sphere, list);

        assertEquals(8,sum,"The amount of cuts is incorrect");
    }
    @Test
    void testCameraPlaneIntersections(){
        camera.setVPSize(3,3);
        camera.setVPDistance(5);

        Plane plane = new Plane(new Point(0,7,0),new Point(2,7,0.5),new Point(1,8,4) );


        List<Ray> list = getRayList(3,3);

        int sum = getSumIntersections(plane,list);

        assertEquals(9,sum,"The amount of cuts is incorrect");
    }
}
