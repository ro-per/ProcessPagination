package entities;

import utils.BinaryConverter;

public class Instruction {
    /* ------------------------------------------ ATTRIBUTES ------------------------------------------ */

    private int processID;
    private int virtualAddress;
    private String opString;
    private int opInt; //DUBBELE OPSLAG VOOR VISUALISATIE

    private int physicalAddress;
    private int frameNumber;
    private int offset;

    /* ------------------------------------------ CONSTRUCTORS ------------------------------------------ */

    public Instruction() {
        processID = 0;
        virtualAddress = 0;
    }

    public Instruction(int pid, int vaddr, String op, int paddr, int fnr, int offset) {
        this.processID = pid;
        this.virtualAddress = vaddr;
        setOperation(op);
        this.physicalAddress = paddr;
        this.frameNumber = fnr;
        this.offset = offset;
    }
    /* ------------------------------------------ GETTERS AND SETTERS ------------------------------------------ */
    // _________________________ PROCESS ID _________________________

    public int getPID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    // _________________________ VIRTUAL ADDRESS _________________________
    public int getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(int virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    // _________________________ OPERATION _________________________
    public String getOpString() {
        return opString;
    }

    public int getOpInt() {
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
                this.opInt = -1;
                break;
        }
    }
    // _________________________ PHYSICAL ADDRESS _________________________

    // _________________________ FRAME NUMBER _________________________

    // _________________________ OFFSET _________________________
    public void setOffset() {
        String binary = BinaryConverter.convertToBinary(virtualAddress, PageTable.PAGE_TABLE_LENGTH);
        String binaryOffset = binary.substring(binary.length() - Ram.AMOUNT_OF_FRAMES);
        this.offset = Integer.parseInt(binaryOffset, 2);
    }

    public int getOffset() {
        setOffset();
        return this.offset;
    }


    // _________________________ PAGE NUMBER _________________________
    public int getPageNumber() {
        String binary = BinaryConverter.convertToBinary(virtualAddress, PageTable.PAGE_TABLE_LENGTH);
        String binaryPageNumber = binary.substring(0, binary.length() - Ram.AMOUNT_OF_FRAMES);
        return Integer.parseInt(binaryPageNumber, 2);
    }

    // _________________________ TOSTRING _________________________
    @Override
    public String toString() {
        return "Instruction{" +
                "processID=" + processID +
                ", operation='" + opString + '\'' +
                ", virtualAddress=" + virtualAddress +
                '}';
    }
}
