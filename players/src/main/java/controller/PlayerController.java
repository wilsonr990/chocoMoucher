package controller;

import models.*;
import models.impl.ChocoMouche;
import views.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerController implements ActionListener {

    private Timer timer;

    public enum Actions {
        StartPlaying, StopPlaying;
    }

    private PlayerView mainView;
    private Recorder recorder;
    private PlayerModel playerModel;

    public PlayerController(PlayerView view, PlayerModel modelPlayer, Recorder modelRecorder) {
        mainView = view;
        playerModel = modelPlayer;
        recorder = modelRecorder;

        updateView();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(Actions.StartPlaying.name())) {
            playerModel.startPlaying();
            recorder.startRecording();
            timer = new Timer();
            timer.schedule( new TimerTask() {
                public void run() {
                    updateView();
                }
            }, 0, 60*10);
        } else if (command.equals(Actions.StopPlaying.name())) {
            playerModel.stopPlaying();
            recorder.stopRecording();
            timer.cancel();
        }
        mainView.repaint();
        updateView();
    }

    private void updateView() {
        mainView.setStatusLabel(playerModel.getGameStatus());
    }
}
