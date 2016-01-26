package controllers;

import Exceptions.CantCaptureScreen;
import Exceptions.FileAlreadyExists;
import Image.Image;
import views.MainView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import static controllers.MainController.Actions.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainController implements ActionListener, KeyListener {
    private Thread thread;
    private String pathName = "data";

    public enum Actions {
        StartRec, StopRec
    }

    private final MainView view;

    public MainController(final MainView mainView) {
        view = mainView;
        view.setPathLabel(new File(pathName).getAbsolutePath());
    }

    private void initializeThread() {
        if (view.getGameLabel().equals("")) {
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
                        Image image = new Image(rectangle);
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

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(StartRec.name())) {
            System.out.println("StartPath");
            if (thread == null || !thread.isAlive()) {
                initializeThread();
            }
        } else if (command.equals(StopRec.name())) {
            System.out.println("StopPath");
            thread.interrupt();
        }
        view.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("SetPath");
        pathName = "data\\" + view.getGameLabel() + "\\";
        view.setPathLabel(new File(pathName).getAbsolutePath());
    }
}
