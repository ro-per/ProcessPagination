package entities;

import java.util.ArrayList;
import java.util.List;

public class Process {

    private int processID;
    private int lastAccessTime;
    private int writeCount;
    private int readCount;

    // Each process has 1 pagetable
    private List<Entry> pageTable;

    public Process(int processID) {
        this.processID = processID;
        writeCount = 0;
        readCount = 0;
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

    public Page getPage(int pageNumber) {
        return pageTable.get(pageNumber).getPage();
    }

    public int getPageNumber(int pageNumber) {
        return pageTable.get(pageNumber).getPage().getPageNumber();
    }

    public Page getLruPage() {
        int min = Integer.MAX_VALUE;
        Page lruPage = null;
        for (Entry entry : pageTable) {
            if (entry.isPresent() && entry.getLastAccessTime() < min) {
                min = entry.getLastAccessTime();
                lruPage = entry.getPage();
            }
        }
        return lruPage;
    }

    public void increaseWriteCount() {
        writeCount++;
    }

    public void increaseReadCount() {
        readCount++;
    }

    public boolean isPageInRam(int pageNumber) {
        return pageTable.get(pageNumber).isPresent();
    }

    public void setModified(int pageNumber, boolean modified) {
        pageTable.get(pageNumber).setModified(modified);
    }

    public void setLastAccesTime(int pageNumber, int time) {
        pageTable.get(pageNumber).setLastAccessTime(time);
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
                ", writeCount=" + writeCount +
                ", readCount=" + readCount +
                ", pageTable=" + pageTable +
                '}';
    }
}
