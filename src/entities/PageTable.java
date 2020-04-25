package entities;

import java.util.ArrayList;
import java.util.List;

import static main.Main.PAGE_TABLE_LENGTH;


public class PageTable {

    private List<PageTableEntry> entries;

    public PageTable() {
        entries = new ArrayList<>();
        for (int i = 0; i < PAGE_TABLE_LENGTH; i++) {
            entries.add(new PageTableEntry(new Page()));
        }
    }

    public PageTable(int processId) {
        entries = initPageTable(processId);
    }

    public List<PageTableEntry> initPageTable(int processID) {
        List<PageTableEntry> tempPageTable = new ArrayList<>();
        for (int i = 0; i < PAGE_TABLE_LENGTH; i++) {
            tempPageTable.add(new PageTableEntry(new Page(processID, i)));
        }
        return tempPageTable;
    }

    public void updatePageTable(int pageNumber, int frameNumber, boolean isPresent, boolean isModified) {
        PageTableEntry entry = entries.get(pageNumber);
        entry.setFrameNumber(frameNumber);
        entry.setModified(isModified);
        entry.setPresent(isPresent);
    }

    public Page getNonPresentedPage() {
        for (PageTableEntry entry : entries) {
            if (!entry.isPresent()) {
                return entry.getPage();
            }
        }
        return null;
    }

    public int getAmountOfPresentPages() {
        int count = 0;
        for (PageTableEntry entry : entries) {
            if (entry.isPresent()) {
                ++count;
            }
        }
        return count;
    }

    public int getFrameNumber(int pageNumber) {
        return entries.get(pageNumber).getFrameNumber();
    }

    public Page getPage(int pageNumber) {
        return entries.get(pageNumber).getPage();
    }

    public int getPageNumber(int pageNumber) {
        return entries.get(pageNumber).getPage().getPNR();
    }

    public boolean isPageInRam(int pageNumber) {
        return entries.get(pageNumber).isPresent();
    }

    public void setModified(int pageNumber, boolean modified) {
        entries.get(pageNumber).setModified(modified);
    }

    public void setPageLastAccessTime(int pageNumber, int time) {
        entries.get(pageNumber).setLastAccessTime(time);
    }

    public List<PageTableEntry> getEntries() {
        return entries;
    }

    public PageTableEntry getEntry(int i) {
        return this.entries.get(i);
    }

    @Override
    public String toString() {
        return "{" +
                "entries=" + entries +
                '}';
    }
}
