package entities;

import java.util.ArrayList;
import java.util.List;

public class Process {

    private int processID;
    private int processLastAccessTime;
    private int writeCount;
    private int readCount;
    private static final int PAGE_AMOUNT = 16;

    // Each process has 1 page table
    private List<Entry> pageTable;

    public Process(int processID) {
        this.processID = processID;
        writeCount = 0;
        readCount = 0;
        pageTable = initPageTable();
    }

    private List<Entry> initPageTable() {
        List<Entry> tempPageTable = new ArrayList<>();
        for (int i = 0; i < PAGE_AMOUNT; i++) {
            tempPageTable.add(new Entry(new Page(processID, i)));
        }
        return tempPageTable;
    }

    public void updatePageTable(int pageNumber, int frameNumber, boolean isPresent, boolean isModified) {
        Entry entry = pageTable.get(pageNumber);
        entry.setFrameNumber(frameNumber);
        entry.setModified(isModified);
        entry.setPresent(isPresent);
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
        int count = 0;
        for (Entry entry : pageTable) {
            if (entry.isPresent()) {
                ++count;
            }
        }
        return count;
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
        int minimumAccessTime = Integer.MAX_VALUE;
        Page lruPage = null;
        for (Entry entry : pageTable) {
            if (entry.isPresent() && entry.getLastAccessTime() < minimumAccessTime) {
                minimumAccessTime = entry.getLastAccessTime();
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

    public void setPageLastAccessTime(int pageNumber, int time) {
        pageTable.get(pageNumber).setLastAccessTime(time);
    }

    public int getProcessLastAccessTime() {
        return processLastAccessTime;
    }

    public void setProcessLastAccessTime(int processLastAccessTime) {
        this.processLastAccessTime = processLastAccessTime;
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
                ", lastAccessTime=" + processLastAccessTime +
                ", writeCount=" + writeCount +
                ", readCount=" + readCount +
                ", pageTable=" + pageTable +
                '}';
    }
}
