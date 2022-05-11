/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author technowings
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXMLFile {

    public static void main(String argv[]) {

        try {

            File fXmlFile = new File("C:/Users/technowings/Documents/plasmodium-phonecamera/plasmodium-phone-0001.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

//	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//
            NodeList nList = doc.getElementsByTagName("bndbox");

            String[][] points = new String[nList.getLength()][4];
//	System.out.println("----------------------------");
            String nodes = null;
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                nodes = nNode.getTextContent();
//		System.out.println("Current Element :" + nodes);
//                System.out.println(nodes);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    points[temp][0] = eElement.getElementsByTagName("xmin").item(0).getTextContent();
                    points[temp][1] = eElement.getElementsByTagName("ymin").item(0).getTextContent();
                    points[temp][2] = eElement.getElementsByTagName("xmax").item(0).getTextContent();
                    points[temp][3] = eElement.getElementsByTagName("ymax").item(0).getTextContent();
//			System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("Xmin: " + eElement.getElementsByTagName("xmin").item(0).getTextContent());
                    System.out.println("Ymin: " + eElement.getElementsByTagName("ymin").item(0).getTextContent());
                    System.out.println("Xmax: " + eElement.getElementsByTagName("xmax").item(0).getTextContent());
                    System.out.println("Ymax : " + eElement.getElementsByTagName("ymax").item(0).getTextContent());

                }
            }
//            String[] n = nodes.split("\n");
//            System.out.println("node = "+points.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[][] read(File infile) {
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(infile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("bndbox");
            String[][] points = new String[nList.getLength()][4];
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


//		System.out.println("Current Element :" + nodes);
//                System.out.println(nodes);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    points[temp][0] = eElement.getElementsByTagName("xmin").item(0).getTextContent();
                    points[temp][1] = eElement.getElementsByTagName("ymin").item(0).getTextContent();
                    points[temp][2] = eElement.getElementsByTagName("xmax").item(0).getTextContent();
                    points[temp][3] = eElement.getElementsByTagName("ymax").item(0).getTextContent();
//			System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("Xmin: " + eElement.getElementsByTagName("xmin").item(0).getTextContent());
                    System.out.println("Ymin: " + eElement.getElementsByTagName("ymin").item(0).getTextContent());
                    System.out.println("Xmax: " + eElement.getElementsByTagName("xmax").item(0).getTextContent());
                    System.out.println("Ymax : " + eElement.getElementsByTagName("ymax").item(0).getTextContent());

                }
            }
            return points;
            }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}