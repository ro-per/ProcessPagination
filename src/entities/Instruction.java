package entities;

public class Instruction {

    private int processID;
    private String operation;
    private int virtualAdress;

    public Instruction(int processID,String operation, int virtualAdress){
        this.processID = processID;
        this.operation = operation;
        this.virtualAdress = virtualAdress;
    }

    public Instruction() {
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

    public int getVirtualAdress() {
        return virtualAdress;
    }

    public void setVirtualAdress(int virtualAdress) {
        this.virtualAdress = virtualAdress;
    }
}
