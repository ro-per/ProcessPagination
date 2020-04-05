package oldFiles;

import java.util.ArrayList;
import java.util.List;


public class RAM {
    List<Process_old> processList = new ArrayList<>();  //max 4 processen
    Page_old[] frameArray = new Page_old[12];
    //List<Page> pageList = new ArrayList<>();
    private int frameCount;
    
    public RAM(int frameCount) {
        this.frameCount = frameCount;
    }
    
    public void addProcess(Process_old process, int timer) {
        if(processList.size() >= 4) {
            int min = Integer.MAX_VALUE;
            int index = -1;
            for( int i=0; i<processList.size(); i++) {
                if(processList.get(i).getLastAccessTime() < min) {
                    min = processList.get(i).getLastAccessTime();
                    index = i;
                }
            }
            removeProcess(processList.get(index).getProcessID());
        }
        processList.add(process);
        reAdjustFrames();
    }
    
    /*
    * Makes sure all frames are used when processes are active, redistribute if necessary
    */
    public void reAdjustFrames() {
        if(processList.size() > 0) {
            int framesPerProcess = frameCount/processList.size();
            for(Process_old p : processList) {
                while (p.getNumberOfPresentPages() != framesPerProcess) {
                    int test = p.getNumberOfPresentPages();
                    if(p.getNumberOfPresentPages() > framesPerProcess) {
                        removePage(p.getLRUPage());
                    }
                    else {
                        Page_old newPage = p.findNonPresentPage();
                        boolean foundNextEmptyFrame = false;
                        int emptyFrameIndex = 0;
                        while(!foundNextEmptyFrame) {
                            if(frameArray[emptyFrameIndex] == null) {
                                foundNextEmptyFrame = true;
                            }
                            else {
                                emptyFrameIndex++;
                            }
                        }
                        frameArray[emptyFrameIndex] = newPage;
                        p.updatePageTable(newPage.getPageNumber(), emptyFrameIndex, true, false);
                    }
                }
            }
        }
    }
    
    public void swapPage(int frameIndex, Page_old newPage) {
        Page_old oldPage = frameArray[frameIndex];
        if(oldPage != null) {
            Process_old oldPageProcess = findProcessByPage(oldPage);
            // Modify bit gets set to false if it was true
            oldPageProcess.updatePageTable(oldPage.getPageNumber(), 0, false, false);
            oldPageProcess.increaseWriteToRAM();
        }
        Process_old newPageProcess = findProcessByPage(newPage);
        newPageProcess.updatePageTable(newPage.getPageNumber(), frameIndex, true, false);
        newPageProcess.increaseReadFromRAM();
        frameArray[frameIndex] = newPage;
    }
    
    public Process_old findProcessByPage(Page_old page) {
        for(Process_old p : processList) {
            if(p.getProcessID() == page.getProcessID()) {
                return p;
            }
        }
        return null;
    }
    
    /*
    * Removes the specified process from the processlist and
    * the pages from that process from the pageList
    */
    public void removeProcess(int processId) {
        Process_old process = new Process_old(processId);
        for (Process_old value : processList) {
            if (value.getProcessID() == processId) {
                process = value;
            }
        }
        for (Page_old page : frameArray) {
            if (page.getProcessID() == process.getProcessID()) {
                removePage(page);
            }
        }
        processList.remove(process);
    }
    
    public void removePage(Page_old page) {
        Process_old process = findProcessByPage(page);
        int frameNumber = process.pageTable.get(page.getPageNumber()).getFrameNumber();
        process.updatePageTable(page.getPageNumber(), frameNumber, false, false);
        frameArray[frameNumber] = null;
    }
    
    /**
     * via gegeven virtueel adres distileer je pagenr en offset
     * 
     * @param virturalAddress
     * @return array met met 2 integers: pagenr en offset
     */
    public int[] analyseAdress(int virturalAddress){
        //zet om naar binair
        String binair = Integer.toString(virturalAddress,2);
        
        //zorg dat het 16bits lang is
        int nullen = 16-binair.length();
        StringBuilder binairAdress16= new StringBuilder();
        for(int i = 0; i < nullen; i++){
            binairAdress16.append("0");
        }
        binairAdress16.append(binair);
        
//      splits nu in pagenr en offset  (eerste 4 zijn pagenr en laatste 12 zijn 
        
        String binair16 = binairAdress16.toString();
        
        String pageNr = binair16.substring(0,binair16.length()-12);
        String offset = binair16.substring(binair16.length()-12);
        return new int [] {Integer.parseInt(pageNr,2),Integer.parseInt(offset,2)};
        
    }
    /**
     * 
     * @param pid
     * @return process if true else null
     */
    public Process_old isProcessInRam(int pid){
        for(Process_old p: processList){
            if(p.getProcessID() == pid) return p;
        }
        return null;
    }


    /**
     * eerst wordt gecontroleerd of het process al in de ram zit 
     * 
     * daarna wordt gecontroleerd of de page al in het ram zit
     * 
     * 
     * @param pid
     * @param pageNrAndOffset
     * @param time 
     */
    public void write(int pid, int[] pageNrAndOffset, int time){
        //eerst controleren of proces in ram zit 
        Process_old proc = isProcessInRam(pid);
        if(proc == null){//als proces zit nog niet in ramin ram
            proc = new Process_old(pid);
            addProcess(proc,time); // in die methode wordt alles gecontroleerd
        }
        if(proc.hasPageInRam(pageNrAndOffset)){
            setModifybit(1, proc,  pageNrAndOffset);
            setLastAccessTime(time, proc, pageNrAndOffset);
        }else{
                Page_old oldPage = proc.getLRUPage();// checkt in de frames
                int oldPageFrameNumber = proc.pageTable.get(oldPage.getPageNumber()).getFrameNumber();
                swapPage(oldPageFrameNumber, proc.pageList.get(pageNrAndOffset[0]));
                proc.updatePageTable(proc.pageList.get(pageNrAndOffset[0]).getPageNumber(), oldPageFrameNumber, true, true);
                proc.pageTable.get(pageNrAndOffset[0]).setLastAccessTime(time);
        }
        proc.setLastAccessTime(time);
              
    }
    
    
    void read(int pid, int[] pageNrAndOffset, int time) {
            //eerst controleren of proces in ram zit 
        Process_old proc = isProcessInRam(pid);
        if(proc == null){//als proces zit nog niet in ramin ram
                addProcess(proc,time); // in die methode wordt alles gecontroleerd 
        }
        if(proc.hasPageInRam(pageNrAndOffset)){
            setLastAccessTime(time, proc, pageNrAndOffset);
        }else{
                Page_old oldPage = proc.getLRUPage();//checkt in de frames
                int oldPageFrameNumber = proc.pageTable.get(oldPage.getPageNumber()).getFrameNumber();
                swapPage(oldPageFrameNumber, proc.pageList.get(pageNrAndOffset[0]));
                proc.updatePageTable(proc.pageList.get(pageNrAndOffset[0]).getPageNumber(), oldPageFrameNumber, true, false);
                proc.pageTable.get(pageNrAndOffset[0]).setLastAccessTime(time);
        }
        proc.setLastAccessTime(time);
    }
    /**
    * 
    * @param bitValue
    * @param pageNrAndOffset
    */
    public void setModifybit(int bitValue, Process_old process, int[] pageNrAndOffset) {
        int page = pageNrAndOffset[0];
        process.pageTable.get(page).setModifyBit(true);
    }
    /**
     * 
     * @param time
     * @param proc 
     */
    private void setLastAccessTime(int time, Process_old proc, int[]pageNrAndOffset) {
        int page = pageNrAndOffset[0];
        proc.pageTable.get(page).setLastAccessTime(time);
    }

    
}
