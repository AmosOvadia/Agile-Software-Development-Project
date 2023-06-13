package renderer;
import primitives.*;
import primitives.Vector;

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
    private RaySampler raySampler;
    private double focalDistance = 0;
    private double aperture = 0;
    private int threadsCount = 1;
    private double debugPrint = 1;

    public Camera setDebugPrint(double v) {
        this.debugPrint = v;
        return this;
    }
    public Camera setMultithreading(int threads) {
        this.threadsCount = threads;
        return this;
    }
    /**
     * setter for the ray sampler
     * @param amountOfRays the amount of rays that will be generated
     * @return Camera
     */
    public Camera setRaySampler(int amountOfRays)
    {
        raySampler = new RaySampler(amountOfRays);
        return this;
    }

    /**
     * setter for the  focal distance and aperture
     * @param focalDist focal distance
     * @param apeture aperture
     * @return Camera
     */
    public Camera setLense(double focalDist, double apeture) {
        this.focalDistance = focalDist;
        this.aperture = apeture;
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
     * getter for apeture
     * @return double
     */

    public double getAperture()
    {
        return aperture;
    }

    /**
     * getter for focal distance
     * @return double
     */

    public double getFocalDistance()
    {
        return focalDistance;
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


        int resX = imageWriter.getNx();
        int resY = imageWriter.getNy();

        Pixel.initialize(resX,resY,debugPrint);

        int a = 0;
        while(threadsCount-- > 0)
        {
            new Thread(() -> {
                for(Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                {
                    if(aperture == 0) //if there is no DOF
                        RenderNoDOF(resX,resY,pixel.col, pixel.row);
                    else
                        imageRenderWithDOF(resX,resY, pixel.col, pixel.row);

                    /* if(pixel.col == pixel.row)
                    {
                        System.out.println("col: " + pixel.col + " row: " + pixel.row);
                    }
                     */
                }
            }).start();
        }
        Pixel.waitToFinish();

        return this;
    }



    /**
     * calculates the center point of a given pixes
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j the pixel's index in x axis
     * @param i the pixel's index in y axis
     * @return Point the center point of the pixel
     */
    private Point calcPIJ(int nX, int nY, int j, int i)
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
        return p_ij;
    }

    /**
     * constructs a ray that goes through the center of a given pixel for rendering without DOF
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j the pixel's index in x axis
     * @param i the pixel's index in y axis
     * @return Ray the ray that goes through the center of the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i)
    {
        Point p_ij = calcPIJ(nX,nY,j,i);
        return  new Ray(location,p_ij.subtract(location));
    }

    /**
     * renders the image with DOF
     * @param resX number of pixels in x axis
     * @param resY number of pixels in y axis
     */
    private void imageRenderWithDOF(int resX, int resY, int i, int j) {

                Color color = Color.BLACK;
                double boxSize = aperture / raySampler.getSqrtAmountOfRays();

                Point pij = calcPIJ(resX,resY,i,j);
                
                Point focalPoint = location.add(pij.subtract(location).normalize().scale(focalDistance));

                List<Ray> rays = raySampler.constructDOFBeam(focalPoint, boxSize, this);
                int size = rays.size();
                for(Ray ray : rays)
                {
                    color = color.add(rayTracerBase.traceRay(ray));
                }
                imageWriter.writePixel(i,j,color.reduce(size));
    }

    /**
     * renders the image without DOF
     * @param resX number of pixels in x axis
     * @param resY number of pixels in y axis
     */
    private void RenderNoDOF(int resX, int resY,int i, int j){
                Ray ray = constructRay(resX,resY,i,j);
                Color color = rayTracerBase.traceRay(ray);
                imageWriter.writePixel(i,j,color);
    }

    /**
     * print a grid on the view plane
     * @param interval interval length between lines
     * @param color color of the lines
     */
    public Camera printGrid(int interval, Color color) {

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
        return this;
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
