package model;

public class Instruction {
    private int processID;
    private Operation operation;
    private int virtualAdress;

    public Instruction(int processID,Operation operation, int virtualAdress){
        this.processID = processID;
        this.operation = operation;
        this.virtualAdress = virtualAdress;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getVirtualAdress() {
        return virtualAdress;
    }

    public void setVirtualAdress(int virtualAdress) {
        this.virtualAdress = virtualAdress;
    }
}
