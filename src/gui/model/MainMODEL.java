package gui.model;

import entities.Process;
import entities.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainMODEL extends Observable {
    private static final int RADIOBUTTON_NUMBER = 3;
    /* ------------------------------------------ ATTRIBUTES ------------------------------------------ */
    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;
    private int clock;
    private List<Boolean> radioButtons = new ArrayList<>();
    private String xmlFile;
    private InstrCardMODEL curInstrCARD = new InstrCardMODEL();
    private InstrCardMODEL prevInstrCARD = new InstrCardMODEL();
    private FramesMODEL frames = new FramesMODEL();

    /* ------------------------------------------ CONSTRUCTORS ------------------------------------------ */
    public MainMODEL() {
        initModel();
    }

    /* ------------------------------------------ INIT ------------------------------------------ */
    public void initModel() {
        //ANCHOR_LEFT
        clock = 0;
        initRadioButtons();
        initInstrCards();
        //FRAMES
        //frames.init();
        frames=new FramesMODEL();
        //REFRESH THE MODEL
        refresh();
    }

    public void initRadioButtons() {
        radioButtons.clear();
        for (int i = 0; i < RADIOBUTTON_NUMBER; i++) {
            radioButtons.add(false);
        }
    }

    public void initInstrCards(){
        curInstrCARD =new InstrCardMODEL();
        prevInstrCARD = new InstrCardMODEL();

        //curInstr.init();
        //prevInstr.init();
    }

    public void initExecution() throws IOException, SAXException, ParserConfigurationException {
        ram = new Ram(new LruStrategy());
        instructionList = InstructionReader.getInstance().readInstructions(this.xmlFile);
        timer = 0;

    }
    /* ------------------------------------------ PROGRAM EXECUTION ------------------------------------------ */
    // _________________________ RUN _________________________

    public void runProgram() {
        while (timer < instructionList.size()) {
            Instruction currentInstruction = instructionList.get(timer);
            executeOperation(currentInstruction);
            timer++;
        }
        setRadioButtonsDisabled(true);
        System.out.println("Terminated" + this.xmlFile + ".xml set");//TODO
    }

    public void stepProgram() {
        System.out.print("[timer="+timer+"|"); //TODO
        System.out.println("listSize="+instructionList.size()+"]"); //TODO
        if (timer < instructionList.size()) {
            //CURRENT INSTRUCTION
            Instruction currentInstruction = instructionList.get(timer);
            executeOperation(currentInstruction);
            //PREVIOUS INSTRUCTION
            Instruction previousInstruction = new Instruction(0,0,"none",0,0,0);
            if (timer > 0) {
                previousInstruction = instructionList.get(timer - 1);
            }

            timer++;
            updateMODEL(ram, currentInstruction, previousInstruction);
            countCLK();
        } else {
            setRadioButtonsDisabled(true);
        }
        System.out.println("Terminated" + this.xmlFile + ".xml set");//TODO
    }
    // _________________________ EXECUTE OPERATION _________________________
    public void executeOperation(Instruction currentInstruction) {
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
                System.out.println("INSTRUCTIE "+operation+" IS GEEN GELDIGE INSTRUCTIE"); //TODO
                break;
        }
    }

    public void read(Instruction currentInstruction) {
        System.out.println("Reading process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber()); //TODO
        ram.read(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
        System.out.println("*READ*" + ram); //TODO
    }

    public void write(Instruction currentInstruction) {
        System.out.println("Writing to process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());//TODO
        ram.write(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
        System.out.println("*WROTE*" + ram);//TODO
    }

    public void start(Instruction currentInstruction) {
        System.out.println("Starting process " + currentInstruction.getPID());//TODO
        entities.Process currentProcess = new Process(currentInstruction.getPID());
        ram.addProcess(currentProcess);
        System.out.println("*STARTED*" + ram);//TODO
    }

    public void terminate(Instruction currentInstruction) {
        System.out.println("Terminating process " + currentInstruction.getPID());//TODO
        ram.removeProcess(currentInstruction.getPID());
        System.out.println("*TERMINATED*" + ram);//TODO
    }



    /* ------------------------------------------ REFRESH ------------------------------------------ */
    public void refresh() {
        setChanged();
        notifyObservers();
    }
    /* ------------------------------------------ UPDATE ------------------------------------------ */
    private void updateMODEL(Ram ram, Instruction currentInstruction, Instruction previousInstruction) {
        //INSRTUCTION CARDS
        //current instruction
        this.curInstrCARD.setProcessID(currentInstruction.getPID());
        this.curInstrCARD.setVirtualAddress(currentInstruction.getVirtualAddress());
        this.curInstrCARD.setOpColor(currentInstruction.getOpInt());
        //previous instruction
        this.prevInstrCARD.setProcessID(previousInstruction.getPID());
        this.prevInstrCARD.setVirtualAddress(previousInstruction.getVirtualAddress());
        this.prevInstrCARD.setOpColor(previousInstruction.getOpInt());

        //PAGE TABLE

        //FRAMES
        this.frames.setFrames(ram.getFrames());
        //PROCESSES


        refresh();
    }
    /* ------------------------------------------ GETTERS AND SETTERS ------------------------------------------ */
    // _________________________ CLOCK _________________________
    public int getClock() {
        return clock;
    }

    // _________________________ RADIOBUTTONS _________________________
    public boolean getButtonsDisabled(int i) {
        return radioButtons.get(i);
    }

    // _________________________ FRAMES _________________________
    public int getFramePid(int i) {
        return frames.getFrameProcessID(i);
    }
    public int getFramePnr(int i) {
        return frames.getFrameProcessNR(i);
    }

    // _________________________ CURRENT INSTRUCTION _________________________
    public String getCurPid() {
        return String.valueOf(this.curInstrCARD.getProcessID());
    }
    public String getCurVaddr() {
        return String.valueOf(this.curInstrCARD.getVirtualAddress());
    }
    public String getCopColors(int i) {
        return curInstrCARD.getOperationColor(i);
    }



    // _________________________ PREVIOUS INSTRUCTION _________________________
    public String getPrevPid() {
        return String.valueOf(this.prevInstrCARD.getProcessID());
    }
    public String getPrevVaddr() {
        return String.valueOf(this.prevInstrCARD.getVirtualAddress());
    }
    public String getPopColors(int i) {
        return prevInstrCARD.getOperationColor(i);
    }
    public String getPrevPaddr() {
        return String.valueOf(this.prevInstrCARD.getPhysicalAddress());
    }
    public String getPrevFrameNr() {
        return String.valueOf(this.prevInstrCARD.getFrameNumber());
    }
    public String getPrevOffset() {
        return String.valueOf(this.prevInstrCARD.getOffset());
    }




    public void setPageTable() {

    }


    public void countCLK() {
        this.clock++;
        refresh();
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void setRadioButtonsDisabled(Boolean bool) {
        for (int i = 0; i < RADIOBUTTON_NUMBER; i++) {
            radioButtons.set(i, bool);
        }
        refresh();
    }



}
