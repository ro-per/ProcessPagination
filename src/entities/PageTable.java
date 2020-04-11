package entities;

import java.util.ArrayList;
import java.util.List;

public class PageTable {

    private List<Entry> entries;
    public static final int PAGE_TABLE_LENGTH = 16;

    public PageTable(int processId) {
        entries = initPageTable(processId);
    }

    public List<Entry> initPageTable(int processID) {
        List<Entry> tempPageTable = new ArrayList<>();
        for (int i = 0; i < PAGE_TABLE_LENGTH; i++) {
            tempPageTable.add(new Entry(new Page(processID, i)));
        }
        return tempPageTable;
    }

    public void updatePageTable(int pageNumber, int frameNumber, boolean isPresent, boolean isModified) {
        Entry entry = entries.get(pageNumber);
        entry.setFrameNumber(frameNumber);
        entry.setModified(isModified);
        entry.setPresent(isPresent);
    }

    public Page getNonPresentedPage() {
        for (Entry entry : entries) {
            if (!entry.isPresent()) {
                return entry.getPage();
            }
        }
        return null;
    }

    public int getAmountOfPresentPages() {
        int count = 0;
        for (Entry entry : entries) {
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
        return entries.get(pageNumber).getPage().getPageNumber();
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

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "{" +
                "entries=" + entries +
                '}';
    }
}
