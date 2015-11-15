package southpaw;

import com.sun.javafx.geom.Point2D;
import javafx.scene.canvas.GraphicsContext;
import java.util.Vector;

public class MapRegion {
    double maxX, maxY, minX, minY;
    String name;
    Vector<Point2D> points;

    public MapRegion(String name) {
        this.name = name;
        points = new Vector<>();
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;

    }

    void addPoint(float x, float y) {
        if(x<minX) {
            minX = x;
        }

        if(y<minY) {
            minY = y;
        }

        if(x>maxX) {
            maxX=x;
        }

        if(y>maxY) {
            maxY=y;
        }

        points.add(new Point2D(x,y));
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
        for(int x=1;x<points.size();x++) {
            Point2D pointFrom = points.get(x - 1);
            Point2D pointTo = points.get(x);

            context.strokeLine(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y);
        }

        Point2D pointFrom = points.get(0);
        Point2D pointTo = points.get(points.size()-1);

        context.strokeLine(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y);
    }
}