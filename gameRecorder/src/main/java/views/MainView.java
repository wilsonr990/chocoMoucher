package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static controllers.MainController.Actions.SetPath;
import static controllers.MainController.Actions.StartRec;
import static controllers.MainController.Actions.StopRec;

public class MainView extends JFrame {
    private ActionListener controller;
    private JButton setPathButton;
    private JButton startRecButton;
    private JButton stopRecButton;
    private JPanel mainPanel;
    private JTextField pathLabel;

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

        setPathButton.setActionCommand(SetPath.name());
        startRecButton.setActionCommand(StartRec.name());
        stopRecButton.setActionCommand(StopRec.name());

        setVisible(true);
    }

    public void repaint() {
        super.repaint();
    }

    public void setController(ActionListener controller) {
        this.controller = controller;
        setPathButton.addActionListener(controller);
        startRecButton.addActionListener(controller);
        stopRecButton.addActionListener(controller);
    }

    public String getPathLabel() {
        return pathLabel.getText();
    }
}

