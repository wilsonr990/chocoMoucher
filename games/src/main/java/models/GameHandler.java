package models;

import Exceptions.CantReadFile;
import Image.ImageHolder;

import java.awt.*;

/**
 * Created by wilsonr on 1/31/2016.
 */
public class GameHandler {
    private boolean gameDetected;
    private Point gameLocation;
    private Dimension gameDimension;

    public GameHandler() {
        gameDetected = false;
    }

    private boolean detectGame(ImageHolder image) {
        try {
            ImageHolder base = new ImageHolder("base.png");
            gameLocation = image.findSubImage(base);
            gameDimension = base.getDimension();
            return gameLocation != null;
        } catch (CantReadFile cantReadFile) {
            return false;
        }
    }

    public void feed(ImageHolder image) {
        gameDetected = detectGame(image);
    }

    public boolean gameDetected() {
        return gameDetected;
    }

    public Point getLocation() {
        return gameLocation;
    }

    public Dimension getDimension() {
        return gameDimension;
    }
}
