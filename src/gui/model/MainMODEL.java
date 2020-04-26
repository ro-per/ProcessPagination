package gui.model;

import entities.Process;
import entities.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static main.Main.RADIOBUTTON_NUMBER;


public class MainMODEL extends Observable {
    /* ------------------------------------------ ATTRIBUTES ------------------------------------------ */
    // _________________________ ENTITIES _________________________
    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;
    private Instruction currentInstruction;
    private Instruction previousInstruction;
    private PageTable pageTable = new PageTable();
    private ReadWriteTable readWriteTable = new ReadWriteTable();

    // _________________________ MODEL VARIABLES _________________________
    private int clock;
    private List<Boolean> radioButtons = new ArrayList<>();
    private String xmlFile;
    private InstructionMODEL curInstrCARD = new InstructionMODEL();
    private InstructionMODEL prevInstrCARD = new InstructionMODEL();
    private FramesMODEL frames = new FramesMODEL();
    private String outPutMessage;

    /* ------------------------------------------ CONSTRUCTORS ------------------------------------------ */
    public MainMODEL() {
        initModel();
    }

    /* ------------------------------------------ INIT ------------------------------------------ */
    public void initModel() {
        this.clock = 0;
        initRadioButtons();
        initInstrCards();
        frames = new FramesMODEL();
        outPutMessage = "O";
        refresh();
    }

    public void initRadioButtons() {
        radioButtons.clear();
        for (int i = 0; i < RADIOBUTTON_NUMBER; i++) {
            radioButtons.add(false);
        }
    }

    public void initInstrCards() {
        curInstrCARD = new InstructionMODEL();
        prevInstrCARD = new InstructionMODEL();
    }

    /* ------------------------------------------ PROGRAM EXECUTION ------------------------------------------ */
    // _________________________ RUN _________________________
    public void initExecution() throws IOException, SAXException, ParserConfigurationException {
        ram = new Ram(new LruStrategy());
        instructionList = InstructionReader.getInstance().readInstructions(this.xmlFile);
        timer = 0;

    }


    public void runProgram() {
        while (timer < instructionList.size()) {
            currentInstruction = instructionList.get(timer);
            executeCurrentInstruction();
            countTimer();
            countClock();
        }
        outPutMessage = "Terminated " + this.xmlFile + ".xml set";

        setRadioButtonsDisabled(true);
        updateMODEL();
    }

    public void stepManualProgram() {
        if (timer < instructionList.size()) {
            //CURRENT INSTRUCTION
            currentInstruction = instructionList.get(timer);
            executeCurrentInstruction();
            //PREVIOUS INSTRUCTION
            previousInstruction = new Instruction(0, 0, "none", 0, 0, 0);
            if (timer > 0) {
                previousInstruction = instructionList.get(timer - 1);
            }
            countTimer();
            updateMODEL();
            countClock();
        } else {
            setRadioButtonsDisabled(true);
            outPutMessage = "Terminated" + this.xmlFile + ".xml set";
            refresh();
        }
    }

    // _________________________ EXECUTE OPERATION _________________________
    public void executeCurrentInstruction() {
        String operation = currentInstruction.getOpString();
        switch (operation) {
            case "Read":
                read();
                break;
            case "Write":
                write();
                break;
            case "Start":
                start();
                break;
            case "Terminate":
                terminate();
                break;
            default:
                outPutMessage = "INSTRUCTIE " + operation + " IS GEEN GELDIGE INSTRUCTIE";
                break;
        }
        refresh();
    }

    private void setInstrParam() {
        currentInstruction.setPhysicalAddress(ram.getPhysicalAddress());
        currentInstruction.setFrameNumber(ram.getFrameNumber());
    }

    public void setRWTable() {
        int p = ram.getCurrentProcess().getProcessID();
        int r = ram.getCurrentProcess().getReadCount();
        int w = ram.getCurrentProcess().getWriteCount();
        readWriteTable.updateCount(p, r, w);
    }

    public void read() {
        ram.read(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
        setInstrParam();
        setRWTable();
    }

    public void write() {
        ram.write(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
        setInstrParam();
        setRWTable();
    }

    public void start() {
        entities.Process currentProcess = new Process(currentInstruction.getPID());
        ram.addProcess(currentProcess);
    }

    public void terminate() {
        ram.removeProcess(currentInstruction.getPID());
    }

    /* ------------------------------------------ REFRESH ------------------------------------------ */
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /* ------------------------------------------ UPDATE ------------------------------------------ */
    private void updateMODEL() {
        this.curInstrCARD.setProcessID(currentInstruction.getPID());
        this.curInstrCARD.setVirtualAddress(currentInstruction.getVirtualAddress());
        this.curInstrCARD.setOpColor(currentInstruction.getOpInt());
        this.prevInstrCARD.setProcessID(previousInstruction.getPID());
        this.prevInstrCARD.setVirtualAddress(previousInstruction.getVirtualAddress());
        this.prevInstrCARD.setOpColor(previousInstruction.getOpInt());
        this.prevInstrCARD.setPhysicalAddress(previousInstruction.getPhysicalAddress());
        this.prevInstrCARD.setFrameNumber(previousInstruction.getFrameNumber());
        this.prevInstrCARD.setOffset(previousInstruction.getOffset());
        this.pageTable = ram.getCurrentProcess().getPageTable();
        this.frames.setFrames(ram.getPresentPages());
        refresh();
    }

    /* ------------------------------------------ GETTERS AND SETTERS ------------------------------------------ */
    public int getClock() {
        return clock;
    }

    public boolean getButtonsDisabled(int i) {
        return radioButtons.get(i);
    }

    public void setRadioButtonsDisabled(Boolean bool) {
        for (int i = 0; i < RADIOBUTTON_NUMBER; i++) {
            radioButtons.set(i, bool);
        }
        refresh();
    }

    public int getFrameProcessID(int i) {
        return frames.getFrameProcessID(i);
    }

    public int getFramePageNumber(int i) {
        return frames.getFrameProcessNR(i);
    }

    public String getCurrentProcessID() {
        return String.valueOf(this.curInstrCARD.getProcessID());
    }

    public String getCurrentVirtualAddress() {
        return String.valueOf(this.curInstrCARD.getVirtualAddress());
    }

    public String getCurrentOperationColors(int i) {
        return curInstrCARD.getOperationColor(i);
    }

    public String getPreviousProcessID() {
        return String.valueOf(this.prevInstrCARD.getProcessID());
    }

    public String getPreviousVirtualAddress() {
        return String.valueOf(this.prevInstrCARD.getVirtualAddress());
    }

    public String getPreviousOperationColors(int i) {
        return prevInstrCARD.getOperationColor(i);
    }

    public String getPreviousPhysicalAddress() {
        return String.valueOf(this.prevInstrCARD.getPhysicalAddress());
    }

    public String getPreviousFrameNumber() {
        return String.valueOf(this.prevInstrCARD.getFrameNumber());
    }

    public String getPreviousOffset() {
        return String.valueOf(this.prevInstrCARD.getOffset());
    }

    public void countClock() {
        this.clock++;
        refresh();
    }

    public void countTimer() {
        timer++;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public PageTable getPageTable() {
        return this.pageTable;
    }

    public List<Process> getProcesses() {
        return ram.getProcessHistory();
    }

    public String getOutPutMessage() {
        return this.outPutMessage;
    }

    public void setOutPutMessage(String s) {
        this.outPutMessage = s;
        refresh();
    }
}
