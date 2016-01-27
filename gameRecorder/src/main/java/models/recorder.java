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
            if (!detectGame()) {
                showMessageDialog(null, "Game not detected");
                return;
            }

            initializeThread();
        }
    }

    private boolean detectGame() {
        try {
            ImageHolder image = new ImageHolder((Rectangle)null);
            ImageHolder base = new ImageHolder("base.png");
            gameLocation = image.findSubImage(base);
            gameDimension = base.getDimension();
            return gameLocation!=null;
        } catch (CantCaptureScreen cantCaptureScreen) {
            return false;
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
                int i = 0, j=0;
                ImageHolder oldImage = new ImageHolder();
                while (!isInterrupted() && detectGame()) {
                    try {
                        Rectangle rectangle = new Rectangle(gameLocation, gameDimension);
                        ImageHolder image = new ImageHolder(rectangle);
                        if(oldImage.findSubImage(image)==null) {
                            image.saveImage(finalFile.getAbsolutePath() + i++ + ".png");
                        }
                        oldImage = new ImageHolder(rectangle);
                    } catch (IOException e) {
                        break;
                    } catch (CantCaptureScreen cantCaptureScreen) {
                        cantCaptureScreen.printStackTrace();
                    } catch (FileAlreadyExists fileAlreadyExists) {
                        showMessageDialog(null, "The path already exists! ( " + dataPath + " )");
                        break;
                    }
                }
                showMessageDialog(null, "Recording Ended");
            }
        };
        thread.start();
    }

    public void setGameToRecord(String gameLabel) {
        System.out.println("SetPath");
        if(!gameLabel.isEmpty()) {
            dataPath = "data\\" + gameLabel + "\\";
            gameNameSet = true;
        }
        else
            gameNameSet = false;
    }

    public String getDataPath() {
        return dataPath;
    }
}
