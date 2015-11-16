package southpaw;

import com.sun.javafx.geom.Point2D;
import javafx.scene.canvas.GraphicsContext;
import java.util.Vector;

public class MapRegion {
    double maxX, maxY, minX, minY;
    String name;
    Vector<MapRegionLine> edges;

    public MapRegion(String name) {
        this.name = name;
        edges = new Vector<>();
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;

    }

    public void addEdge(float x1, float y1, float x2, float y2) {

        if(x1<minX) minX = x1;
        if(x2<minX) minX = x2;

        if(y1<minY) minY = y1;
        if(y2<minY) minY = y2;

        if(x1>maxX) maxX=x1;
        if(x2>maxX) maxX=x2;

        if(y1>maxY) maxY=y1;
        if(y2>maxY) maxY=y2;

        edges.add(new MapRegionLine(x1,y1,x2,y2));
    }

    boolean pointIsInside(double x, double y) {
        if(x<minX || y<minY) {
            return false;
        }

        if(x>maxX || y>maxY) {
            return false;
        }

        return true;
    }

    void draw(GraphicsContext context) {
        for(int x=0;x<edges.size();x++) {
            MapRegionLine mapRegionLine = edges.get(x);

            context.strokeLine(mapRegionLine.fromX, mapRegionLine.fromY, mapRegionLine.toX, mapRegionLine.toY);
        }
    }
}