package entities;

import java.util.ArrayList;
import java.util.List;

public class Process {

    private int processID;

    // Each process has 1 pagetable
    private List<Entry> pageTable;

    public Process(int processID){
        this.processID = processID;
        pageTable = initPageTable();
    }

    private List<Entry> initPageTable(){
        List<Entry> temp = new ArrayList<>();
        for (int i=0; i < 16; i++){
            temp.add(new Entry(new Page(processID,i)));
        }
        return temp;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }


}
