package gui.model;

import entities.Instruction;
import entities.InstructionReader;
import entities.Process;
import entities.Ram;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {
    //************************** ATTRIBUTES **************************

    private int clock;
    private Ram ram;
    private Process currentProcess;
    private String filename;
    private InstructionReader instructionReader;
    private List<Instruction> instructionList;


    //************************** CONSTRUCTORS **************************
    public MainModel() throws ParserConfigurationException, SAXException, IOException {
        ram = new Ram();
        instructionReader = new InstructionReader();
        filename = InstructionReader.INSTR_30_4;
        init();
    }

    //************************** METHODS **************************
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    public void init() throws IOException, SAXException, ParserConfigurationException {
        clock = 0;
        currentProcess = null;
        if (filename != null) {
            instructionList = instructionReader.readInstructions(filename);
            refresh();
        } else {
            System.out.println("Ooops, filename is not correct");
        }
    }

    public void stepInstruction(){
        if(clock < instructionList.size()){
            Instruction instruction = instructionList.get(clock);
            switch (instruction.getOperation()){
                case"Start":
                    currentProcess = new Process(instruction.getProcessID());
                    // give process process table
                    // initialise ram
                    break;
                case "Write":
                    //
                    //
                    break;
                case "Read":
                    //
                    //
                    break;
                case "Terminate":
                    //
                    //
                    break;
                default:
                    break;
            }
        }
    }


    //************************** CLOCK COUNTERS **************************
    public void countClockUp() {
        this.clock++;
        stepInstruction();
        refresh();
    }

    public void countClockDown() {
        if (clock > -1) {
            this.clock--;
        }
        refresh();
    }

    //************************** GETTERS **************************
    public int getClock() {
        return clock;
    }

    //************************** SETTERS **************************
    public void setClock(int clock) {
        this.clock = clock;
        refresh();
    }

    public void setFilename(String filename) throws ParserConfigurationException, SAXException, IOException {
        this.filename = filename;
        init();
        refresh();
    }
}
