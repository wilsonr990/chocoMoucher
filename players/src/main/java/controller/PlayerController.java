package controller;

import models.Player;
import models.Recorder;
import views.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController implements ActionListener {
    private Recorder recorder;

    public enum Actions {
        StartPlaying, StopPlaying
    }

    private PlayerView mainView;
    private Player player;

    public PlayerController(PlayerView view, Player modelPlayer, Recorder modelRecorder) {
        mainView = view;
        player = modelPlayer;
        recorder = modelRecorder;

        updateView();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(Actions.StartPlaying.name())) {
            player.startPlaying();
            recorder.startRecording();
        } else if (command.equals(Actions.StopPlaying.name())) {
            player.stopPlaying();
            recorder.stopRecording();
        }
        mainView.repaint();
    }

    private void updateView() {}
}
