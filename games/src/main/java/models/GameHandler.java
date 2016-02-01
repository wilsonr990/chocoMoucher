package models;

import Exceptions.CantReadFile;
import Image.ImageHolder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by wilsonr on 1/31/2016.
 */
public class GameHandler {
    private enum Status {
        DetectingGame, WaitingToStart, PlayingGame, GameEnded, UnknownState;
    }

    private static final int MAXIMUM_UNKNOWN_COUNTS = 5;
    private int unknownCount;
    private Point gameLocation;
    private Dimension gameDimension;
    private Status generalState;

    public GameHandler() {
        generalState = Status.DetectingGame;
    }

    private boolean detectSubImage(ImageHolder image, String name) {
        try {
            ImageHolder base = new ImageHolder(name);
            Point subImageLocation = image.findSubImage(base);
            if (name.equals("base.png")) {
                gameLocation = subImageLocation;
                gameDimension = base.getDimension();
            }
            return subImageLocation != null;
        } catch (CantReadFile cantReadFile) {
            return false;
        }
    }

    public void feed(ImageHolder image) {
        UpdateStatus(image);
        UpdateGameVariables(image);
    }

    private void UpdateGameVariables(ImageHolder image) {
        if (generalState != Status.UnknownState)
            unknownCount = 0;
        if (generalState == Status.PlayingGame)
            updateGameVariables(image);
    }

    private void updateGameVariables(ImageHolder image) {
    }

    private void UpdateStatus(ImageHolder image) {
        switch (generalState) {
            case DetectingGame:
                if (detectSubImage(image, "base.png"))
                    generalState = Status.WaitingToStart;
                break;
            case WaitingToStart:
                if (detectSubImage(image, "playing.png"))
                    generalState = Status.PlayingGame;
                else if (!detectSubImage(image, "base.png"))
                    generalState = Status.DetectingGame;
                break;
            case PlayingGame:
                if (detectSubImage(image, "ended.png"))
                    generalState = Status.GameEnded;
                else if (!detectSubImage(image, "playing.png"))
                    generalState = Status.UnknownState;
                break;
            case GameEnded:
                break;
            case UnknownState:
                if (detectSubImage(image, "ended.png"))
                    generalState = Status.GameEnded;
                else if (detectSubImage(image, "playing.png"))
                    generalState = Status.PlayingGame;
                else if (unknownCount++ > MAXIMUM_UNKNOWN_COUNTS)
                    generalState = Status.GameEnded;
                break;
            default:
                generalState = Status.GameEnded;
        }
    }

    public boolean gameDetected() {
        return generalState != Status.DetectingGame;
    }

    public boolean gameEnded() {
        return generalState == Status.GameEnded;
    }

    public void reset() {
        generalState = Status.DetectingGame;
        unknownCount = 0;
    }

    public Point getLocation() {
        return gameLocation;
    }

    public Dimension getDimension() {
        return gameDimension;
    }

    public String getGameStatus() {
        return generalState.name();
    }
}
