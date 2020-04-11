package entities;

import utils.BinaryConverter;

public class Instruction {

    private int processID;
    private String operation;
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

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
                ", operation='" + operation + '\'' +
                ", virtualAddress=" + virtualAddress +
                '}';
    }
}
