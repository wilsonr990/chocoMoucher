package views;

import controllers.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by wilsonr on 1/16/2016.
 */
public class MainView extends JFrame {
    private final MainController controller;
    private JButton okButton;
    private JButton submitButton;
    private JButton cancelButton;
    private JLabel status;
    private JPanel mainPanel;

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
        controller = new MainController(this);
    }

    public void start() {
        status.setText("Control in action: Button");

        okButton.setActionCommand("OK");
        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        okButton.addActionListener(controller);
        submitButton.addActionListener(controller);
        cancelButton.addActionListener(controller);

        setVisible(true);
    }

    public void repaint() {
        status.setText(controller.getStatus());
        super.repaint();
    }
}

