package models;

import Exceptions.CantCaptureScreen;
import Exceptions.FileAlreadyExists;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 1/26/2016.
 */
public class Recorder {
    private Thread thread;
    private String pathName = "data";
    private boolean gameNameSet = false;

    public void startRecording() {
        System.out.println("Start");
        if (thread == null || !thread.isAlive()) {
            initializeThread();
        }
    }

    public void stopRecording() {
        System.out.println("Stop");
        thread.interrupt();
    }

    private void initializeThread() {
        if (!gameNameSet) {
            showMessageDialog(null, "Game should not be empty");
            return;
        }

        File file = new File(pathName);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(pathName + 'a' + "0.png");
        char c;
        for (c = 'a'; file.exists() && c < 'z'; ) {
            file = new File(pathName + ++c + "0.png");
        }

        final File finalFile = new File(pathName + c);
        thread = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (!isInterrupted()) {
                    try {
                        Rectangle rectangle = null;
                        Image.Image image = new Image.Image(rectangle);
                        image.saveImage(finalFile.getAbsolutePath() + i++ + ".png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CantCaptureScreen cantCaptureScreen) {
                        cantCaptureScreen.printStackTrace();
                    } catch (FileAlreadyExists fileAlreadyExists) {
                        showMessageDialog(null, "The path already exists! ( " + pathName + " )");
                        return;
                    }
                }
            }
        };
        thread.start();
    }

    public void setGameToRecord(String gameLabel) {
        System.out.println("SetPath");
        if(!gameLabel.isEmpty()) {
            pathName = "data\\" + gameLabel + "\\";
            gameNameSet = true;
        }
        else
            gameNameSet = false;
    }

    public String getDataPath() {
        return pathName;
    }
}
