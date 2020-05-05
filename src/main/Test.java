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
        String test = "{\"results\":[{\"currency\":\"euro\",\"lpg\":\"0,405\",\"diesel\":\"1,385\",\"gasoline\":\"1,393\",\"country\":\"Albania\"},{\"currency\":\"euro\",\"lpg\":\"0,065\",\"diesel\":\"0,099\",\"gasoline\":\"0,163\",\"country\":\"Algeria\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,000\",\"gasoline\":\"1,035\",\"country\":\"Andorra\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,920\",\"gasoline\":\"0,977\",\"country\":\"Armenia\"},{\"currency\":\"euro\",\"lpg\":\"0,770\",\"diesel\":\"1,045\",\"gasoline\":\"1,090\",\"country\":\"Austria\"},{\"currency\":\"euro\",\"lpg\":\"0,360\",\"diesel\":\"0,679\",\"gasoline\":\"0,679\",\"country\":\"Belarus\"},{\"currency\":\"euro\",\"lpg\":\"0,381\",\"diesel\":\"1,229\",\"gasoline\":\"1,231\",\"country\":\"Belgium\"},{\"currency\":\"euro\",\"lpg\":\"0,537\",\"diesel\":\"1,024\",\"gasoline\":\"1,080\",\"country\":\"Bosnia and Herzegovina\"},{\"currency\":\"euro\",\"lpg\":\"0,414\",\"diesel\":\"0,956\",\"gasoline\":\"0,920\",\"country\":\"Bulgaria\"},{\"currency\":\"euro\",\"lpg\":\"0,484\",\"diesel\":\"1,044\",\"gasoline\":\"1,044\",\"country\":\"Croatia\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"1,022\",\"gasoline\":\"0,000\",\"country\":\"Cyprus\"},{\"currency\":\"euro\",\"lpg\":\"0,496\",\"diesel\":\"0,968\",\"gasoline\":\"1,047\",\"country\":\"Czech Republic\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"1,018\",\"gasoline\":\"1,179\",\"country\":\"Denmark\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,323\",\"gasoline\":\"0,443\",\"country\":\"Egypt\"},{\"currency\":\"euro\",\"lpg\":\"0,668\",\"diesel\":\"1,219\",\"gasoline\":\"1,200\",\"country\":\"Estonia\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"1,189\",\"gasoline\":\"1,290\",\"country\":\"Finland\"},{\"currency\":\"euro\",\"lpg\":\"0,863\",\"diesel\":\"1,207\",\"gasoline\":\"1,251\",\"country\":\"France\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,558\",\"gasoline\":\"0,572\",\"country\":\"Georgia\"},{\"currency\":\"euro\",\"lpg\":\"0,598\",\"diesel\":\"1,076\",\"gasoline\":\"1,198\",\"country\":\"Germany\"},{\"currency\":\"euro\",\"lpg\":\"0,983\",\"diesel\":\"1,194\",\"gasoline\":\"1,290\",\"country\":\"Greece\"},{\"currency\":\"euro\",\"lpg\":\"0,647\",\"diesel\":\"0,936\",\"gasoline\":\"0,853\",\"country\":\"Hungary\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"1,290\",\"gasoline\":\"1,300\",\"country\":\"Iceland\"},{\"currency\":\"euro\",\"lpg\":\"0,690\",\"diesel\":\"1,169\",\"gasoline\":\"1,269\",\"country\":\"Ireland\"},{\"currency\":\"euro\",\"lpg\":\"0,770\",\"diesel\":\"1,873\",\"gasoline\":\"1,345\",\"country\":\"Israel\"},{\"currency\":\"euro\",\"lpg\":\"0,591\",\"diesel\":\"1,348\",\"gasoline\":\"1,457\",\"country\":\"Italy\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,717\",\"gasoline\":\"1,245\",\"country\":\"Jordan\"},{\"currency\":\"euro\",\"lpg\":\"0,510\",\"diesel\":\"1,000\",\"gasoline\":\"1,050\",\"country\":\"Kosovo\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,327\",\"gasoline\":\"0,193\",\"country\":\"Kuwait\"},{\"currency\":\"euro\",\"lpg\":\"0,475\",\"diesel\":\"0,000\",\"gasoline\":\"0,000\",\"country\":\"Latvia\"},{\"currency\":\"euro\",\"lpg\":\"0,640\",\"diesel\":\"0,610\",\"gasoline\":\"1,433\",\"country\":\"Lebanon\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,098\",\"gasoline\":\"0,130\",\"country\":\"Libya\"},{\"currency\":\"euro\",\"lpg\":\"0,399\",\"diesel\":\"0,000\",\"gasoline\":\"0,000\",\"country\":\"Lithuania\"},{\"currency\":\"euro\",\"lpg\":\"0,392\",\"diesel\":\"0,000\",\"gasoline\":\"0,000\",\"country\":\"Luxembourg\"},{\"currency\":\"euro\",\"lpg\":\"0,446\",\"diesel\":\"0,746\",\"gasoline\":\"0,737\",\"country\":\"Macedonia\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"1,280\",\"gasoline\":\"1,410\",\"country\":\"Malta\"},{\"currency\":\"euro\",\"lpg\":\"0,539\",\"diesel\":\"0,803\",\"gasoline\":\"0,900\",\"country\":\"Moldova\"},{\"currency\":\"euro\",\"lpg\":\"0,580\",\"diesel\":\"0,000\",\"gasoline\":\"0,000\",\"country\":\"Montenegro\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,670\",\"gasoline\":\"0,722\",\"country\":\"Morocco\"},{\"currency\":\"euro\",\"lpg\":\"0,735\",\"diesel\":\"1,303\",\"gasoline\":\"1,567\",\"country\":\"Netherlands\"},{\"currency\":\"euro\",\"lpg\":\"0,738\",\"diesel\":\"1,305\",\"gasoline\":\"1,322\",\"country\":\"Norway\"},{\"currency\":\"euro\",\"lpg\":\"0,306\",\"diesel\":\"0,804\",\"gasoline\":\"0,740\",\"country\":\"Poland\"},{\"currency\":\"euro\",\"lpg\":\"0,678\",\"diesel\":\"1,229\",\"gasoline\":\"1,334\",\"country\":\"Portugal\"},{\"currency\":\"euro\",\"lpg\":\"0,539\",\"diesel\":\"0,962\",\"gasoline\":\"0,923\",\"country\":\"Romania\"},{\"currency\":\"euro\",\"lpg\":\"0,266\",\"diesel\":\"0,576\",\"gasoline\":\"0,575\",\"country\":\"Russia\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,184\",\"gasoline\":\"0,221\",\"country\":\"Saudi Arabia\"},{\"currency\":\"euro\",\"lpg\":\"0,645\",\"diesel\":\"1,266\",\"gasoline\":\"1,185\",\"country\":\"Serbia\"},{\"currency\":\"euro\",\"lpg\":\"0,592\",\"diesel\":\"1,230\",\"gasoline\":\"1,334\",\"country\":\"Slovakia\"},{\"currency\":\"euro\",\"lpg\":\"0,674\",\"diesel\":\"1,000\",\"gasoline\":\"1,000\",\"country\":\"Slovenia\"},{\"currency\":\"euro\",\"lpg\":\"0,666\",\"diesel\":\"1,065\",\"gasoline\":\"1,175\",\"country\":\"Spain\"},{\"currency\":\"euro\",\"lpg\":\"0,830\",\"diesel\":\"1,241\",\"gasoline\":\"1,165\",\"country\":\"Sweden\"},{\"currency\":\"euro\",\"lpg\":\"0,843\",\"diesel\":\"1,420\",\"gasoline\":\"1,344\",\"country\":\"Switzerland\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,373\",\"gasoline\":\"0,501\",\"country\":\"Tunisia\"},{\"currency\":\"euro\",\"lpg\":\"0,213\",\"diesel\":\"0,642\",\"gasoline\":\"0,683\",\"country\":\"Turkey\"},{\"currency\":\"euro\",\"lpg\":\"-\",\"diesel\":\"0,589\",\"gasoline\":\"0,424\",\"country\":\"U.S.A\"},{\"currency\":\"euro\",\"lpg\":\"0,297\",\"diesel\":\"0,755\",\"gasoline\":\"0,780\",\"country\":\"Ukraine\"},{\"currency\":\"euro\",\"lpg\":\"0,605\",\"diesel\":\"1,355\",\"gasoline\":\"1,308\",\"country\":\"United Kingdom\"}],\"success\":true}";
        String out = test.replaceAll(",","");
        System.out.println(out);
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

                    System.out.println("PROCESS ****************************"+ram.getCurrentProcess().getProcessID());
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
