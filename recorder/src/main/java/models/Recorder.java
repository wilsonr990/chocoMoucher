package models;

import Exceptions.CantCaptureScreen;
import Exceptions.FileAlreadyExists;
import Image.ImageHolder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 1/26/2016.
 */
public class Recorder {
    private Thread thread;
    private String dataPath = "data";
    private boolean gameNameSet = false;
    private GameHandler gameHandler;

    public Recorder(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void startRecording() {
        System.out.println("Start");
        if (thread == null || !thread.isAlive()) {
            if (!gameNameSet) {
                showMessageDialog(null, "Game should not be empty");
                return;
            }

            initializeThread();
        }
    }

    public void stopRecording() {
        System.out.println("Stop");
        thread.interrupt();
    }

    private void initializeThread() {
        File file = new File(dataPath);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(dataPath + 'a' + "0.png");
        char c;
        for (c = 'a'; file.exists() && c < 'z'; ) {
            file = new File(dataPath + ++c + "0.png");
        }

        final File finalFile = new File(dataPath + c);
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    ImageHolder oldImage = new ImageHolder();
                    gameHandler.reset();
                    while (!isInterrupted()) {
                        ImageHolder image = new ImageHolder((Rectangle) null);
                        gameHandler.feed(image);
                        if( !gameHandler.gameDetected() || gameHandler.gameEnded() ) {
                            break;
                        }

                        image.getSubImage( new Rectangle(gameHandler.getLocation(), gameHandler.getDimension()) );
                        if (oldImage.findSubImage(image) == null) {
                            image.saveImage(finalFile.getAbsolutePath() + i++ + ".png");
                        }
                        oldImage = image;
                    }
                } catch (IOException e) {
                    showMessageDialog(null, "Cant manage files!");
                } catch (CantCaptureScreen cantCaptureScreen) {
                    showMessageDialog(null, "Cant capture screen!");
                } catch (FileAlreadyExists fileAlreadyExists) {
                    showMessageDialog(null, "The path already exists! ( " + dataPath + " )");
                }
            }
        };
        thread.start();
    }

    public void setGameToRecord(String gameLabel) {
        System.out.println("SetPath");
        if (!gameLabel.isEmpty()) {
            dataPath = "data\\" + gameLabel + "\\";
            gameNameSet = true;
        } else
            gameNameSet = false;
    }

    public String getDataPath() {
        return dataPath;
    }

    public String getGameStatus() {
        return gameHandler.getGameStatus();
    }
}
