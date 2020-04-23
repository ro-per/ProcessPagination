package gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class InstrCardMODEL extends Observable {
    /* ------------------------------------------ ATTRIBUTES ------------------------------------------ */
    private static final String OP_ACTIVE = "-fx-background-color: CHARTREUSE;";
    private static final String OP_INACTIVE = "-fx-background-color: WHITE;";
    private static final int OP_AMOUNT = 4;
    private List<String> opColorList = new ArrayList<>();
    private int processID;
    private int virtualAddress;
    private int physicalAddress;
    private int frameNumber;
    private int offset;

    /* ------------------------------------------ CONSTRUCTORS ------------------------------------------ */
    public InstrCardMODEL() {
        initOperationColors();
        this.processID = -1;
        this.virtualAddress = -1;
        this.physicalAddress = -1;
        this.frameNumber = -1;
        this.offset = -1;
    }

    /* ------------------------------------------ INIT ------------------------------------------ */
    public void initOperationColors() {
        opColorList.clear();
        for (int i = 0; i < OP_AMOUNT; i++) {
            opColorList.add(OP_INACTIVE);
        }
    }

    /* ------------------------------------------ REFRESH ------------------------------------------ */
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    public int getProcessID() {
        return this.processID;
    }

    /* ------------------------------------------ GETTERS AND SETTERS ------------------------------------------ */
    // _________________________ PROCESS ID _________________________
    public void setProcessID(int pid) {
        this.processID = pid;
        refresh();
    }

    public int getVirtualAddress() {
        return this.virtualAddress;
    }

    // _________________________ VIRTUAL ADDRESS _________________________
    public void setVirtualAddress(int vaddr) {
        this.virtualAddress = vaddr;
        refresh();
    }
    // _________________________ OPERATION _________________________

    public String getOperationColor(int i) {
        return opColorList.get(i);
    }

    public void setOpColor(int i) {
        initOperationColors();
        if (i != -1) {
            opColorList.set(i, OP_ACTIVE);
        }
        refresh();
    }

    // _________________________ PHYSICAL ADDRESS _________________________
    public int getPhysicalAddress() {
        return this.physicalAddress;
    }

    // _________________________ FRAME NUMBER _________________________
    public int getFrameNumber() {
        return this.frameNumber;
    }

    // _________________________ OFFSET _________________________
    public int getOffset() {
        return this.offset;
    }
}
