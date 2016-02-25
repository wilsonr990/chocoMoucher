package models;

import Exceptions.CantReadFile;
import Image.ImageHolder;
import models.impl.ChocoMouche;

import java.awt.*;
import java.util.Map;

/**
 * Created by wilsonr on 1/31/2016.
 */
public abstract class GameHandler {
    public enum Status {
        DetectingGame, WaitingToStart, PlayingGame, GameEnded, UnknownState;
    }

    private static final int MAXIMUM_UNKNOWN_COUNTS = 5;
    private int unknownCount;
    protected Point gameLocation;
    protected Dimension gameDimension;
    private Status gameState;
    protected String printedMap;

    public GameHandler() {
        gameState = Status.DetectingGame;
    }

    public Status getStatus() {
        return gameState;
    }

    protected boolean detectSubImage(ImageHolder image, String name) {
        try {
            ImageHolder base = new ImageHolder(name);
            Point subImageLocation = image.findSubImage(base);
            if (name.equals("base.png") && gameState==Status.DetectingGame) {
                gameLocation = subImageLocation;
                gameDimension = base.getDimension();
            }
            return subImageLocation != null;
        } catch (CantReadFile cantReadFile) {
            return false;
        }
    }

    public void feed(ImageHolder image) throws CantReadFile {
        UpdateStatus(image);
        if (gameState != Status.UnknownState)
            unknownCount = 0;
        if (gameState == Status.PlayingGame)
            UpdateGameVariables(image);
    }

    private void UpdateStatus(ImageHolder image) {
        switch (gameState) {
            case DetectingGame:
                if (detectSubImage(image, "base.png"))
                    gameState = Status.WaitingToStart;
                break;
            case WaitingToStart:
                if (detectSubImage(image, "playing.png"))
                    gameState = Status.PlayingGame;
                else if (!detectSubImage(image, "base.png"))
                    gameState = Status.DetectingGame;
                break;
            case PlayingGame:
                if (detectSubImage(image, "ended.png"))
                    gameState = Status.GameEnded;
                else if (!detectSubImage(image, "playing.png"))
                    gameState = Status.UnknownState;
                break;
            case GameEnded:
                break;
            case UnknownState:
                if (detectSubImage(image, "ended.png"))
                    gameState = Status.GameEnded;
                else if (detectSubImage(image, "playing.png"))
                    gameState = Status.PlayingGame;
                else if (unknownCount++ > MAXIMUM_UNKNOWN_COUNTS)
                    gameState = Status.GameEnded;
                break;
            default:
                gameState = Status.GameEnded;
        }
    }

    public boolean gameDetected() {
        return gameState != Status.DetectingGame;
    }

    public boolean gameEnded() {
        return gameState == Status.GameEnded;
    }

    public void reset() {
        gameState = Status.DetectingGame;
        unknownCount = 0;
        ResetGameVariables();
    }

    public Point getLocation() {
        return gameLocation;
    }

    public Dimension getDimension() {
        return gameDimension;
    }

    public String getGameStatus() {
        return gameState.name() + "\n\n" + printedMap;
    }

    protected abstract void ResetGameVariables();

    protected abstract void UpdateGameVariables(ImageHolder image) throws CantReadFile;

    public abstract Map<ChocoMouche.Property, Object> getGameProperties();
}
