package models;

import Exceptions.ErrorInImageResources;
import Image.ImageHolder;

import java.awt.*;

/**
 * Created by wilsonr on 1/31/2016.
 */
public abstract class BasicGame implements Game {
    public enum Status {
        DetectingGame, WaitingToStart, PlayingGame, GameEnded, UnknownState
    }

    private static final int MAXIMUM_UNKNOWN_COUNTS = 5;
    private int unknownCount;
    protected Point gameLocation;
    protected Dimension gameDimension;
    private Status gameState;

    public BasicGame() {
        gameState = Status.DetectingGame;
    }

    private boolean detectSubImage(ImageHolder image, String name) {
        try {
            ImageHolder base = new ImageHolder(name);
            Point subImageLocation = image.findSubImage(base);
            if (name.equals("base.png") && gameState == Status.DetectingGame) {
                gameLocation = subImageLocation;
                gameDimension = base.getDimension();
            }
            return subImageLocation != null;
        } catch (ErrorInImageResources errorInImageResources) {
            return false;
        }
    }

    public void feed(ImageHolder image) throws ErrorInImageResources {
        updateStatus(image);
        if (gameState != Status.UnknownState)
            unknownCount = 0;
        if (gameState == Status.PlayingGame)
            updateGameVariables(image);
    }

    private void updateStatus(ImageHolder image) {
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

    public boolean playingGame() {
        return gameState == Status.PlayingGame;
    }

    public void reset() {
        gameState = Status.DetectingGame;
        unknownCount = 0;
        resetGameVariables();
    }

    public Point getLocation() {
        return gameLocation;
    }

    public Dimension getDimension() {
        return gameDimension;
    }

    public String getGameStatus() {
        return gameState.name();
    }
}
