package views;

import controllers.AnalyzerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static controllers.AnalyzerController.Actions.*;

public class AnalyzerView extends JFrame {
    private JButton startRecButton;
    private JButton stopRecButton;
    private JPanel mainPanel;

    public AnalyzerView() {
        setTitle("Game Recorder");
        setSize(400, 400);
        setLayout(new GridLayout(3, 1));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        add(mainPanel);

        startRecButton.setActionCommand(StartAnalysis.name());
        stopRecButton.setActionCommand(StopAnalysis.name());

        setVisible(true);
    }

    public void repaint() {
        super.repaint();
    }

    public void setController(AnalyzerController controller) {
        startRecButton.addActionListener(controller);
        stopRecButton.addActionListener(controller);
    }
}

