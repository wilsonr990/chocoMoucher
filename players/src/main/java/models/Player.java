package models;

import Exceptions.CantCaptureScreen;
import Exceptions.CantReadFile;
import Exceptions.FileAlreadyExists;
import Exceptions.GameHasEnded;
import Image.ImageHolder;

import java.awt.*;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 2/7/2016.
 */
public abstract class Player {
    protected final GameHandler gameHandler;
    private final GameInterface gameInterface;
    private Thread thread;

    public Player(GameHandler gameHandler, GameInterface gameInterface) {
        this.gameHandler = gameHandler;
        this.gameInterface = gameInterface;
    }

    public void startPlaying() {
        System.out.println("Start");
        if (thread == null || !thread.isAlive()) {
            initializeThread();
        }
    }

    private void initializeThread() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    gameHandler.reset();
                    while (!isInterrupted()) {
                        ImageHolder image = new ImageHolder((Rectangle) null);
                        if (gameHandler.gameDetected())
                            image.getSubImage(new Rectangle(gameHandler.getLocation(), gameHandler.getDimension()));
                        gameHandler.feed(image);
                        if (!gameHandler.gameDetected() || gameHandler.gameEnded()) {
                            showMessageDialog(null, "Game Ended or closed!");
                            break;
                        }
                        else{
                            Play();
                        }
                    }
                } catch (CantCaptureScreen cantCaptureScreen) {
                    showMessageDialog(null, "Cant take screenshot!");
                } catch (GameHasEnded gameHasEnded) {
                    showMessageDialog(null, "ended game???!");
                } catch (CantReadFile cantReadFile) {
                    showMessageDialog(null, "filee???!");
                }
            }
        };
        thread.start();
    }

    protected abstract void Play() throws GameHasEnded;

    public void stopPlaying() {
        System.out.println("Stop");
        thread.interrupt();
    }
}
