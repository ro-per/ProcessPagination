package model;

import java.util.List;

public class Process {

    private int processID;

    // Each process has 1 pagetable
    private List<Entry> pageTabel;

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

}
