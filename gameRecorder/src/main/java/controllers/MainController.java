package controllers;

import views.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {
    private final JFrame view;
    private String status;

    public MainController(MainView mainView) {
        view = mainView;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("OK")) {
            status = "Ok Button clicked.";
        } else if (command.equals("Submit")) {
            status = "Submit Button clicked.";
        } else {
            status = "Cancel Button clicked.";
        }
        view.repaint();
    }

    public String getStatus() {
        return status;
    }
}
