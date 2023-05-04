package XmlParsers;

import geometries.*;
import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlToSceneParser {


    private static String [] getNameFromElem(Element elem, String name)
    {
        return elem.getAttribute(name).split(" ");
    }
    private static Point getPointFromXml(String str)
    {
        String[] p2String = str.split(" ");
        return new Point(Double.parseDouble(p2String[0]),
                Double.parseDouble(p2String[1]),
                Double.parseDouble(p2String[2]));
    }
    public static Scene loadSceneFromXML(String sceneName, String fileName) {

        Scene scene = new Scene(sceneName);
        try {
            File xmlFile = new File(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(xmlFile);
            Element sceneElement = doc.getDocumentElement();

            String[] bgColorString = getNameFromElem(sceneElement,"background-color");
            Color bgColor = new Color(Integer.parseInt(bgColorString[0]),
                    Integer.parseInt(bgColorString[1]),
                    Integer.parseInt(bgColorString[2]));

            NodeList ambientLightList = sceneElement.getElementsByTagName("ambient-light");
            Element ambientLightElement = (Element) ambientLightList.item(0);
            String[] ambientColorString = getNameFromElem(ambientLightElement,"color");
            AmbientLight ambientColor = new AmbientLight(new Color(Integer.parseInt(ambientColorString[0]),
                    Integer.parseInt(ambientColorString[1]),
                    Integer.parseInt(ambientColorString[2])), new Double3(1, 1, 1));

            Geometries geometries = new Geometries();
            //List<Geometry> geometries = new ArrayList<>();
            NodeList geometryList = sceneElement.getElementsByTagName("geometries");
            Element geometryElement = (Element) geometryList.item(0);
            NodeList shapeList = geometryElement.getChildNodes();

            Point center;
            for (int i = 0; i < shapeList.getLength(); i++) {
                if (shapeList.item(i) instanceof Element shapeElement) {
                    String shapeType = shapeElement.getNodeName();
                    switch (shapeType) {
                        case "sphere":
                            center = getPointFromXml(shapeElement.getAttribute("center"));
                            double radius = Double.parseDouble(shapeElement.getAttribute("radius"));
                            Sphere sphere = new Sphere(center, radius);
                            geometries.add(sphere);
                            break;
                        case "triangle":
                            Point p0 = getPointFromXml(shapeElement.getAttribute("p0"));
                            Point p1 = getPointFromXml(shapeElement.getAttribute("p1"));
                            Point p2 = getPointFromXml(shapeElement.getAttribute("p2"));
                            Triangle triangle = new Triangle(p0, p1, p2);
                            geometries.add(triangle);
                            break;

                        case "Polygon":
                            List<Point> polygonPoints = new ArrayList<>();
                            NamedNodeMap attributes = shapeElement.getAttributes();
                            for (int j = 0; j < attributes.getLength(); j++) {
                                Point point = getPointFromXml(attributes.item(j).getNodeValue());
                                polygonPoints.add(point);
                            }
                            Point[] pointsArray = polygonPoints.toArray(new Point[polygonPoints.size()]);
                            Polygon polygon = new Polygon(pointsArray);
                            geometries.add(polygon);
                            break;

                        case "plane":
                            //Vector normal = (Vector)getPointFromXml(shapeElement.getAttribute("normal"));
                            String [] normalVector = shapeElement.getAttribute("normal").split(" ");
                            Vector normal = new Vector(Double.parseDouble(normalVector[0]),
                                    Double.parseDouble(normalVector[1]),
                                    Double.parseDouble(normalVector[2]));
                            Point planePoint = getPointFromXml(shapeElement.getAttribute("point"));
                            Plane plane = new Plane(planePoint, normal);
                            geometries.add(plane);
                            break;
                    }
                }
            }
            // Create the Scene object
            scene.setGeometries(geometries);
            scene.setBackground(bgColor);
            scene.setAmbientLight(ambientColor);

        } catch(Exception e){
            e.printStackTrace();
        }
        return scene;
    }

}
