package views;

import controller.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static controller.PlayerController.Actions.StartPlaying;
import static controller.PlayerController.Actions.StopPlaying;

public class PlayerView extends JFrame {
    private JButton startRecButton;
    private JButton stopRecButton;
    private JPanel mainPanel;
    private JTextArea statusLabel;

    public PlayerView() {
        setTitle("Game Recorder");
        setSize(400, 400);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        add(mainPanel);

        startRecButton.setActionCommand(StartPlaying.name());
        stopRecButton.setActionCommand(StopPlaying.name());

        setVisible(true);
    }

    public void repaint() {
        super.repaint();
    }

    public void setController(PlayerController controller) {
        startRecButton.addActionListener(controller);
        stopRecButton.addActionListener(controller);
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel.setText(statusLabel);
    }
}

