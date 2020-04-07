package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import gui.model.MainModel;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer {

    private MainModel model;

    public void setModel(MainModel model) {
        this.model = model;
    }

    //************************** LABELS **************************
    @FXML
    private Label clk_value;

    /**
     * ************************ FRAMES *************************
     **/
    @FXML
    private Label frame0_pid;
    @FXML
    private Label frame0_pageNr;
    @FXML
    private Label frame1_pid;
    @FXML
    private Label frame1_pageNr;
    @FXML
    private Label frame2_pid;
    @FXML
    private Label frame2_pageNr;
    @FXML
    private Label frame3_pid;
    @FXML
    private Label frame3_pageNr;
    @FXML
    private Label frame4_pid;
    @FXML
    private Label frame4_pageNr;
    @FXML
    private Label frame5_pid;
    @FXML
    private Label frame5_pageNr;


    //************************** UPDATE **************************

    @Override
    public void update(Observable o, Object arg) {
        clk_value.setText(String.valueOf(model.getClock()));
        frame0_pid.setText(String.valueOf(model.getFramePid(0)));
        frame1_pid.setText(String.valueOf(model.getFramePid(1)));

    }


    @FXML
    void reset(ActionEvent e) throws ParserConfigurationException, SAXException, IOException {
        model.init();
    }

    @FXML
    void next(ActionEvent e) {
        model.countClockUp();
    }

    //************************** Frames **************************


}
