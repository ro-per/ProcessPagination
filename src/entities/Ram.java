package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ram {

    //Ram has max. 12 frames or 12 pages
    Page[] frames = new Page[12];
    //Present Processes
    List<Process> processes;

    private int framesPerProcess;
    private int processCounter;

    //In Ram max 4 times same process
    //Each process has exactly the same amount of frames:
    // 1 process:   12 frames/process
    // 2 processes: 6 frames/process
    // 3 processes: 4 frames/process
    // 4 processes: 3 frames/process

    public Ram() {
        processCounter = 0;
        processes = new ArrayList<>();
    }

    public void addProcess(Process process) {
        if (processes.size() >= 4) {
            removeLastUsedProcess();
        }
        processes.add(process);
    }

    public void removeLastUsedProcess() {
        int min = Integer.MAX_VALUE;
        Process tempProcess = null;
        for (Process process : processes) {
            if (process.getLastAccessTime() < min) {
                min = process.getLastAccessTime();
                tempProcess = process;
            }
        }
        removeProcess(tempProcess);
    }

    public void removeProcess(Process process) {
        if (process != null) {
            for (Page frame : frames) {
                if (frame.getProcessID() == process.getProcessID()) {
                    removePage(process);
                }
            }
            processes.remove(process);
        }
    }

    public void removeProcess(int processID){
        Process process = getProcess(processID);
        removeProcess(process);
    }


    public void removePage(Process process) {
        Page page = process.getLruPage();
        int pageNumber = page.getPageNumber();
        int frameNumber = process.getFrameNumber(pageNumber);
        process.updatePageTable(pageNumber, frameNumber, false, false);
        frames[frameNumber] = null;
    }

    public void adjustFrames() {
        if (!processes.isEmpty()) {
            framesPerProcess = frames.length / processes.size();
            for (Process process : processes) {
                while (process.getAmountOfPresentPages() != framesPerProcess) {
                    if (process.getAmountOfPresentPages() > framesPerProcess) {
                        removePage(process);
                    } else {
                        int index = 0;
                        Page page = process.getNonPresentPage();
                        boolean isEmptyFrame = false;
                        while (!isEmptyFrame) {
                            if (frames[index] == null) {
                                isEmptyFrame = true;
                            } else {
                                index++;
                            }
                        }
                        frames[index] = page;
                        process.updatePageTable(page.getPageNumber(), index, true, false);
                    }
                }
            }
        }
    }


    public void write(int processID, int pageNumber, int time) {
        Process currentProcess = getProcess(processID);
        if (currentProcess == null) {
            currentProcess = new Process(processID);
            addProcess(currentProcess);
        }
        if (currentProcess.isPageInRam(pageNumber)) {
            currentProcess.setModified(pageNumber, true);
        } else {
            Page page = currentProcess.getLruPage();
            int currentFrameNumber = currentProcess.getFrameNumber(page.getPageNumber());
            swapPage(currentFrameNumber, pageNumber, processID);
        }
        currentProcess.setLastAccesTime(pageNumber, time);
        currentProcess.setLastAccessTime(time);
    }

    public void read(int processID, int pageNumber, int time) {
        Process currentProcess = getProcess(processID);
        if (currentProcess == null) {
            addProcess(currentProcess);
        }
        if (!currentProcess.isPageInRam(pageNumber)) {
            Page page = currentProcess.getLruPage();
            int currentFrameNumber = currentProcess.getFrameNumber(page.getPageNumber());
            swapPage(currentFrameNumber, pageNumber, processID);
            currentProcess.updatePageTable(currentProcess.getPageNumber(pageNumber), currentFrameNumber, true, false);
        }
        currentProcess.setLastAccesTime(pageNumber, time);
        currentProcess.setLastAccessTime(time);
    }

    public Process getProcess(int processID) {
        for (Process process : processes) {
            if (process.getProcessID() == processID) {
                return process;
            }
        }
        return null;
    }

    public void swapPage(int currentFrameNumber, int newPageNumber, int processID) {
        Page currentPage = frames[currentFrameNumber];
        Process currentProcess = getProcess(processID);
        if (currentPage != null) {
            currentProcess.updatePageTable(currentPage.getPageNumber(), 0, false, false);
        }
        currentProcess.updatePageTable(newPageNumber, currentFrameNumber, true, false);
        frames[currentFrameNumber] = currentProcess.getPage(newPageNumber);
    }

    @Override
    public String toString() {
        return "Ram{" +
                "frames=" + Arrays.toString(frames) +
                ", processes=" + processes +
                ", framesPerProcess=" + framesPerProcess +
                ", processCounter=" + processCounter +
                '}';
    }
}