package models;

import Exceptions.ErrorInImageResources;
import Image.ImageHolder;
import models.impl.ChocoMouche;

import java.awt.*;
import java.util.Map;

/**
 * Created by wilsonr on 2/24/2016.
 */
public interface GameHandler {

    void feed(ImageHolder image) throws ErrorInImageResources;

    boolean gameDetected();

    boolean gameEnded();

    void reset();

    Point getLocation();

    Dimension getDimension();

    String getGameStatus();

    void resetGameVariables();

    void updateGameVariables(ImageHolder image) throws ErrorInImageResources;

    Map<ChocoMouche.Property, Object> getGameProperties();
}
