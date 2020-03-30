package entities;

public class Instruction {

    private int processID;
    private String operation;
    private int virtualAddress;

    public Instruction(int processID,String operation, int virtualAdress){
        this.processID = processID;
        this.operation = operation;
        this.virtualAddress = virtualAdress;
    }

    public Instruction() {
    }

    private String convertToBinary(int adress){
        String binaryAdress = Integer.toBinaryString(adress);
        int zeroes = 16 - binaryAdress.length();
        StringBuilder binaryBuilder = new StringBuilder();
        for(int i=0; i < zeroes; i++){
            binaryBuilder.append("0");
        }
        binaryBuilder.append(binaryAdress);
        return binaryBuilder.toString();
    }

    public int getOffset(){
        String binary = convertToBinary(virtualAddress);
        String binaryOffset = binary.substring(binary.length()-12);
        return Integer.parseInt(binaryOffset,2);
    }

    public int getPageNumber(){
        String binary = convertToBinary(virtualAddress);
        String binaryPageNumber = binary.substring(0, binary.length()-12);
        return Integer.parseInt(binaryPageNumber,2);
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
}
