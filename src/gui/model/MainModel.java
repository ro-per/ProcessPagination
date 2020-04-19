package gui.model;

import entities.Process;
import entities.*;
import gui.controller.MainController;
import javafx.scene.control.Button;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static main.Main.FRAME_NUMBER;

public class MainModel extends Observable {
    // _________________________ MODELS _________________________
    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;

    // _________________________ ATTRIBUTES _________________________
    private int clk;
    private String xmlSet;
    private static final int BUTTON_NUMBER = 3;

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

    // _________________________ RUN _________________________
    public void runProgram() {
        while (timer < instructionList.size()) {
            Instruction currentInstruction = instructionList.get(timer);
            String operation = currentInstruction.getOperation();
            switch (operation) {
                case "Read":
                    System.out.println("Reading process " + currentInstruction.getProcessID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
                    ram.read(currentInstruction.getProcessID(), currentInstruction.getPageNumber(), timer);
                    System.out.println("Read " + ram);
                    break;
                case "Write":
                    System.out.println("Writing to process " + currentInstruction.getProcessID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
                    ram.write(currentInstruction.getProcessID(), currentInstruction.getPageNumber(), timer);
                    System.out.println("Wrote " + ram);
                    break;
                case "Start":
                    System.out.println("Starting process " + currentInstruction.getProcessID());
                    entities.Process currentProcess = new Process(currentInstruction.getProcessID());
                    ram.addProcess(currentProcess);
                    System.out.println("Started: " + ram);
                    break;
                case "Terminate":
                    System.out.println("Terminating process " + currentInstruction.getProcessID());
                    ram.removeProcess(currentInstruction.getProcessID());
                    System.out.println("Terminated:" + ram);
                    break;
                default:
                    break;
            }
            timer++;
        }
        setButtonsDisabled(true);
        System.out.println("_-_-_-_-" + this.xmlSet + "-_-_-_-_");
    }

    public void stepProgram() {
        System.out.println(timer);
        System.out.println(instructionList.size());
        if (timer < instructionList.size()) {
            Instruction currentInstruction = instructionList.get(timer);
            String operation = currentInstruction.getOperation();
            switch (operation) {
                case "Read":
                    System.out.println("Reading process " + currentInstruction.getProcessID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
                    ram.read(currentInstruction.getProcessID(), currentInstruction.getPageNumber(), timer);
                    System.out.println("Read " + ram);

                    setEverything(ram.getFrames(), 1);

                    break;
                case "Write":
                    System.out.println("Writing to process " + currentInstruction.getProcessID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
                    ram.write(currentInstruction.getProcessID(), currentInstruction.getPageNumber(), timer);
                    System.out.println("Wrote " + ram);


                    setEverything(ram.getFrames(), 2);

                    break;
                case "Start":
                    System.out.println("Starting process " + currentInstruction.getProcessID());
                    entities.Process currentProcess = new Process(currentInstruction.getProcessID());
                    ram.addProcess(currentProcess);
                    System.out.println("Started: " + ram);

                    setEverything(ram.getFrames(), 0);

                    break;
                case "Terminate":
                    System.out.println("Terminating process " + currentInstruction.getProcessID());
                    ram.removeProcess(currentInstruction.getProcessID());
                    System.out.println("Terminated:" + ram);


                    setEverything(ram.getFrames(), 3);

                    break;
                default:
                    break;
            }
            timer++;
        } else {
            setButtonsDisabled(true);
        }
        System.out.println("_-_-_-_-" + this.xmlSet + "-_-_-_-_");
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

    // _________________________ SETTERS _________________________
    public void setEverything(Page[] frames, int currentOP) {
        setFrames(frames);
        setOpColors('C', currentOP);
    }

    public void countCLK() {
        this.clk++;
        refresh();
    }

    public void setXmlSet(String xmlSet) {
        this.xmlSet = xmlSet;
    }

    public void setFrames(Page[] frames) {
        this.frames.setFrames(frames);
    }


    public void setOpColors(char c, int i) {
        if (c == 'C') {
            cOP.setOpColor(i);
        } else {
            pOP.setOpColor(i);
        }
        refresh();
    }

    public void setButtonsDisabled(Boolean bool) {
        for (int i = 0; i < BUTTON_NUMBER; i++) {
            actionButtons.set(i,bool);
        }
        refresh();
    }


}
