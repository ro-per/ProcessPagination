package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import gui.model.MainModel;

import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer {

    private MainModel model;

    public void setModel(MainModel model) {
        this.model = model;
    }

    //************************** LABELS **************************
    @FXML
    private Label clockField;
    @FXML
    private ProgressBar progressBar;


    //************************** UPDATE **************************

    @Override
    public void update(Observable o, Object arg) {
        clockField.setText(String.valueOf(model.getClock()));
    }




    //************************** Frames **************************




}
