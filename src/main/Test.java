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
        init("30_3");
        //init("20000_4");
        //init("20000_20");
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
                    Process currentProcess = new Process(currentInstruction.getProcessID());
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

}
