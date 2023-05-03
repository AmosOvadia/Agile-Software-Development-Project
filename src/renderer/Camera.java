package renderer;
import primitives.*;
public class Camera {

    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    double height;
    double width;
    double distance;

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
    public Camera setVPSize(double newWidth, double newHeight)
    {
        width = newWidth;
        height = newHeight;
        return this;
    }
    public Camera setVPDistance(double newDistance)
    {
        if(newDistance <= 0)
            throw  new IllegalArgumentException("Distance can't be less or equals to zero");
        distance = newDistance;
        return  this;
    }
    public Ray constructRay(int nX, int nY, int j, int i)
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
      if(xj == 0 && yi == 0 ) {
          return  new Ray(location,p_ij.subtract(location));
      }
      else {
          if(xj == 0)
          {
               p_ij = pc.add(vUp.scale(yi)) ;
          }
          else if(yi==0)
          {
               p_ij = pc.add( vRight.scale(xj));
          }
          else
          {
               p_ij = pc.add( vRight.scale(xj).add(vUp.scale(yi)));
          }
      }
       return  new Ray(location,p_ij.subtract(location));
    }

    public Point getLocation() {
        return location;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

}
