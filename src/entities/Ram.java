package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ram {

    private int framesPerProcess;
    private int processCounter;
    private int totalWrites;
    private int totalReads;
    private int amountOfDifferentProcesses;
    public static final int AMOUNT_OF_FRAMES = 12;

    //Ram has max. 12 frames or maximum 12 pages
    private Page[] presentPages = new Page[AMOUNT_OF_FRAMES];

    //Present Processes
    private List<Process> processes;

    private ReplacementStrategy strategy;

    //In Ram max 4 times same process
    //Each process has exactly the same amount of frames:
    // 1 process:   12 frames/process
    // 2 processes: 6 frames/process
    // 3 processes: 4 frames/process
    // 4 processes: 3 frames/process

    public Ram(ReplacementStrategy strategy) {
        processCounter = 0;
        processes = new ArrayList<>();
        totalReads = 0;
        totalWrites = 0;
        amountOfDifferentProcesses = 4;
        this.strategy = strategy;
    }

    private void removeProcess(Process process) {
        if (process != null) {
            for (Page frame : presentPages) {
                if (frame.getProcessID() == process.getProcessID()) {
                    removePage(frame);
                }
            }
            processes.remove(process);
        }
    }


    private void removePage(Page page) {
        Process process = getProcess(page.getProcessID());
        int frameNumber;
        frameNumber = process.getFrameNumber(page.getPageNumber());
        process.updatePageTable(page.getPageNumber(), frameNumber, false, false);
        presentPages[frameNumber] = null;
    }

    private void adjustFrames() {
        if (!processes.isEmpty()) {
            framesPerProcess = presentPages.length / processes.size();
            for (Process process : processes) {
                while (process.getAmountOfPresentPages() != framesPerProcess) {
                    if (process.getAmountOfPresentPages() > framesPerProcess) {
                        removePage(strategy.getPage(process));
                    } else {
                        int index = 0;
                        Page page = process.getNonPresentPage();
                        updateFrames(process.getProcessID(), page, index);
                    }
                }
            }
        }
    }

    private void updateFrames(int processID, Page page, int index) {
        Process process = getProcess(processID);
        while (presentPages[index] != null) {
            index++;
        }
        presentPages[index] = page;
        process.updatePageTable(page.getPageNumber(), index, true, false);
    }

    private Process getProcess(int processID) {
        for (Process process : processes) {
            if (process.getProcessID() == processID) {
                return process;
            }
        }
        return null;
    }

    private void swapPage(int currentFrameNumber, int newPageNumber, int processID) {
        Page currentPage = presentPages[currentFrameNumber];
        Process currentProcess = getProcess(processID);
        if (currentPage != null) {
            currentProcess.updatePageTable(currentPage.getPageNumber(), 0, false, false);
            currentProcess.increaseWriteCount();
            totalWrites++;
        }
        currentProcess.updatePageTable(newPageNumber, currentFrameNumber, true, false);
        currentProcess.increaseReadCount();
        totalReads++;
        presentPages[currentFrameNumber] = currentProcess.getPage(newPageNumber);
    }

    public void write(int processID, int pageNumber, int time) {
        Process currentProcess = getProcess(processID);

        if (currentProcess.isPageInRam(pageNumber)) {
            currentProcess.setModified(pageNumber, true);
        } else {
            Page page = strategy.getPage(currentProcess);
            int currentFrameNumber = currentProcess.getFrameNumber(page.getPageNumber());
            swapPage(currentFrameNumber, pageNumber, processID);
        }
        currentProcess.setPageLastAccessTime(pageNumber, time);
        currentProcess.setProcessLastAccessTime(time);
    }

    public void read(int processID, int pageNumber, int time) {
        Process currentProcess = getProcess(processID);

        if (!currentProcess.isPageInRam(pageNumber)) {
            Page page = strategy.getPage(currentProcess);
            int currentFrameNumber = currentProcess.getFrameNumber(page.getPageNumber());
            swapPage(currentFrameNumber, pageNumber, processID);
            currentProcess.updatePageTable(currentProcess.getPageNumber(pageNumber), currentFrameNumber, true, false);
        }
        currentProcess.setPageLastAccessTime(pageNumber, time);
        currentProcess.setProcessLastAccessTime(time);
    }

    public void removeProcess(int processID) {
        Process process = getProcess(processID);
        removeProcess(process);
        adjustFrames();
    }

    public void addProcess(Process process) {
        if (processes.size() >= amountOfDifferentProcesses) {
            removeProcess(strategy.getProcess(processes));
        }
        processes.add(process);
        adjustFrames();
    }

    public void changeStrategy(ReplacementStrategy strategy) {
        this.strategy = strategy;
    }

    public void setAmountOfDifferentProcesses(int amountOfDifferentProcesses) {
        if (amountOfDifferentProcesses > 0) {
            this.amountOfDifferentProcesses = amountOfDifferentProcesses;
        }
    }

    @Override
    public String toString() {
        return "Ram{" +
                "frames=" + Arrays.toString(presentPages) +
                ", processes=" + processes +
                ", framesPerProcess=" + framesPerProcess +
                ", processCounter=" + processCounter +
                ", totalWrites=" + totalWrites +
                ", totalReads=" + totalReads +
                '}';
    }
}