package controllers;

import models.Recorder;
import views.RecorderView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.TimerTask;

import static controllers.RecorderController.Actions.StartRec;
import static controllers.RecorderController.Actions.StopRec;

public class RecorderController implements ActionListener, KeyListener {
    public enum Actions {
        StartRec, StopRec
    }

    private RecorderView mainView;
    private Recorder recorder;

    public RecorderController(RecorderView view, Recorder model) {
        mainView = view;
        recorder = model;

        Timer timer = new Timer(100, null);
        timer.addActionListener(this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command!=null) {
            if (command.equals(StartRec.name())) {
                recorder.startRecording();
            } else if (command.equals(StopRec.name())) {
                recorder.stopRecording();
            }
        }
        mainView.repaint();
        updateView();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        recorder.setGameToRecord( mainView.getGameField() );
        updateView();
    }

    private void updateView() {
        mainView.setPathLabel(new File(recorder.getDataPath()).getAbsolutePath());
        mainView.setStatusLabel(recorder.getGameStatus());
    }
}
