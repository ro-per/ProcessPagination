package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import gui.model.MainModel;
import javafx.scene.layout.AnchorPane;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static main.MainNew.FRAMENUMBER;

public class MainController implements Observer {

    private MainModel model;
    /* ******************** CLK ******************** */
    @FXML
    private Label clkValue;

    /* ******************** FRAMES - IDs ******************** */
    private List<Label> framePidList = new ArrayList<>();

    @FXML
    private Label frame0Pid;
    @FXML
    private Label frame1Pid;
    @FXML
    private Label frame2Pid;
    @FXML
    private Label frame3Pid;
    @FXML
    private Label frame4Pid;
    @FXML
    private Label frame5Pid;
    @FXML
    private Label frame6Pid;
    @FXML
    private Label frame7Pid;
    @FXML
    private Label frame8Pid;
    @FXML
    private Label frame9Pid;
    @FXML
    private Label frame10Pid;
    @FXML
    private Label frame11Pid;

    /* ******************** FRAMES - PNRs ******************** */
    private List<Label> framePnrList = new ArrayList<>();
    @FXML
    private Label frame0Pnr;
    @FXML
    private Label frame1Pnr;
    @FXML
    private Label frame2Pnr;
    @FXML
    private Label frame3Pnr;
    @FXML
    private Label frame4Pnr;
    @FXML
    private Label frame5Pnr;
    @FXML
    private Label frame6Pnr;
    @FXML
    private Label frame7Pnr;
    @FXML
    private Label frame8Pnr;
    @FXML
    private Label frame9Pnr;
    @FXML
    private Label frame10Pnr;
    @FXML
    private Label frame11Pnr;

    /* ******************** INSTRUCTION OPERATIONs ******************** */
    private String opGREEN = "-fx-background-color: CHARTREUSE;";
    private String opRED = "-fx-background-color: firebrick;";

    private List<AnchorPane> opList = new ArrayList<>();
    @FXML
    private AnchorPane curOpStart;
    @FXML
    private AnchorPane curOpRead;
    @FXML
    private AnchorPane curOpWrite;
    @FXML
    private AnchorPane curOpTerminate;
    @FXML
    private AnchorPane prevOpStartPane;
    @FXML
    private AnchorPane prevOpReadPane;
    @FXML
    private AnchorPane prevOpWritePane;
    @FXML
    private AnchorPane prevOpTerminatePane;


    /* ******************** UPDATE ******************** */
    @Override
    public void update(Observable o, Object arg) {
        clkValue.setText(String.valueOf(model.getClk()));
        updateInstructionCards();
        updateFrames();
    }

    void updateInstructionCards() {
        updateOpColors();
    }

    void updateOpColors() {
        for (int i = 0; i < 4; i++)
            this.opList.get(i).setStyle(model.getCopColors(i));
        for (int i = 0; i < 4; i++)
            this.opList.get(i + 4).setStyle(model.getPopColors(i));
    }

    void updateFrames() {
        updateFramePIDs();
        updateFramePNRs();
    }

    void updateFramePIDs() {
        for (int i = 0; i < FRAMENUMBER; i++)
            this.framePidList.get(i).setText(String.valueOf(model.getFramePid(i)));
    }

    void updateFramePNRs() {
        for (int i = 0; i < FRAMENUMBER; i++)
            this.framePnrList.get(i).setText(String.valueOf(model.getFramePnr(i)));
    }
    //ANCHOR_PROCESSES

    /* ******************** ACTIONS ******************** */
    //MENU

    //ANCHOR_LEFT
    @FXML
    void reset(ActionEvent e) throws ParserConfigurationException, SAXException, IOException {
        model.resetModel();
    }

    @FXML
    void next(ActionEvent e) {
        model.countCLK();
        model.setFramePIDs();
        model.setFramePNRs();
    }

    @FXML
    void run(ActionEvent e) {
        model.setOpColors('C', 3);
        model.setOpColors('P', 2);

    }
    /* ******************** GETTERS ******************** */
    //ANCHOR_LEFT

    //ANCHOR_PAGES

    //ANCHOR_FRAMES

    //ANCHOR_PROCESSES

    /* ******************** SETTERS ******************** */
    public void setModel(MainModel model) {
        this.model = model;
        initFrames();
        initOpColors();

    }

    //ANCHOR_LEFT
    public void initOpColors() {
        initCurOpColors();
        initPrevOpColors();
    }

    public void initCurOpColors() {
        opList.add(curOpStart);
        opList.add(curOpRead);
        opList.add(curOpWrite);
        opList.add(curOpTerminate);
    }

    public void initPrevOpColors() {
        opList.add(prevOpStartPane);
        opList.add(prevOpReadPane);
        opList.add(prevOpWritePane);
        opList.add(prevOpTerminatePane);
    }

    //ANCHOR_PAGES

    //ANCHOR_FRAMES
    public void initFrames() {
        initFramePIDs();
        initFramePNRs();
    }

    private void initFramePIDs() {
        framePidList.add(frame0Pid);
        framePidList.add(frame1Pid);
        framePidList.add(frame2Pid);
        framePidList.add(frame3Pid);
        framePidList.add(frame4Pid);
        framePidList.add(frame5Pid);
        framePidList.add(frame6Pid);
        framePidList.add(frame7Pid);
        framePidList.add(frame8Pid);
        framePidList.add(frame9Pid);
        framePidList.add(frame10Pid);
        framePidList.add(frame11Pid);
    }

    private void initFramePNRs() {
        framePnrList.add(frame0Pnr);
        framePnrList.add(frame1Pnr);
        framePnrList.add(frame2Pnr);
        framePnrList.add(frame3Pnr);
        framePnrList.add(frame4Pnr);
        framePnrList.add(frame5Pnr);
        framePnrList.add(frame6Pnr);
        framePnrList.add(frame7Pnr);
        framePnrList.add(frame8Pnr);
        framePnrList.add(frame9Pnr);
        framePnrList.add(frame10Pnr);
        framePnrList.add(frame11Pnr);

    }
    //ANCHOR_PROCESSES
}
