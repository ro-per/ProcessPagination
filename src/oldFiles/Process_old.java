package oldFiles;

import java.util.ArrayList;

public class Process_old {
    private int processID;
    private int lastAccessTime;
    private int readFromRAM;
    private int writeToRAM;
    
    ArrayList<Page_old> pageList = new ArrayList<Page_old>();
    ArrayList <PageTableEntry> pageTable = new ArrayList<>();
    
    public Process_old(int processID) {
        this.processID = processID;
        initializePageList();
        initializePageTable();
        readFromRAM = 0;
        writeToRAM = 0;
    }
    
    private void initializePageList() {
        for(int i=0; i<16;i++) {
            this.pageList.add(new Page_old(this.processID, i));
        }
    }
    
    private void initializePageTable() {
        for(int i=0; i<16;i++) {
            this.pageTable.add(new PageTableEntry());
        }
    }
    
    public int getProcessID() {
        return this.processID;
    }
    
    public int getLastAccessTime() {
        return this.lastAccessTime;
    }
    public void setLastAccessTime(int lat){
        this.lastAccessTime = lat;
    }
    
    public void increaseReadFromRAM() {
        readFromRAM++;
    }
    
    public void increaseWriteToRAM() {
        writeToRAM++;
    }
    
    public int getReadFromRAM() {
        return readFromRAM;
    }
    
    public int getWriteToRAM() {
        return writeToRAM;
    }
    
    public int getNumberOfPresentPages() {
        int numberOfPresentPages = 0;
        for(PageTableEntry pte : pageTable) {
            if(pte.getPresentBit()) {
                numberOfPresentPages++;
            }
        }
        return numberOfPresentPages;
    }
    /**
     * controleert presentbit true of false
     * 
     * @param pageNrAndOffset
     * @return true als page aanwezig 
     */
    public boolean hasPageInRam(int[] pageNrAndOffset) {
        int page = pageNrAndOffset[0];
        return pageTable.get(page).getPresentBit();
    }

    
    public Page_old findNonPresentPage() {
        for(int i=0; i<pageTable.size(); i++) {
            if(!pageTable.get(i).getPresentBit()) {
                return pageList.get(i);
            }
        }
        return null;
    }
    
    public int getLRUFrameNumber() {
        PageTableEntry oldestPTE = new PageTableEntry();
        for(PageTableEntry pte : pageTable) {
            if(pte.getPresentBit() && pte.getLastAccessTime() < oldestPTE.getLastAccessTime()) {
                oldestPTE = pte;
            }
        }
        return oldestPTE.getFrameNumber();
    }
    
    public Page_old getLRUPage() {
        int index = -1;
        int min = Integer.MAX_VALUE;
        for(int i=0; i<pageTable.size(); i++) {
            if(pageTable.get(i).getPresentBit() && pageTable.get(i).getLastAccessTime() < min) {
                min = pageTable.get(i).getLastAccessTime();
                index = i;
            }
        }
        return pageList.get(index);
    }
    
    public void updatePageTable(int pageNumber, int frameNumber, boolean presentBit, boolean modifyBit) {
        PageTableEntry pte = pageTable.get(pageNumber);
        pte.setFrameNumber(frameNumber);
        pte.setPresentBit(presentBit);
        pte.setModifyBit(modifyBit);
    }
    
}
