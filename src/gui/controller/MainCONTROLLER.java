package gui.controller;

import gui.model.MainMODEL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static main.Main.FRAME_NUMBER;

public class MainCONTROLLER implements Observer {
    /* __________________________________________ MODEL VARIABLES __________________________________________ */
    private MainMODEL model;
    /* __________________________________________ VIEW VARIABLES __________________________________________ */
    // _________________________ KOLOM 1 _________________________
    //CLOCK
    @FXML
    private Label clkValue;
    // RADIO BUTTONS
    @FXML
    private RadioButton radio303, radio20k4, radio20k20;
    //ACTION BUTTONS
    @FXML
    private Button button_reset, button_run, button_next;
    // CURRENT INSTRUCTION CARD
    private List<AnchorPane> curOperationColorList = new ArrayList<>();
    @FXML
    private AnchorPane curOpStart, curOpRead, curOpWrite, curOpTerminate;
    @FXML
    private Label curPid, curVaddr;

    // PREVIOUS INSTRUCTION CARD
    private List<AnchorPane> prevOperationColorList = new ArrayList<>();
    @FXML
    private AnchorPane prevOpStartPane, prevOpReadPane, prevOpWritePane, prevOpTerminatePane;
    @FXML
    private Label prevPid, prevVaddr, prevPaddr, prevFrameNr, prevOffset;
    // _________________________ KOLOM 2 _________________________

    // _________________________ KOLOM 3 _________________________
    // FRAMES - IDs
    private List<Label> framePidList = new ArrayList<>();
    @FXML
    private Label frame0Pid, frame1Pid, frame2Pid, frame3Pid, frame4Pid, frame5Pid;
    @FXML
    private Label frame6Pid, frame7Pid, frame8Pid, frame9Pid, frame10Pid, frame11Pid;
    // FRAMES - PNRs
    private List<Label> framePnrList = new ArrayList<>();
    @FXML
    private Label frame0Pnr, frame1Pnr, frame2Pnr, frame3Pnr, frame4Pnr, frame5Pnr;
    @FXML
    private Label frame6Pnr, frame7Pnr, frame8Pnr, frame9Pnr, frame10Pnr, frame11Pnr;
    // _________________________ KOLOM 4 _________________________


    // __________________________________________ METHODS __________________________________________
    // _________________________ UPDATE _________________________
    @Override
    public void update(Observable o, Object arg) {
        clkValue.setText(String.valueOf(model.getClock()));
        updateInstructionCards();
        updateFrames();
        updateActionButtons();
    }

    public void updateActionButtons() {
        button_reset.setDisable(model.getButtonsDisabled(0));
        button_run.setDisable(model.getButtonsDisabled(1));
        button_next.setDisable(model.getButtonsDisabled(2));

    }

    // INSTRUCTION CARDS
    void updateInstructionCards() {
        updateCurrentInstructionVIEW();
        updatePreviousInstructionCard();
    }

    private void updateCurrentInstructionVIEW() {
        this.curPid.setText(model.getCurPid());
        this.curVaddr.setText(model.getCurVaddr());
        for (int i = 0; i < 4; i++)
            this.curOperationColorList.get(i).setStyle(model.getCopColors(i));
    }

    private void updatePreviousInstructionCard() {
        this.prevPid.setText(model.getPrevPid());
        this.prevVaddr.setText(model.getPrevVaddr());
        for (int i = 0; i < 4; i++)
            this.prevOperationColorList.get(i).setStyle(model.getPopColors(i));
        this.prevPaddr.setText(model.getPrevPaddr());
        this.prevFrameNr.setText(model.getPrevFrameNr());
        this.prevOffset.setText((model.getPrevOffset()));

    }

    //FRAMES
    void updateFrames() {
        updateFramePIDs();
        updateFramePNRs();
    }

    void updateFramePIDs() {
        for (int i = 0; i < FRAME_NUMBER; i++)
            this.framePidList.get(i).setText(String.valueOf(model.getFramePid(i)));
    }

    void updateFramePNRs() {
        for (int i = 0; i < FRAME_NUMBER; i++)
            this.framePnrList.get(i).setText(String.valueOf(model.getFramePnr(i)));
    }

    // _________________________ BUTTON-ACTIONS _________________________
    @FXML
    void reset(ActionEvent e) {
        System.out.println("++++++++++++ RESET ++++++++++++");
        model.initModel();
        setFileChooser();
        setOpColors();
        setFrames();
        model.setRadioButtonsDisabled(true);
    }

    @FXML
    void next(ActionEvent e) {
        System.out.println("++++++++++++ NEXT ++++++++++++");
        model.stepProgram();
    }

    @FXML
    void run(ActionEvent e) {
        System.out.println("++++++++++++ RUN ++++++++++++");
        model.runProgram();
    }

    // _________________________ SETTERS _________________________
    public void setModel(MainMODEL model) {
        this.model = model;
        model.setRadioButtonsDisabled(true);
        setFileChooser();
        setOpColors();
        setFrames();
    }

    //FILE CHOOSER
    public void setFileChooser() {
        ToggleGroup fileChooser = new ToggleGroup();
        //add to toggle group
        radio303.setToggleGroup(fileChooser);
        radio20k4.setToggleGroup(fileChooser);
        radio20k20.setToggleGroup(fileChooser);
        radio303.setSelected(false);
        radio20k4.setSelected(false);
        radio20k20.setSelected(false);
        //ACTION LISTENER
        fileChooser.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            if (fileChooser.getSelectedToggle() != null) {
                model.initModel();
                RadioButton temp = (RadioButton) fileChooser.getSelectedToggle();
                String set = temp.getText();
                model.setXmlFile(set);
                System.out.println(set); //TODO
                model.setRadioButtonsDisabled(false);
                try {
                    model.initExecution();
                } catch (IOException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            } else {
                radio303.setSelected(true);
                radio20k4.setSelected(false);
                radio20k20.setSelected(false);
            }
        });
    }

    //INSTRUCTION CARDS
    public void setOpColors() {
        initCurOpColors();
        initPrevOpColors();
    }

    public void initCurOpColors() {
        curOperationColorList.add(curOpStart);
        curOperationColorList.add(curOpRead);
        curOperationColorList.add(curOpWrite);
        curOperationColorList.add(curOpTerminate);
    }

    public void initPrevOpColors() {
        prevOperationColorList.add(prevOpStartPane);
        prevOperationColorList.add(prevOpReadPane);
        prevOperationColorList.add(prevOpWritePane);
        prevOperationColorList.add(prevOpTerminatePane);
    }

    //FRAMES
    public void setFrames() {
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
}
