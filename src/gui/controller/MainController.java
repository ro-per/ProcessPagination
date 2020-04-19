package gui.controller;

import gui.model.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class MainController implements Observer {
    /* __________________________________________ MODEL VARIABELEN __________________________________________ */
    private MainModel model;
    //CLOCK
    @FXML
    private Label clkValue;

    // RADIO BUTTONS
    @FXML
    private RadioButton radio303, radio20k4, radio20k20;

    // INSTRUCTION CARDS
    private List<AnchorPane> opList = new ArrayList<>();
    @FXML
    private AnchorPane curOpStart, curOpRead, curOpWrite, curOpTerminate;
    @FXML
    private AnchorPane prevOpStartPane, prevOpReadPane, prevOpWritePane, prevOpTerminatePane;

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


    // __________________________________________ METHODS __________________________________________
    // _________________________ UPDATE _________________________
    @Override
    public void update(Observable o, Object arg) {
        clkValue.setText(String.valueOf(model.getClk()));
        updateInstructionCards();
        updateFrames();
    }

    // INSTRUCTION CARDS
    void updateInstructionCards() {
        updateOpColors();
    }

    void updateOpColors() {
        for (int i = 0; i < 4; i++)
            this.opList.get(i).setStyle(model.getCopColors(i));
        for (int i = 0; i < 4; i++)
            this.opList.get(i + 4).setStyle(model.getPopColors(i));
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
        model.initModel();
        initFileChooser();
        initOpColors();
        initFrames();
    }

    @FXML
    void next(ActionEvent e) {
        model.countCLK();
        model.setFramePIDs();
        model.setFramePNRs();

        model.setOpColors('C', 3);
        model.setOpColors('P', 2);


    }

    @FXML
    void run(ActionEvent e) throws ParserConfigurationException, SAXException, IOException {
        model.initProgram();
        model.runProgram();
    }

    // _________________________ GETTERS _________________________

    // _________________________ SETTERS _________________________
    public void setModel(MainModel model) {
        this.model = model;
        initFileChooser();
        initOpColors();
        initFrames();
    }


    //FILE CHOOSER
    public void initFileChooser() {
        ToggleGroup fileChooser = new ToggleGroup();
        //add to toggle group
        radio303.setToggleGroup(fileChooser);
        radio20k4.setToggleGroup(fileChooser);
        radio20k20.setToggleGroup(fileChooser);
        initSelectionState();

        //ACTION LISTENER
        fileChooser.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            if (fileChooser.getSelectedToggle() != null) {
                model.initModel();
                RadioButton temp = (RadioButton) fileChooser.getSelectedToggle();
                String set = temp.getText();
                model.setXmlSet(set);
                System.out.println(set);

            } else {
                initSelectionState();
            }
        });
    }

    public void initSelectionState() {
        radio303.setSelected(true);
        radio20k4.setSelected(false);
        radio20k20.setSelected(false);
    }

    //INSTRUCTION CARDS
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

    //FRAMES
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


}
