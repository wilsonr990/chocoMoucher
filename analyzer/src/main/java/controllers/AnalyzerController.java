package controllers;

import models.Analyzer;
import views.AnalyzerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzerController implements ActionListener {
    public enum Actions {
        StartAnalysis, StopAnalysis
    }

    private AnalyzerView mainView;
    private Analyzer analyzer;

    public AnalyzerController(AnalyzerView view, Analyzer model) {
        mainView = view;
        analyzer = model;

        updateView();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(Actions.StartAnalysis.name())) {
            analyzer.startAnalysis();
        } else if (command.equals(Actions.StopAnalysis.name())) {
            analyzer.stopAnalysis();
        }
        mainView.repaint();
    }

    private void updateView() {}
}
