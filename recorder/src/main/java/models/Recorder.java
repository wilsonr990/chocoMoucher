package models;

import Exceptions.CantCaptureScreen;
import Exceptions.CantReadFile;
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
    private Point gameLocation;
    private Dimension gameDimension;

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
                    while (!isInterrupted()) {
                        ImageHolder image = new ImageHolder((Rectangle) null);
                        if( !detectGame(image) ) {
                            showMessageDialog(null, "No game detected!");
                            break;
                        }

                        image.getSubImage( new Rectangle(gameLocation, gameDimension) );
                        if (oldImage.findSubImage(image) == null) {
                            image.saveImage(finalFile.getAbsolutePath() + i++ + ".png");
                        }
                        oldImage = image;
                    }
                    showMessageDialog(null, "Recording Ended");
                } catch (IOException e) {
                    showMessageDialog(null, "Cant manage files!");
                } catch (CantCaptureScreen cantCaptureScreen) {
                    showMessageDialog(null, "Cannt capture screen!");
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
}
