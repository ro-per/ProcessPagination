package oldFiles;

public class Instruction {
    private int processID;
    private String operation;
    private int address;

    public Instruction(int processID, String operation, int address) {
        this.processID = processID;
        this.operation = operation;
        this.address = address;
    }

    public int getProcessID() {
        return processID;
    }

    public String getOperation() {
        return operation;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "pID=" + processID +
                ", operation='" + operation + '\'' +
                ", address=" + address +
                '}';
    }

}
