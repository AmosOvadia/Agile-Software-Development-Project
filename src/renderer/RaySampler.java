package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * a class for sampling rays from a point
 */
class RaySampler {

    int sQrtAmountOfRays;

    /**
     * constructor for ray sampler
     * @param amountOfRays the amount of rays that will be generated
     */
    public RaySampler(int amountOfRays)
    {
        this.sQrtAmountOfRays = (int)Math.sqrt(amountOfRays);
    }

    /**
     * a getter for the amount of rays
     * @return the amount of rays
     */
    public int getSqrtAmountOfRays()
    {
        return sQrtAmountOfRays;
    }

    /**
     * a function that generates rays from a point
     * @param boxH the height of the box
     * @param boxW the width of the box
     * @param location the location of the point
     * @param vUp the up vector
     * @param vRight the right vector
     * @return a list of rays
     */
    private List<Point> getPoints(double boxH ,double boxW , Point location, Vector vUp, Vector vRight) {
        double halfBoxW = boxW/2;
        double halfBoxH = boxH/2;
        List<Point> pList = new LinkedList<>();
        Random rand = new Random();
        for(int i = 0; i < sQrtAmountOfRays; i++)
        {
            for(int j = 0; j < sQrtAmountOfRays; j ++)
            {
                double offsetX = rand.nextDouble(boxW) - halfBoxW;
                double offsetY = rand.nextDouble(boxH) - halfBoxH;
                double yi = -1.0*(i- ((double)(sQrtAmountOfRays - 1)/2))*boxH + offsetY;
                double xj = (j- ((double)(sQrtAmountOfRays - 1)/2))*boxW + offsetX;
                Point p = location;
                if(yi != 0)
                    p = p.add(vUp.scale(yi));
                if(xj != 0)
                    p = p.add(vRight.scale(xj));
                pList.add(p);
            }
        }
        return pList;
    }

    /**
     * a function that generates rays from a point to a focal point
     * @param focalPoint the focal point
     * @param boxSize the size of the box
     * @param camera the camera
     * @return a list of rays
     */
    public List<Ray> constructDOFBeam(Point focalPoint, double boxSize, Camera camera)
    {
        List<Point> points = getPoints(boxSize, boxSize, camera.getLocation(), camera.getvUp(), camera.getvRight());

        List<Ray> rays = new LinkedList<>();
        for(Point point : points)
        {
            rays.add(new Ray(point,focalPoint.subtract(point).normalize()));
        }
        return  rays;
    }

    /**
     * a function that generates rays from a point to a focal point
     * @param p_ij the point
     * @param boxH the height of the box
     * @param boxW the width of the box
     * @param camera the camera
     * @return a list of rays
     */
    public List<Ray> constructAntiAliasingBeam(Point p_ij, double boxH, double boxW , Camera camera)
    {
        List<Point> points = getPoints(boxH, boxW, camera.getLocation(), camera.getvUp(), camera.getvRight());
        List<Ray> rays = new LinkedList<>();
        for(Point point : points)
        {
            rays.add(new Ray(camera.getLocation(), point.subtract(camera.getLocation())));
        }
        return rays;
    }
}
