package entities;

import java.util.ArrayList;
import java.util.List;

public class Process {

    private int processID;
    private int processLastAccessTime;
    private int writeCount;
    private int readCount;

    // Each process has 1 page table
    private PageTable pageTable;

    public Process(int processID) {
        this.processID = processID;
        pageTable = new PageTable(processID);
        writeCount = 0;
        readCount = 0;
    }

    public void updatePageTable(int pageNumber, int frameNumber, boolean isPresent, boolean isModified) {
        pageTable.updatePageTable(pageNumber, frameNumber, isPresent, isModified);
    }

    public Page getNonPresentPage() {
        return pageTable.getNonPresentedPage();
    }

    public int getAmountOfPresentPages() {
        return pageTable.getAmountOfPresentPages();
    }

    public int getFrameNumber(int pageNumber) {
        return pageTable.getFrameNumber(pageNumber);
    }

    public Page getPage(int pageNumber) {
        return pageTable.getPage(pageNumber);
    }

    public int getPageNumber(int pageNumber) {
        return pageTable.getPageNumber(pageNumber);
    }

    public void increaseWriteCount() {
        writeCount++;
    }

    public void increaseReadCount() {
        readCount++;
    }

    public boolean isPageInRam(int pageNumber) {
        return pageTable.isPageInRam(pageNumber);
    }

    public void setModified(int pageNumber, boolean modified) {
        pageTable.setModified(pageNumber, modified);
    }

    public void setPageLastAccessTime(int pageNumber, int time) {
        pageTable.setPageLastAccessTime(pageNumber, time);
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

    public PageTable getPageTable() {
        return pageTable;
    }

    public int getReadCount() {
        return readCount;
    }

    public int getWriteCount() {
        return writeCount;
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
