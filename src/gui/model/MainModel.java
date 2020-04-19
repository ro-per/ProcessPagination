package gui.model;

import entities.Process;
import entities.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {
    // _________________________ MODELS _________________________
    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;

    // _________________________ ATTRIBUTES _________________________
    private int clk;
    private String xmlFile;

    private Frames frames = new Frames();
    private CurrentInstruction cOP = new CurrentInstruction();
    private PreviousInstruction pOP = new PreviousInstruction();

    // _________________________ CONSTRUCTORS _________________________
    public MainModel() throws ParserConfigurationException, SAXException, IOException {
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

        //REFRESH THE MODEL
        refresh();
    }

    public void initProgram(String xmlFile) throws IOException, SAXException, ParserConfigurationException {
        ram = new Ram(new LruStrategy());
        instructionList = InstructionReader.getInstance().readInstructions(xmlFile);
        timer = 0;

    }

    // _________________________ RUN _________________________
    public static void runProgram(String set) {
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
        System.out.println("_-_-_-_-" + set + "-_-_-_-_");
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

    // _________________________ SETTERS _________________________
    public void countCLK() {
        this.clk++;
        refresh();
    }

    public void setClk(int clock) {
        this.clk = clock;
        refresh();
    }

    public void setFramePIDs() {
        frames.countPIDs();
    }

    public void setFramePNRs() {
        frames.countPNRs();
    }

    public void setOpColors(char c, int i) {
        if (c == 'C') {
            cOP.setOpColor(i);
        } else {
            pOP.setOpColor(i);
        }
        refresh();
    }
}
