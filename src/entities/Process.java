package entities;

import java.util.ArrayList;
import java.util.List;

public class Process {

    private int processID;
    private int lastAccessTime;

    // Each process has 1 pagetable
    private List<Entry> pageTable;

    public Process(int processID) {
        this.processID = processID;
        pageTable = initPageTable();
    }

    private List<Entry> initPageTable() {
        List<Entry> temp = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            temp.add(new Entry(new Page(processID, i)));
        }
        return temp;
    }

    public void updatePageTable(int pageNumber, int frameNumber, boolean isPresent, boolean isModified) {
        Entry temp = pageTable.get(pageNumber);
        temp.setFrameNumber(frameNumber);
        temp.setModified(isModified);
        temp.setPresent(isPresent);
    }

    public Page getNonPresentPage() {
        for (Entry entry : pageTable) {
            if (!entry.isPresent()) {
                return entry.getPage();
            }
        }
        return null;
    }

    public int getAmountOfPresentPages() {
        int temp = 0;
        for (Entry entry : pageTable) {
            if (entry.isPresent()) {
                temp++;
            }
        }
        return temp;
    }

    public int getFrameNumber(int pageNumber) {
        return pageTable.get(pageNumber).getFrameNumber();
    }

    public Page getLruPage() {
        int min = Integer.MAX_VALUE;
        Page LruPage = null;
        for (Entry entry : pageTable) {
            if (entry.isPresent() && entry.getLastAccessTime() < min) {
                min = entry.getLastAccessTime();
                LruPage = entry.getPage();
            }
        }
        return LruPage;
    }

    public int getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(int lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public List<Entry> getPageTable() {
        return pageTable;
    }

    public void setPageTable(List<Entry> pageTable) {
        this.pageTable = pageTable;
    }

    @Override
    public String toString() {
        return "\n Process{" +
                "processID=" + processID +
                ", lastAccessTime=" + lastAccessTime +
                ", pageTable=" + pageTable +
                '}';
    }
}
