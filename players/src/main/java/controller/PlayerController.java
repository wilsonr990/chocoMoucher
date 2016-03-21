package controller;

import models.*;
import views.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController implements ActionListener {

    public enum Actions {
        StartPlaying, StopPlaying;

    }

    private PlayerView mainView;
    private Recorder recorder;
    private final Game game;
    private PlayerModel playerModel;

    public PlayerController(PlayerView view, Game modelGame, PlayerModel modelPlayer, Recorder modelRecorder) {
        mainView = view;
        game = modelGame;
        playerModel = modelPlayer;
        recorder = modelRecorder;

        updateView();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(Actions.StartPlaying.name())) {
            playerModel.startPlaying();
            recorder.startRecording();
        } else if (command.equals(Actions.StopPlaying.name())) {
            playerModel.stopPlaying();
            recorder.stopRecording();
        }
        mainView.repaint();
    }

    private void updateView() {}
}
