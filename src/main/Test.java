package main;

import entities.Instruction;
import entities.InstructionReader;
import entities.Process;
import entities.Ram;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Test {

    private static Ram ram;
    private static List<Instruction> instructionList;
    private static int timer;

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        init();
        run();
    }

    public static void init() throws IOException, SAXException, ParserConfigurationException {
         ram = new Ram();
         InstructionReader instructionReader = InstructionReader.getInstance();
         String filename = InstructionReader.INSTR_30_4;
         instructionList = instructionReader.readInstructions(filename);
         timer = 0;
    }

    public static void run(){
        while (timer < instructionList.size()){
            Instruction currentInstruction = instructionList.get(timer);
            String operation = currentInstruction.getOperation();
            switch (operation){
                case "Read":
                    System.out.println("Reading process " + currentInstruction.getProcessID() + " with virtual address "+ currentInstruction.getVirtualAddress());
                    ram.read(currentInstruction.getProcessID(),currentInstruction.getPageNumber(),timer);
                    System.out.println("Readed "+ ram.toString());
                    break;
                case "Write":
                    System.out.println("Writing to process " + currentInstruction.getProcessID() + " with virtual address "+ currentInstruction.getVirtualAddress());
                    ram.write(currentInstruction.getProcessID(),currentInstruction.getPageNumber(),timer);
                    System.out.println("Writed " + ram.toString());
                    break;
                case "Start":
                    System.out.println("Starting process " + currentInstruction.getProcessID());
                    Process currentProcess = new Process(currentInstruction.getProcessID());
                    ram.addProcess(currentProcess);
                    ram.adjustFrames();
                    System.out.println("Started: " + ram.toString());
                    break;
                case "Terminate":
                    System.out.println("Terminating process " + currentInstruction.getProcessID());
                    ram.removeProcess(currentInstruction.getProcessID());
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
