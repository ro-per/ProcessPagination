package gui.controller;

import entities.PageTableEntry;
import entities.Process;
import gui.model.MainMODEL;
import gui.model.PageTableEntryMODEL;
import gui.model.ReadWriteTableEntryMODEL;
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
    private MainMODEL model;
    private TableView<PageTableEntryMODEL> pageTableView;
    private TableView<ReadWriteTableEntryMODEL> readWriteTableView;
    private ObservableList<PageTableEntryMODEL> pageTableENTRIES =
            FXCollections.observableArrayList();
    private List<Label> framePidList = new ArrayList<>();
    private List<AnchorPane> curOperationColorList = new ArrayList<>();
    private List<AnchorPane> prevOperationColorList = new ArrayList<>();
    private List<Label> framePnrList = new ArrayList<>();
    private ObservableList<ReadWriteTableEntryMODEL> readWriteTableENTRIES =
            FXCollections.observableArrayList();
    /* ------------------------------------------ FXML IDs ------------------------------------------ */
    @FXML
    private Label clkValue;
    @FXML
    private RadioButton radio303, radio20k4, radio20k20;
    @FXML
    private Button button_reset, button_run, button_next;
    @FXML
    private AnchorPane curOpStart, curOpRead, curOpWrite, curOpTerminate;
    @FXML
    private Label curPid, curVaddr;
    @FXML
    private AnchorPane prevOpStartPane, prevOpReadPane, prevOpWritePane, prevOpTerminatePane;
    @FXML
    private Label prevPid, prevVaddr, prevPaddr, prevFrameNr, prevOffset;
    @FXML
    private Label outPutMessage;
    @FXML
    private AnchorPane pageTablePane;
    @FXML
    private Label frame0Pid, frame1Pid, frame2Pid, frame3Pid, frame4Pid, frame5Pid;
    @FXML
    private Label frame6Pid, frame7Pid, frame8Pid, frame9Pid, frame10Pid, frame11Pid;
    @FXML
    private Label frame0Pnr, frame1Pnr, frame2Pnr, frame3Pnr, frame4Pnr, frame5Pnr;
    @FXML
    private Label frame6Pnr, frame7Pnr, frame8Pnr, frame9Pnr, frame10Pnr, frame11Pnr;
    @FXML
    private AnchorPane readWriteTablePane;


    /* ------------------------------------------ METHODS ------------------------------------------ */
    // _________________________ UPDATE _________________________
    @Override
    public void update(Observable o, Object arg) {
        clkValue.setText(String.valueOf(model.getClock()));
        outPutMessage.setText(model.getOutPutMessage());
        updateInstructionCards();
        updateFrames();
        updateActionButtons();
        updatePageTable();
        updateReadWriteTable();
    }

    void updateInstructionCards() {
        updateCurrentInstructionCard();
        updatePreviousInstructionCard();
    }

    private void updateCurrentInstructionCard() {
        this.curPid.setText(model.getCurrentProcessID());
        this.curVaddr.setText(model.getCurrentVirtualAddress());
        for (int i = 0; i < 4; i++)
            this.curOperationColorList.get(i).setStyle(model.getCurrentOperationColors(i));
    }

    private void updatePreviousInstructionCard() {
        this.prevPid.setText(model.getPreviousProcessID());
        this.prevVaddr.setText(model.getPreviousVirtualAddress());
        for (int i = 0; i < 4; i++)
            this.prevOperationColorList.get(i).setStyle(model.getPreviousOperationColors(i));

        this.prevPaddr.setText(model.getPreviousPhysicalAddress());
        this.prevFrameNr.setText(model.getPreviousFrameNumber());
        this.prevOffset.setText((model.getPreviousOffset()));

    }

    void updateFrames() {
        updateFramePIDs();
        updateFramePNRs();
    }

    void updateFramePIDs() {
        for (int i = 0; i < Main.AMOUNT_OF_FRAMES; i++)
            this.framePidList.get(i).setText(String.valueOf(model.getFrameProcessID(i)));
    }

    void updateFramePNRs() {
        for (int i = 0; i < Main.AMOUNT_OF_FRAMES; i++)
            this.framePnrList.get(i).setText(String.valueOf(model.getFramePageNumber(i)));
    }

    public void updateActionButtons() {
        button_reset.setDisable(model.getButtonsDisabled(0));
        button_run.setDisable(model.getButtonsDisabled(1));
        button_next.setDisable(model.getButtonsDisabled(2));
    }

    public void updatePageTable() {
        pageTableENTRIES = FXCollections.observableArrayList();
        pageTableView.refresh();

        int x = 0;
        for (int i = 0; i < PAGE_TABLE_LENGTH; i++) {
            PageTableEntry e = model.getPageTable().getEntry(i);
            String pid, pb, mb, lta, fn;
            pid = String.valueOf(x);
            pb = e.getIsPresentString();
            mb = e.getIsModifiedString();
            lta = String.valueOf(e.getLastAccessTime());
            fn = String.valueOf(e.getFrameNumber());
            pageTableENTRIES.add(new PageTableEntryMODEL(pid, pb, mb, lta, fn));
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
                readWriteTableENTRIES.add(new ReadWriteTableEntryMODEL(pid, r, w));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        readWriteTableView.setItems(readWriteTableENTRIES);
    }

    /* ------------------------------------------ BUTTON ACTIONS ------------------------------------------ */
    @FXML
    void reset(ActionEvent e) {
        model.initModel();
        setFileChooser();
        setOpColors();
        setFrames();
        model.setRadioButtonsDisabled(true);
    }

    @FXML
    void stepManual(ActionEvent e) {
        model.stepManualProgram();
        outPutMessage.setText(model.getOutPutMessage());
    }

    @FXML
    void run(ActionEvent e) {
        model.runProgram();
        outPutMessage.setText(model.getOutPutMessage());
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

    public void setFileChooser() {
        ToggleGroup fileChooser = new ToggleGroup();
        radio303.setToggleGroup(fileChooser);
        radio20k4.setToggleGroup(fileChooser);
        radio20k20.setToggleGroup(fileChooser);
        radio303.setSelected(false);
        radio20k4.setSelected(false);
        radio20k20.setSelected(false);
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
                    model.refresh();
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

    /* ------------------------------------------ INIT & SETTERS ------------------------------------------ */
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

    public void initPageTable() {
        pageTableView = new TableView<>();
        pageTableView.setEditable(true);

        TableColumn pageCOL = new TableColumn("Page");
        pageCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        pageCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableEntryMODEL, String>("page"));
        pageCOL.setSortable(false);

        TableColumn presentBitCOL = new TableColumn("Present Bit");
        presentBitCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        presentBitCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableEntryMODEL, String>("presentBit"));
        presentBitCOL.setSortable(false);

        TableColumn modifyBitCOL = new TableColumn("Modify Bit");
        modifyBitCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        modifyBitCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableEntryMODEL, String>("modifyBit"));
        modifyBitCOL.setSortable(false);

        TableColumn lastTimeAccessedCOL = new TableColumn("Last Time Accessed");
        lastTimeAccessedCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        lastTimeAccessedCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableEntryMODEL, String>("lastTimeAccessed"));
        lastTimeAccessedCOL.setSortable(false);

        TableColumn frameNumberCOL = new TableColumn("Frame Number");
        frameNumberCOL.setMinWidth(PAGE_TABLE_COLUMN_WIDTH);
        frameNumberCOL.setCellValueFactory(
                new PropertyValueFactory<PageTableEntryMODEL, String>("frameNumber"));
        frameNumberCOL.setSortable(false);

        pageTableView.getColumns().addAll(pageCOL, presentBitCOL, modifyBitCOL, lastTimeAccessedCOL, frameNumberCOL);
        pageTableView.setMinSize(5 * PAGE_TABLE_COLUMN_WIDTH, PAGE_TABLE_HEIGHT);
        pageTablePane.getChildren().add(pageTableView);
    }

    public void initReadWriteTable() {
        readWriteTableView = new TableView<ReadWriteTableEntryMODEL>();
        readWriteTableView.setEditable(true);

        TableColumn pidCOL = new TableColumn("PID");
        pidCOL.setMinWidth(READ_WRITE_TABLE_COLUMN_WIDTH);
        pidCOL.setCellValueFactory(
                new PropertyValueFactory<ReadWriteTableEntryMODEL, String>("processID"));
        pidCOL.setSortable(false);

        TableColumn readCOL = new TableColumn("READ");
        readCOL.setMinWidth(READ_WRITE_TABLE_COLUMN_WIDTH);
        readCOL.setCellValueFactory(
                new PropertyValueFactory<ReadWriteTableEntryMODEL, String>("readCount"));
        readCOL.setSortable(false);

        TableColumn writeCOL = new TableColumn("WRITE");
        writeCOL.setMinWidth(READ_WRITE_TABLE_COLUMN_WIDTH);
        writeCOL.setCellValueFactory(
                new PropertyValueFactory<ReadWriteTableEntryMODEL, String>("writeCount"));
        writeCOL.setSortable(false);

        readWriteTableView.getColumns().addAll(pidCOL, readCOL, writeCOL);
        readWriteTableView.setMinSize(3 * READ_WRITE_TABLE_COLUMN_WIDTH, PAGE_TABLE_HEIGHT);
        readWriteTablePane.getChildren().add(readWriteTableView);
    }

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
