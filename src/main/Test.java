package main;

import entities.Instruction;
import entities.InstructionReader;
import entities.Process;
import entities.Ram;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static Ram ram;
    private static InstructionReader instructionReader;
    private static String filename;
    private static List<Instruction> instructionList;
    private static int timer;
    private static Instruction currentInstruction;
    private static Process currentProcess;


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        init();
        run();
    }

    public static void init() throws IOException, SAXException, ParserConfigurationException {
         ram = new Ram();
         instructionReader = new InstructionReader();
         filename = InstructionReader.INSTR_30_4;
         instructionList = instructionReader.readInstructions(filename);
         timer = 0;
    }

    public static void run(){
        while (timer < instructionList.size()){
            currentInstruction = instructionList.get(timer);
            String operation = currentInstruction.getOperation();
            switch (operation){
                case "Read":
                    System.out.println("Reading process " + currentInstruction.getProcessID() + " with virtual address "+ currentInstruction.getVirtualAddress());
                    break;
                case "Write":

                    System.out.println("Writing to process " + currentInstruction.getProcessID() + " with virtual address "+ currentInstruction.getVirtualAddress());
                    break;
                case "Start":
                    System.out.println("Starting process " + currentInstruction.getProcessID());
                    currentProcess = new Process(currentInstruction.getProcessID());
                    ram.addProcess(currentProcess);
                    ram.adjustFrames();
                    System.out.println("Started: " + ram.toString());
                    break;
                case "Terminate":
                    System.out.println("Terminating process " + currentInstruction.getProcessID());
                    ram.removeProcess(currentProcess);
                    ram.adjustFrames();
                    System.out.println("Terminated:" + ram.toString());
                    break;
                default:
                    break;
            }
            timer++;
        }
    }

}
