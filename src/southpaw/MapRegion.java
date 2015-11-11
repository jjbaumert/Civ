package southpaw;

import com.sun.javafx.geom.Point2D;
import javafx.scene.canvas.GraphicsContext;
import java.util.Vector;

public class MapRegion {
    String name;
    Vector<Point2D> points;

    public MapRegion(String name) {
        this.name = name;
        points = new Vector<>();
    }

    void addPoint(float x, float y) {
        points.add(new Point2D(x,y));
    }

    boolean pointIsInside(double x, double y) {
        return false;
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