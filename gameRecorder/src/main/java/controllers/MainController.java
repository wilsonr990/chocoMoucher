package controllers;

import models.Recorder;
import views.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import static controllers.MainController.Actions.StartRec;
import static controllers.MainController.Actions.StopRec;

public class MainController implements ActionListener, KeyListener {
    public enum Actions {
        StartRec, StopRec
    }

    private MainView mainView;
    private Recorder recorder;

    public MainController(MainView view, Recorder model) {
        mainView = view;
        recorder = model;

        updateView();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(StartRec.name())) {
            recorder.startRecording();
        } else if (command.equals(StopRec.name())) {
            recorder.stopRecording();
        }
        mainView.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        recorder.setGameToRecord( mainView.getGameLabel() );
        updateView();
    }

    private void updateView() {
        mainView.setPathLabel(new File(recorder.getDataPath()).getAbsolutePath());
    }
}
