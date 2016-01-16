package controllers;

import Exceptions.CantCaptureScreen;
import views.MainView;

import Image.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InterruptedIOException;

import static controllers.MainController.Actions.*;

public class MainController implements ActionListener {
    private final Thread thread;
    private String pathName = "img";

    public enum Actions {
        SetPath, StartRec, StopRec
    }
    private final MainView view;

    public MainController(MainView mainView) {
        view = mainView;
        thread = new Thread() {
            @Override
            public void run() {
                int i=0;
                while (!isInterrupted()) {
                    System.out.println("taking " + pathName + i);
                    try {
                        Rectangle rectangle = null;
                        Image image = new Image(rectangle);
                        image.saveImage(pathName + i++ + ".png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CantCaptureScreen cantCaptureScreen) {
                        cantCaptureScreen.printStackTrace();
                    }
                }
            }
        };
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("action ");
        if (command.equals(SetPath.name())) {
            System.out.println("setpath");
            pathName = view.getPathLabel();
        } else if (command.equals(StartRec.name())) {
            thread.start();
        } else if(command.equals(StopRec.name())){
            thread.interrupt();
        }
        view.repaint();
    }
}
