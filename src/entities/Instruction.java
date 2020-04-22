package entities;

import utils.BinaryConverter;

public class Instruction {

    private int processID;
    private String opString;
    private int opInt; //NIET VERWIJDEREN
    private int virtualAddress;

    public Instruction() {
        processID = 0;
        virtualAddress = 0;
    }

    public int getOffset() {
        String binary = BinaryConverter.convertToBinary(virtualAddress, PageTable.PAGE_TABLE_LENGTH);
        String binaryOffset = binary.substring(binary.length() - Ram.AMOUNT_OF_FRAMES);
        return Integer.parseInt(binaryOffset, 2);
    }

    public int getPageNumber() {
        String binary = BinaryConverter.convertToBinary(virtualAddress, PageTable.PAGE_TABLE_LENGTH);
        String binaryPageNumber = binary.substring(0, binary.length() - Ram.AMOUNT_OF_FRAMES);
        return Integer.parseInt(binaryPageNumber, 2);
    }

    public int getPID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public String getOpString() {
        return opString;
    }
    public int getOpInt(){
        return this.opInt;
    }

    public void setOperation(String operation) {
        this.opString = operation;
        switch (operation) {
            case "Start":
                this.opInt = 0;
                break;
            case "Read":
                this.opInt = 1;

                break;
            case "Write":
                this.opInt = 2;

                break;
            case "Terminate":
                this.opInt = 3;
                break;
            default:
                break;
        }
    }

    public int getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(int virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "processID=" + processID +
                ", operation='" + opString + '\'' +
                ", virtualAddress=" + virtualAddress +
                '}';
    }
}
