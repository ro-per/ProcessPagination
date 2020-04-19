package gui.model;

import entities.Instruction;
import entities.InstructionReader;
import entities.LruStrategy;
import entities.Process;
import entities.Ram;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {

    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;

    /* ******************** ATTRIBUTES ******************** */
    private int clk;
    private String xmlFile;

    private Frames frames = new Frames();
    private CurrentInstruction cOP = new CurrentInstruction();
    private PreviousInstruction pOP = new PreviousInstruction();

    private ToggleGroup fileChooser = new ToggleGroup();
    RadioButton file1, file2, file3, fileSelected;

    /* ******************** CONSTRUCTORS ******************** */
    public MainModel() throws ParserConfigurationException, SAXException, IOException {
        initModel();
    }

    /* ******************** INIT ******************** */
    public void initModel() {
        //ANCHOR_LEFT
        clk = 0;
        initRadioButtons();

        //INSTRUCTION CARDS
        cOP.init();
        pOP.init();
        //FRAMES
        frames.init();

        refresh();
    }

    public void initRadioButtons() {
        file1 = new RadioButton("30_3");
        file1.setToggleGroup(fileChooser);
        file1.setSelected(true);

        file2 = new RadioButton("20000_4");
        file2.setToggleGroup(fileChooser);

        file3 = new RadioButton("20000_20");
        file3.setToggleGroup(fileChooser);
    }

    public void initProgram() throws IOException, SAXException, ParserConfigurationException {
        refreshRadioButtons();
        xmlFile = fileSelected.getText();
        ram = new Ram(new LruStrategy());
        instructionList = InstructionReader.getInstance().readInstructions(xmlFile);
        timer = 0;

    }

    public static void runProgram() {
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
    }


    /* ******************** REFRESH ******************** */

    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /* ******************** COUNTERS ******************** */
    public void countCLK() {
        this.clk++;
        refresh();
    }

    /* ******************** GETTERS ******************** */
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

    /* ******************** SETTERS ******************** */
    public void setXmlFile(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
        this.xmlFile = xmlFile;
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
    }


    public void setRadioButtons(RadioButton set_30_3, RadioButton set_20k_4, RadioButton set_20k_20) {


    }

    public void refreshRadioButtons() {
        fileSelected = (RadioButton) fileChooser.getSelectedToggle();

    }
}
