package southpaw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Vector;

public class Main extends Application {
    private Scene scene;
    private MapRegions mapRegions;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("civ.fxml"));
        primaryStage.setTitle("Civilization");

        scene = new Scene(root, 1500, 800);
        Canvas mapCanvas = (Canvas) scene.lookup("#mapCanvas");
        Image image = new Image("map/CivilizationMap.jpg");

        GraphicsContext graphicsContext = mapCanvas.getGraphicsContext2D();
        graphicsContext.drawImage(image,0,0,1400,700);

        mapCanvas.setOnMouseClicked(event -> handleClick(event.getX(), event.getY()));

        System.out.println(image.getWidth());
        System.out.println(image.getHeight());

        readMapRegions();
        graphicsContext.setStroke(Color.WHITE);
        mapRegions.draw(graphicsContext);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    void handleClick(double x, double y) {
        System.out.println(x + ", " + y);

        Vector<MapRegion> insideRegions = mapRegions.getInside(x,y);

        for (MapRegion region : insideRegions) {
            System.out.println(region.name);
        }

    }

    private void readMapRegions() throws ParserConfigurationException, SAXException, IOException {
        mapRegions = new MapRegions();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
