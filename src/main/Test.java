package main;

import entities.Process;
import entities.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Test {

    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        init(InstructionReader.INSTR_30_4);
        run();
    }

    public static void init(String amount) throws IOException, SAXException, ParserConfigurationException {
        ram = new Ram(new LruStrategy());
        instructionList = InstructionReader.getInstance().readInstructions(amount);
        timer = 0;
    }

    public static void run() {
        while (timer < instructionList.size()) {
            Instruction currentInstruction = instructionList.get(timer);
            String operation = currentInstruction.getOpString();
            switch (operation) {
                case "Read":
                    System.out.println("Reading process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
                    ram.read(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
                    System.out.println("Read: " + ram.getPhysicalAddress());
                    System.out.println("Offset: " + currentInstruction.getOffset());
                    System.out.println("FrameNumber: " + ram.getFrameNumber());
                    System.out.println("Write count: " +  ram.getCurrentProcess().getWriteCount());
                    System.out.println("Read count: " +  ram.getCurrentProcess().getReadCount());
                    System.out.println(ram);
                    break;
                case "Write":
                    System.out.println("Writing to process " + currentInstruction.getPID() + " with virtual address: " + currentInstruction.getVirtualAddress() + " and page number: " + currentInstruction.getPageNumber());
                    ram.write(currentInstruction.getPID(), currentInstruction.getPageNumber(), timer, currentInstruction.getOffset());
                    System.out.println("Wrote: " + ram.getPhysicalAddress());
                    System.out.println("Offset: " + currentInstruction.getOffset());
                    System.out.println("FrameNumber: " + ram.getFrameNumber());
                    System.out.println("Write count: " + ram.getCurrentProcess().getWriteCount());
                    System.out.println("Read count: " +  ram.getCurrentProcess().getReadCount());
                    System.out.println(ram);
                    break;
                case "Start":
                    System.out.println("Starting process " + currentInstruction.getPID());
                    Process currentProcess = new Process(currentInstruction.getPID());
                    ram.addProcess(currentProcess);
                    System.out.println("Started: " + ram);
                    break;
                case "Terminate":
                    System.out.println("Terminating process " + currentInstruction.getPID());
                    ram.removeProcess(currentInstruction.getPID());
                    System.out.println("Terminated:" + ram);
                    break;
                default:
                    break;
            }
            timer++;
        }
    }

}
