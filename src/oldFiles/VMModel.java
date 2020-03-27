package oldFiles;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class VMModel {
    public ArrayList<Instruction> instructionList;
    public RAM ram;
    public int timer;
    public int[] pageNrAndOffset;
    public Process currentProcess;//wordt telkens aangepast bij elke instructie
    public ArrayList<Process> processesOveral;
    public String fileName = "Instructions_30_3.xml";


    public void init() {
        instructionList = loadXML(fileName);
        processesOveral = new ArrayList<>();
        timer = 0;
        ram = new RAM(12);
        currentProcess = null;
        pageNrAndOffset = null;
    }

    public void setFileName(String newFileName) {
        this.fileName = newFileName;
        init();
    }

    /*
     * Loads the XML into instructionList
     */
    public ArrayList loadXML(String bestand) {
        try {
            File file = new File(bestand);
            instructionList = new ArrayList<Instruction>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = (Document) db.parse(file);
            document.getDocumentElement().normalize();

            NodeList xmlList = document.getElementsByTagName("instruction");
            for (int i = 0; i < xmlList.getLength(); i++) {
                Element element = (Element) xmlList.item(i);
                int processID = Integer.parseInt(element.getElementsByTagName("processID").item(0).getTextContent());
                String operation = element.getElementsByTagName("operation").item(0).getTextContent();
                int address = Integer.parseInt(element.getElementsByTagName("address").item(0).getTextContent());
                Instruction instruction = new Instruction(processID, operation, address);
                instructionList.add(instruction);
            }
            System.out.println("loadXML: ingelezen");
            return instructionList;
        } catch (Exception e) {
            System.out.println("loadXML: An error occured.");
            return null;
        }
    }

    public Instruction performOneInstruction() {
        if (timer < instructionList.size()) {
            Instruction currentInstruction = instructionList.get(timer);
            String operation = currentInstruction.getOperation();
            switch (operation) {
                case "Start":
                    pageNrAndOffset = null;
                    currentProcess = new Process(currentInstruction.getProcessID());
                    processesOveral.add(currentProcess);
                    ram.addProcess(currentProcess, timer);
                    System.out.println(currentInstruction.getProcessID() + " start");
                    break;
                case "Write":
                    System.out.println(currentInstruction.getProcessID() + " write");
                    setCurrentProcess(currentInstruction.getProcessID());
                    pageNrAndOffset = ram.analyseAdress(currentInstruction.getAddress());
                    ram.write(currentInstruction.getProcessID(), pageNrAndOffset, timer);

                    break;
                case "Read":
                    System.out.println(currentInstruction.getProcessID() + " read");
                    setCurrentProcess(currentInstruction.getProcessID());
                    pageNrAndOffset = ram.analyseAdress(currentInstruction.getAddress());
                    ram.read(currentInstruction.getProcessID(), pageNrAndOffset, timer);

                    break;
                case "Terminate":
                    System.out.println(currentInstruction.getProcessID() + " terminate");
                    setCurrentProcess(currentInstruction.getProcessID());
                    pageNrAndOffset = null;
                    ram.removeProcess(currentInstruction.getProcessID());
                    ram.reAdjustFrames();

                    break;
                default:
                    break;
            }
            timer++;
            return currentInstruction;
        } else {
            return null;
        }
    }

    /*
     * Get the instruction that isn't executed yet
     */
    public Instruction getCurrentInstruction() {
        if (timer < instructionList.size()) {
            return instructionList.get(timer);
        }
        return null;
    }

    public void cancel() {
        init();
    }

    public Process getCurrentProcess() {

        return currentProcess;
    }

    private void setCurrentProcess(int processID) {
        for (Process p : processesOveral) {
            if (p.getProcessID() == processID) {
                currentProcess = p;
            }
        }

    }

    public Page[] getFrames() {
        if (ram != null) {
            return ram.frameArray;
        }
        return null;
    }

    public ArrayList<Process> getAllProcesses() {
        if (ram != null) {
            return processesOveral;
        }

        return null;
    }

    public int getFrameNumber(Instruction executedInstruction) {
        int pid = executedInstruction.getProcessID();
        int adress = executedInstruction.getAddress();
        int[] pnEnOffset = ram.analyseAdress(adress);
        int framenummer = 0;
        for (Page p : ram.frameArray) {
            //check of pid en pn gelijk zijn aan die van de instructie
            if (p != null) {
                if (p.getProcessID() == pid) {
                    if (p.getPageNumber() == pnEnOffset[0]) {
                        return framenummer;
                    }
                }
            }

            framenummer++;

        }
        return -1;
    }

    public Integer getFysAdress(int framenummer, int offsetDec) {
        if (framenummer >= 0) {
            //dit wordt gedaan in het net uitgevoerde process
            //int fysAdress; //is framenummer naar binair + offset naar binair 
            String offsetbin = Integer.toBinaryString(offsetDec);
            String frameBin = Integer.toBinaryString(framenummer);

            StringBuilder fysAdress = new StringBuilder();

            StringBuilder zeros = new StringBuilder();

            int aantalZeros = 4 - frameBin.length();// voor de adres
            for (int i = 0; i < aantalZeros; i++) {
                zeros.append("0");
            }
            fysAdress = fysAdress.append(zeros).append(frameBin);

            zeros = zeros.delete(0, zeros.length());
            aantalZeros = 12 - offsetbin.length(); // voor offset
            for (int i = 0; i < aantalZeros; i++) {
                zeros.append("0");
            }
            fysAdress = fysAdress.append(zeros).append(offsetbin);


            String fys = fysAdress.toString();
            return Integer.parseInt(fys, 2);
        }
        return -1;
    }
}
