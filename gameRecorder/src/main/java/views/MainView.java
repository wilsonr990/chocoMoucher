package views;

import controllers.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static controllers.MainController.Actions.StartRec;
import static controllers.MainController.Actions.StopRec;

public class MainView extends JFrame {
    private JButton startRecButton;
    private JButton stopRecButton;
    private JPanel mainPanel;
    private JTextField gameLabel;
    private JLabel pathLabel;

    public MainView() {
        setTitle("Game Recorder");
        setSize(400, 400);
        setLayout(new GridLayout(3, 1));

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

    public void setController(MainController controller) {
        startRecButton.addActionListener(controller);
        stopRecButton.addActionListener(controller);
        gameLabel.addKeyListener(controller);
    }

    public String getGameLabel() {
        return gameLabel.getText();
    }

    public void setGameLabel(String path) {
        gameLabel.setText( path );
    }

    public void setPathLabel(String pathLabel) {
        this.pathLabel.setText( pathLabel );
    }
}

