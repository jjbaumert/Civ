package southpaw;

import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.plaf.synth.Region;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class MapRegions {
    final String MAP_FILE_NAME="map/MapRegions.xml";

    Vector<MapRegion> regions;

    private File getMapFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(MAP_FILE_NAME).getFile());
    }

    private Document getXMLDocument(File mapFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(mapFile);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        return doc;
    }

    private void getMapRegions(Document doc) {
        NodeList mapRegionList = doc.getElementsByTagName("mapregion");

        for(int nodeIndex=0; nodeIndex<mapRegionList.getLength(); nodeIndex++) {
            Node mapRegion = mapRegionList.item(nodeIndex);
            Element mapRegionElements = (Element) mapRegion;

            String name = mapRegionElements.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
            Node boundary = mapRegionElements.getElementsByTagName("boundary").item(0);
            NodeList pointList = ((Element)boundary).getElementsByTagName("point");

            MapRegion region = new MapRegion(name);
            regions.add(region);

            for(int pointIndex=0; pointIndex<pointList.getLength(); pointIndex++) {
                Node point = pointList.item(pointIndex);
                Element pointElements = (Element) point;

                float x = Float.parseFloat(pointElements.getAttribute("x"));
                float y = Float.parseFloat(pointElements.getAttribute("y"));

                region.addPoint(x,y);
            }
        }
    }

    public void draw(GraphicsContext context) {
        for(int x=0;x<regions.size();x++) {
            MapRegion mapRegion = regions.get(x);
            mapRegion.draw(context);
        }
    }

    MapRegions() throws IOException, SAXException, ParserConfigurationException {
        regions = new Vector<MapRegion>();

        File mapFile = getMapFile();
        Document doc = getXMLDocument(mapFile);

        getMapRegions(doc);
    }

    Vector<MapRegion> getInside(double x, double y) {
        Vector<MapRegion> returnRegions = new Vector<MapRegion>();

        for(MapRegion region : regions) {
            if(region.pointIsInside(x,y)) {
                returnRegions.add(region);
            }
        }

        return returnRegions;
    }
}
