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
    private Instruction currentInstruction, previousInstruction;
    private PageTable pageTable=new PageTable();
    private ReadWriteTable readWriteTable = new ReadWriteTable();
    // _________________________ MODEL VARIABLES _________________________
    private int clock;
    private List<Boolean> radioButtons = new ArrayList<>();
    private String xmlFile;
    private InstructionMODEL curInstrCARD = new InstructionMODEL();
    private InstructionMODEL prevInstrCARD = new InstructionMODEL();
    private FramesMODEL frames = new FramesMODEL();
    private boolean end = false;
    /* ------------------------------------------ CONSTRUCTORS ------------------------------------------ */
    public MainMODEL() {
        initModel();
    }

    public static Ram getRam() {
        return ram;
    }

    /* ------------------------------------------ INIT ------------------------------------------ */
    public void initModel() {
        //ANCHOR_LEFT
        this.clock = 0;
        initRadioButtons();
        initInstrCards();

        //FRAMES
        frames = new FramesMODEL();

        //REFRESH THE MODEL
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

        //curInstr.init();
        //prevInstr.init();
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
            timer++;
        }
        setRadioButtonsDisabled(true);
        System.out.println("Terminated" + this.xmlFile + ".xml set");//TODO
    }

    public void stepManualProgram() {
        System.out.print("[timer=" + timer + "|"); //TODO
        System.out.println("listSize=" + instructionList.size() + "]"); //TODO
        if (timer < instructionList.size()) {
            //CURRENT INSTRUCTION
            currentInstruction = instructionList.get(timer);
            executeCurrentInstruction();
            //PREVIOUS INSTRUCTION
            previousInstruction = new Instruction(0, 0, "none", 0, 0, 0);
            if (timer > 0) {
                previousInstruction = instructionList.get(timer - 1);
            }

            timer++;
            updateMODEL(/*ram, currentInstruction, previousInstruction*/);
            countCLK();
        } else {
            setRadioButtonsDisabled(true);
            end = true;
        }
    }

    // _________________________ EXECUTE OPERATION _________________________
    public void executeCurrentInstruction() {
        String operation = currentInstruction.getOpString();
        switch (operation) {
            case "Read":
                read(/*currentInstruction*/);
                setInstrParam();
                setRWTable();
                break;
            case "Write":
                write(/*currentInstruction*/);
                setInstrParam();
                setRWTable();

                break;
            case "Start":
                start(/*currentInstruction*/);
                break;
            case "Terminate":
                terminate(/*currentInstruction*/);
                break;
            default:
                System.out.println("INSTRUCTIE " + operation + " IS GEEN GELDIGE INSTRUCTIE"); //TODO
                break;
        }
    }

    private void setInstrParam() {
        currentInstruction.setPhysicalAddress(ram.getPhysicalAddress());
        currentInstruction.setFrameNumber(ram.getFrameNumber());
    }
    public void setRWTable(){
        int p = ram.getCurrentProcess().getProcessID();
        int r = ram.getCurrentProcess().getReadCount();
        int w = ram.getCurrentProcess().getWriteCount();
        readWriteTable.updateCount(p,r,w);
    }



    public void read(/*Instruction currentInstruction*/) {
        System.out.println("Reading process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber()); //TODO
        ram.read(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
        System.out.println("*READ*" + ram); //TODO
    }

    public void write(/*Instruction currentInstruction*/) {
        System.out.println("Writing to process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());//TODO
        ram.write(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
        System.out.println("*WROTE*" + ram);//TODO
    }

    public void start(/*Instruction currentInstruction*/) {
        System.out.println("Starting process " + currentInstruction.getPID());//TODO
        entities.Process currentProcess = new Process(currentInstruction.getPID());
        ram.addProcess(currentProcess);
        System.out.println("*STARTED*" + ram);//TODO
    }

    public void terminate(/*Instruction currentInstruction*/) {
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
    private void updateMODEL(/*Ram ram, Instruction currentInstruction, Instruction previousInstruction*/) {
        //INSRTUCTION CARDS
        //current instruction
        this.curInstrCARD.setProcessID(currentInstruction.getPID());
        this.curInstrCARD.setVirtualAddress(currentInstruction.getVirtualAddress());
        this.curInstrCARD.setOpColor(currentInstruction.getOpInt());
        //previous instruction
        this.prevInstrCARD.setProcessID(previousInstruction.getPID());
        this.prevInstrCARD.setVirtualAddress(previousInstruction.getVirtualAddress());
        this.prevInstrCARD.setOpColor(previousInstruction.getOpInt());

        this.prevInstrCARD.setPhysicalAddress(previousInstruction.getPhysicalAddress());
        this.prevInstrCARD.setFrameNumber(previousInstruction.getFrameNumber());
        this.prevInstrCARD.setOffset(previousInstruction.getOffset());

        //PAGE TABLE
        this.pageTable=ram.getCurrentProcess().getPageTable();;

        //FRAMES
        this.frames.setFrames(ram.getPresentPages());
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

    public Boolean getEnd() {
        return this.end;
    }

    public void setEnd(Boolean b) {
        this.end = b;
    }

    public PageTable getPageTable(){
        return this.pageTable;
    }

    public ReadWriteTable getReadWriteTable(){
        return this.readWriteTable;
    }


    public List<Process> getProcesses() {
        return ram.getProcessHistory();
    }
}
