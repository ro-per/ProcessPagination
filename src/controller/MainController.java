package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.MainModel;

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
    //************************** MENU-FILE **************************

    @FXML
    public void openFileChooser(ActionEvent event) {

    }

    @FXML
    public void save(ActionEvent event) {

    }

    @FXML
    public void saveAs(ActionEvent event) {

    }

    @FXML
    public void quitApplication(ActionEvent event) {

    }

    //************************** MENU-RESET **************************
    @FXML
    public void reset(ActionEvent event) {
        model.setClock(Long.valueOf(0));
    }

    //************************** ABOUT **************************
    @FXML
    public void openGithub(ActionEvent event) {

    }

    //************************** MASTER-BUTTONS **************************
    @FXML
    public void previous(ActionEvent event) {
        model.countClockDown();

    }

    @FXML
    public void pause(ActionEvent event) {

    }

    @FXML
    public void next(ActionEvent event) {
        model.countClockUp();

    }
}
