package gui.controller;

import entities.PageTableEntry;
import entities.PageTable;
import entities.Process;
import entities.ReadWriteTable;
import entities.ReadWriteTableEntry;
import gui.model.MainMODEL;
import gui.model.PageTableENTRY;
import gui.model.RWTableENRTY;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.Main;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static main.Main.*;


public class MainCONTROLLER implements Observer {

    /* ------------------------------------------ MODEL ATTRIBUTES ------------------------------------------ */
    private MainMODEL model;
    private int x, y;
    /* ------------------------------------------ VIEW ATTRIBUTES ------------------------------------------ */
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
    @FXML
    private AnchorPane pageTablePane;
    TableView<PageTableENTRY> pageTableView;
    private ObservableList<PageTableENTRY> pageTableENTRIES =
            FXCollections.observableArrayList();
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
    @FXML
    private AnchorPane readWriteTablePane;
    TableView<RWTableENRTY> readWriteTableView;

    private ObservableList<RWTableENRTY> readWriteTableENTRIES =
            FXCollections.observableArrayList();

    /* ------------------------------------------ METHODS ------------------------------------------ */
    // _________________________ UPDATE _________________________
    @Override
    public void update(Observable o, Object arg) {
        clkValue.setText(String.valueOf(model.getClock()));
        updateInstructionCards();
        updateFrames();
        updateActionButtons();
        updatePageTable();
        updateReadWriteTable();
    }

    public void updatePageTable() {
        pageTableENTRIES = FXCollections.observableArrayList();
        pageTableView.refresh();

        x = 0;
        for (int i = 0; i < PAGE_TABLE_LENGTH; i++) {
            PageTableEntry e = model.getPageTable().getEntry(i);

            String pid, pb, mb, lta, fn;
            pid = String.valueOf(x);
            pb = e.getIsPresentString();
            mb = e.getIsModifiedString();
            lta = String.valueOf(e.getLastAccessTime());
            fn = String.valueOf(e.getFrameNumber());
            pageTableENTRIES.add(new PageTableENTRY(pid, pb, mb, lta, fn));
            x++;
        }
        pageTableView.setItems(pageTableENTRIES);

    }

    public void updateReadWriteTable() {
        readWriteTableENTRIES = FXCollections.observableArrayList();
        readWriteTableView.refresh();
        try {
            List<Process> processes = model.getProcesses();
            for (int i = 0; i < processes.size(); i++) {
                int pid = processes.get(i).getProcessID();
                int r = processes.get(i).getReadCount();
                int w = processes.get(i).getWriteCount();
                readWriteTableENTRIES.add(new RWTableENRTY(pid, r, w));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        readWriteTableView.setItems(readWriteTableENTRIES);
    }

    public void updateActionButtons() {
        button_reset.setDisable(model.getButtonsDisabled(0));
        button_run.setDisable(model.getButtonsDisabled(1));
        button_next.setDisable(model.getButtonsDisabled(2));

    }

    // INSTRUCTION CARDS
    void updateInstructionCards() {
        updateCurrentInstructionCard();
        updatePreviousInstructionCard();
    }

    private void updateCurrentInstructionCard() {
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
        for (int i = 0; i < Main.AMOUNT_OF_FRAMES; i++)
            this.framePidList.get(i).setText(String.valueOf(model.getFramePid(i)));
    }

    void updateFramePNRs() {
        for (int i = 0; i < Main.AMOUNT_OF_FRAMES; i++)
            this.framePnrList.get(i).setText(String.valueOf(model.getFramePnr(i)));
    }

    /* ------------------------------------------ BUTTON ACTIONS ------------------------------------------ */
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
    void stepManual(ActionEvent e) {
        System.out.println("++++++++++++ NEXT ++++++++++++");
        model.stepManualProgram();
    }

    @FXML
    void run(ActionEvent e) {
        System.out.println("++++++++++++ RUN ++++++++++++");
        model.runProgram();
    }

    /* ------------------------------------------ SETTERS ------------------------------------------ */
    public void setModel(MainMODEL model) {
        this.model = model;
        model.setRadioButtonsDisabled(true);
        setFileChooser();
        setOpColors();
        setFrames();
        initPageTable();
        initReadWriteTable();
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

    // PAGE TABLE
    public void initPageTable() {
        pageTableView = new TableView<PageTableENTRY>();

        pageTableView.setEditable(true);

        TableColumn pageCOL = new TableColumn("Page");
        pageCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        pageCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("page"));
        pageCOL.setSortable(false);

        TableColumn presentBitCOL = new TableColumn("Present Bit");
        presentBitCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        presentBitCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("presentBit"));
        presentBitCOL.setSortable(false);

        TableColumn modifyBitCOL = new TableColumn("Modify Bit");
        modifyBitCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        modifyBitCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("modifyiBt"));
        modifyBitCOL.setSortable(false);

        TableColumn lastTimeAccessedCOL = new TableColumn("Last Time Accessed");
        lastTimeAccessedCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        lastTimeAccessedCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("lastTimeAccessed"));
        lastTimeAccessedCOL.setSortable(false);

        TableColumn frameNumberCOL = new TableColumn("Frame Number");
        frameNumberCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        frameNumberCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableENTRY, String>("frameNumber"));
        frameNumberCOL.setSortable(false);

        pageTableView.getColumns().addAll(pageCOL, presentBitCOL, modifyBitCOL, lastTimeAccessedCOL, frameNumberCOL);
        pageTableView.setMinSize(5 * PAGE_TABLE_COLUMN_WIDTH, PAGE_TABLE_HEIGHT);
        pageTablePane.getChildren().add(pageTableView);

    }

    //READ WRITE TABLE
    public void initReadWriteTable() {
        readWriteTableView = new TableView<RWTableENRTY>();

        readWriteTableView.setEditable(true);

        TableColumn pidCOL = new TableColumn("PID");
        pidCOL.setMinWidth(READ_WRITE_TABLE_COLUMN_WIDTH);
        pidCOL.setCellValueFactory(
                new PropertyValueFactory<RWTableENRTY, String>("processID"));
        pidCOL.setSortable(false);

        TableColumn readCOL = new TableColumn("READ");
        readCOL.setMinWidth(READ_WRITE_TABLE_COLUMN_WIDTH);
        readCOL.setCellValueFactory(
                new PropertyValueFactory<RWTableENRTY, String>("readCount"));
        readCOL.setSortable(false);

        TableColumn writeCOL = new TableColumn("WRITE");
        writeCOL.setMinWidth(READ_WRITE_TABLE_COLUMN_WIDTH);
        writeCOL.setCellValueFactory(
                new PropertyValueFactory<RWTableENRTY, String>("writeCount"));
        writeCOL.setSortable(false);


        readWriteTableView.getColumns().addAll(pidCOL, readCOL, writeCOL);
        readWriteTableView.setMinSize(3 * READ_WRITE_TABLE_COLUMN_WIDTH, PAGE_TABLE_HEIGHT);

        readWriteTablePane.getChildren().add(readWriteTableView);
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
