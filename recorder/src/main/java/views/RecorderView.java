package views;

import controllers.RecorderController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TimerTask;

import static controllers.RecorderController.Actions.StartRec;
import static controllers.RecorderController.Actions.StopRec;

public class RecorderView extends JFrame {
    private JButton startRecButton;
    private JButton stopRecButton;
    private JPanel mainPanel;
    private JTextField gameField;
    private JLabel pathLabel;
    private JTextArea statusLabel;

    public RecorderView() {
        setTitle("Game Recorder");
        setSize(400, 400);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        add(mainPanel);

        startRecButton.setActionCommand(StartRec.name());
        stopRecButton.setActionCommand(StopRec.name());

        setVisible(true);
    }

    public void repaint() {
        super.repaint();
    }

    public void setController(RecorderController controller) {
        startRecButton.addActionListener(controller);
        stopRecButton.addActionListener(controller);
        gameField.addKeyListener(controller);
    }

    public String getGameField() {
        return gameField.getText();
    }

    public void setGameField(String path) {
        gameField.setText(path);
    }

    public void setPathLabel(String pathLabel) {
        this.pathLabel.setText(pathLabel);
    }

    public JTextArea getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel.setText(statusLabel);
    }
}

