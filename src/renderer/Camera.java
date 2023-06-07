package renderer;
import primitives.*;
import primitives.Vector;

import javax.sound.sampled.Line;
import java.util.*;

/**
 * a class for camera in the scene that takes the picture
 */
public class Camera {

    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    double height;
    double width;
    double distance;
    ImageWriter imageWriter;
    RayTracerBase rayTracerBase;
    int amountOfRays = 0;
    private Lense lense = null;
    public Camera setLense(double focalDist, double apeture) {
        lense = new Lense(focalDist,apeture);
        return this;
    }
    public Camera setAmountOfRays(int n) {
        amountOfRays = n;
        return this;
    }
    /**
     * setter for image writer
     * @param imageWriter image writer
     * @return Camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * setter for ray tracer base
     * @param rayTracerBase ray tracer base
     * @return Camera
     */
    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * constructor for camera class
     * @param loc location
     * @param to to vector
     * @param up up vector
     */
    public Camera(Point loc, Vector to,Vector up)
    {
        location = loc;
      if (up.dotProduct(to) != 0 || up.distance(new Point(0,0,0))==0 ||to.distance(new Point(0,0,0))==0)
      {
          throw  new IllegalArgumentException("Vectors are not perpendicular to each other");
      }
      vUp = up.normalize();
      vTo = to.normalize();
      vRight = to.crossProduct(up).normalize();
    }

    /**
     * setter for view plane size and distance
     * @param newWidth width
     * @param newHeight height
     * @return Camera
     */
    public Camera setVPSize(double newWidth, double newHeight)
    {
        width = newWidth;
        height = newHeight;
        return this;
    }

    /**
     * setter for view plane distance
     * @param newDistance distance
     * @return Camera
     */
    public Camera setVPDistance(double newDistance)
    {
        if(newDistance <= 0)
            throw  new IllegalArgumentException("Distance can't be less or equals to zero");
        distance = newDistance;
        return  this;
    }

    public Ray constructRay(int nX, int nY, int j, int i)
    {
        return new Ray(Point.ZERO,new Vector(0,0,0));
    }
    /**
     * constructs a ray through the view plane
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j pixel in x axis
     * @param i pixel in y axis
     * @return Ray
     */
    public List<Ray> constructBeam(int nX, int nY, int j, int i,List<Point> points)
    {
     if(nX <= 0 || nY <= 0 || j < 0 || i < 0)
     {
         throw  new IllegalArgumentException("The numbers less then zero!");
     }
       Point pc = location.add(vTo.scale(distance));
       double rY = this.height / nY;
       double rX = this.width / nX;

       double yi = -1.0*(i- ((double)(nY - 1)/2))*rY;

       double xj = (j- ((double)(nX - 1)/2))*rX;

     Point p_ij = pc;
      if(yi != 0)
      {
           p_ij = p_ij.add(vUp.scale(yi));
      }
      if(xj != 0)
      {
           p_ij = p_ij.add( vRight.scale(xj));
      }

      Point focalPoint = location.add(p_ij.subtract(location).normalize().scale(lense.focalDistance));

      List<Ray> rays = new LinkedList<>();
      for(Point point : points)
      {
         rays.add(new Ray(point,focalPoint.subtract(point).normalize()));
      }
       return  List.of(new Ray(location,p_ij.subtract(location)));
    }

    /**
     * getter for location
     * @return Point
     */
    public Point getLocation() {
        return location;
    }

    /**
     * getter for vTo
     * @return Vector
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * getter for vUp
     * @return Vector
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * getter for vRight
     * @return Vector
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * getter for height
     * @return double
     */
    public double getHeight() {
        return height;
    }

    /**
     * getter for width
     * @return double
     */
    public double getWidth() {
        return width;
    }

    /**
     * getter for distance
     * @return double
     */
    public double getDistance() {
        return distance;
    }

    /**
     * renders the image by the ray tracer
     */
    public Camera renderImage()
    {
        if(location == null || vTo == null || vUp == null || vRight == null || height == 0 || width == 0 || distance == 0 || imageWriter == null || rayTracerBase == null)
        {
            throw new MissingResourceException("Missing resource", "Camera", "location or vTo or vUp or vRight or height or width or distance or imageWriter or rayTracerBase");
        }

        //throw new UnsupportedOperationException("missing implementation");

        int resX = imageWriter.getNx();
        int resY = imageWriter.getNy();
        List<Point> points = getPoints();
        for(int i = 0; i < resX; i++)
        {
            for(int j = 0; j < resY; j++)
            {
                double red = 0;
                double green = 0;
                double blue = 0;
                Color color = Color.BLACK;
                List<Ray> rays = constructBeam(resX,resY,i,j,points);
                int size = rays.size();
                for(Ray ray : rays)
                {
                    color = rayTracerBase.traceRay(ray);
                    red += color.getColor().getRed();
                    green += color.getColor().getGreen();
                    blue += color.getColor().getBlue();
                }
                red /= size;
                green /= size;
                blue /= size;
                imageWriter.writePixel(i,j,color);
            }
        }

        return this;
    }


    private List<Point> getPoints() {
        int n = (int)Math.sqrt(amountOfRays);
        double boxSize = lense.apeture / n;
        double halfBox = boxSize/2;
        List<Point> pList = new LinkedList<>();
        Random rand = new Random();
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j ++)
            {
                double offsetX = rand.nextDouble(boxSize) - halfBox;
                double offsetY = rand.nextDouble(boxSize) - halfBox;
                double yi = -1.0*(i- ((double)(n - 1)/2))*boxSize;// + offsetY;
                double xj = (j- ((double)(n - 1)/2))*boxSize;// + offsetX;
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
     * print a grid on the view plane
     * @param interval interval length between lines
     * @param color color of the lines
     */
    public void printGrid(int interval, Color color) {

        if(imageWriter == null)
        {
            throw new MissingResourceException("Missing resource", "Camera", "imageWriter");
        }
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for(int i = 0; i < nX; i += interval)
            for(int j = 0; j < nY; j ++)
                imageWriter.writePixel(i, j, color);

        for(int i = 0; i < nY; i ++)
            for(int j = 0; j < nX; j += interval)
                imageWriter.writePixel(i, j, color);
    }

    /**
     * writes the image to the file
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("Missing resource", "Camera", "imageWriter");
        }
        imageWriter.writeToImage();
    }
}
