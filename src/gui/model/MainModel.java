package gui.model;

import entities.Process;
import entities.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {
    private static final int BUTTON_NUMBER = 3;
    // _________________________ MODELS _________________________
    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;
    // _________________________ ATTRIBUTES _________________________
    private int clk;
    private String xmlSet;
    private Frames frames = new Frames();
    private CurrentInstruction cOP = new CurrentInstruction();
    private PreviousInstruction pOP = new PreviousInstruction();

    private List<Boolean> actionButtons = new ArrayList<>();

    // _________________________ CONSTRUCTORS _________________________
    public MainModel() {
        initModel();
    }

    // _________________________ INIT _________________________
    public void initModel() {
        //ANCHOR_LEFT
        clk = 0;

        //INSTRUCTION CARDS
        cOP.init();
        pOP.init();
        //FRAMES
        frames.init();

        initActionButtons();


        //REFRESH THE MODEL
        refresh();

    }

    public void initActionButtons() {
        actionButtons.clear();
        for (int i = 0; i < BUTTON_NUMBER; i++) {
            actionButtons.add(false);
        }
    }

    public void initProgram() throws IOException, SAXException, ParserConfigurationException {
        ram = new Ram(new LruStrategy());
        instructionList = InstructionReader.getInstance().readInstructions(this.xmlSet);
        timer = 0;

    }

    // _________________________ DETERMINE OPERATION _________________________
    public void determineOperation(Instruction currentInstruction){
        String operation = currentInstruction.getOpString();
        switch (operation) {
            case "Read":
                read(currentInstruction);
                break;
            case "Write":
                write(currentInstruction);
                break;
            case "Start":
                start(currentInstruction);
                break;
            case "Terminate":
                terminate(currentInstruction);
                break;
            default:
                break;
        }
    }
    public void read(Instruction currentInstruction){
        System.out.println("Reading process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
        ram.read(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer);
        System.out.println("*READ*" + ram);
    }
    public void write(Instruction currentInstruction){
        System.out.println("Writing to process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
        ram.write(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer);
        System.out.println("*WROTE*" + ram);
    }
    public void start(Instruction currentInstruction){
        System.out.println("Starting process " + currentInstruction.getPID());
        entities.Process currentProcess = new Process(currentInstruction.getPID());
        ram.addProcess(currentProcess);
        System.out.println("*STARTED*" + ram);
    }
    public void terminate(Instruction currentInstruction){
        System.out.println("Terminating process " + currentInstruction.getPID());
        ram.removeProcess(currentInstruction.getPID());
        System.out.println("*TERMINATED*" + ram);
    }

    // _________________________ RUN _________________________

    public void runProgram() {
        while (timer < instructionList.size()) {
            Instruction currentInstruction = instructionList.get(timer);
            determineOperation(currentInstruction);
            timer++;
        }
        setButtonsDisabled(true);
        System.out.println("Terminated" + this.xmlSet + ".xml set");
    }

    public void stepProgram() {
        System.out.println(timer);
        System.out.println(instructionList.size());
        if (timer < instructionList.size()) {
            Instruction currentInstruction = instructionList.get(timer);
            determineOperation(currentInstruction);
            timer++;

            setViewComplete(ram, currentInstruction);
            countCLK();
        } else {
            setButtonsDisabled(true);
        }
        System.out.println("Terminated" + this.xmlSet + ".xml set");
    }

    // _________________________ REFRESH _________________________
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    // _________________________ GETTERS _________________________
    public int getClk() {
        return clk;
    }

    public void setClk(int clock) {
        this.clk = clock;
        refresh();
    }

    public String getCopColors(int i) {
        return cOP.getOpColor(i);
    }

    public String getPopColors(int i) {
        return pOP.getOpColor(i);
    }

    public int getFramePid(int i) {
        return frames.getFramePidSingle(i);
    }

    public int getFramePnr(int i) {
        return frames.getFramePnrSingle(i);
    }

    public boolean getButtonsDisabled(int i) {
        return actionButtons.get(i);
    }

    public String getCurPid() {
        return String.valueOf(this.cOP.getPID());
    }
    public String getcurvaddr() {
        return String.valueOf(this.cOP.getvaddr());
    }
    // _________________________ SETTERS _________________________

    private void setViewComplete(Ram ram, Instruction currentInstruction) {
        //INSRTUCTION CARDS
        //current instruction
        this.cOP.setPID(currentInstruction.getPID());
        this.cOP.setvaddr(currentInstruction.getVirtualAddress());
        this.cOP.setOpColor(currentInstruction.getOpInt());
        //previous instruction
        //this.pOP.setOpColor(pOP);

        //PAGE TABLE

        //FRAMES
        this.frames.setFrames(ram.getFrames());
        //PROCESSES


        refresh();
    }

    public void setPageTable() {

    }


    public void countCLK() {
        this.clk++;
        refresh();
    }

    public void setXmlSet(String xmlSet) {
        this.xmlSet = xmlSet;
    }

    public void setButtonsDisabled(Boolean bool) {
        for (int i = 0; i < BUTTON_NUMBER; i++) {
            actionButtons.set(i, bool);
        }
        refresh();
    }



}
