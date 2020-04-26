package entities;

import utils.BinaryConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.Main.AMOUNT_OF_FRAMES;

public class Ram {
    private int framesPerProcess;
    private int processCounter;
    private int totalWrites;
    private int totalReads;
    private int amountOfDifferentProcesses;
    //Ram has maximum 12 frames or maximum 12 pages
    private Page[] presentPages = new Page[AMOUNT_OF_FRAMES];

    //Present Processes
    private List<Process> processes;
    private List<Process> processHistory;

    private ReplacementStrategy strategy;

    private Process currentProcess;
    private int offset;
    private int frameNumber;
    private int physicalAddress;

    //In Ram max 4 times same process
    //Each process has exactly the same amount of frames:
    // 1 process:12 frames/process
    // 2 processes: 6 frames/process
    // 3 processes: 4 frames/process
    // 4 processes: 3 frames/process

    public Ram(ReplacementStrategy strategy) {
        processCounter = 0;
        processes = new ArrayList<>();
        processHistory = new ArrayList<>();
        totalReads = 0;
        totalWrites = 0;
        amountOfDifferentProcesses = 4;
        this.strategy = strategy;
    }

    private void removeProcess(Process process) {
        if (process != null) {
            for (Page frame : presentPages) {
                if (frame.getPID() == process.getProcessID()) {
                    removePage(frame);
                }
            }
            processes.remove(process);
        }
    }


    private void removePage(Page page) {
        Process process = getProcess(page.getPID());
        int fnr;
        fnr = process.getFrameNumber(page.getPNR());
        process.updatePageTable(page.getPNR(), fnr, false, false);
        presentPages[fnr] = null;
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
        process.updatePageTable(page.getPNR(), index, true, false);
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
        Process cp = getProcess(processID);
        if (currentPage != null) {
            cp.updatePageTable(currentPage.getPNR(), 0, false, false);
            cp.increaseWriteCount();
            totalWrites++;
        }
        cp.updatePageTable(newPageNumber, currentFrameNumber, true, false);
        cp.increaseReadCount();
        totalReads++;
        presentPages[currentFrameNumber] = cp.getPage(newPageNumber);
    }

    public void write(int processID, int pageNumber, int time, int offset) {
        currentProcess = getProcess(processID);
        this.offset = offset;
        if (currentProcess.isPageInRam(pageNumber)) {
            currentProcess.setModified(pageNumber, true);
            frameNumber = currentProcess.getFrameNumber(pageNumber);
        } else {
            Page page = strategy.getPage(currentProcess);
            frameNumber = currentProcess.getFrameNumber(page.getPNR());
            swapPage(frameNumber, pageNumber, processID);
        }
        currentProcess.setPageLastAccessTime(pageNumber, time);
        currentProcess.setProcessLastAccessTime(time);
        physicalAddress = Integer.parseInt(BinaryConverter.convertToBinary(frameNumber, 4) + BinaryConverter.convertToBinary(this.offset, 12), 2);
    }

    public void read(int processID, int pageNumber, int time, int offset) {
        this.offset = offset;
        currentProcess = getProcess(processID);
        frameNumber = currentProcess.getFrameNumber(pageNumber);
        if (!currentProcess.isPageInRam(pageNumber)) {
            Page page = strategy.getPage(currentProcess);
            frameNumber = currentProcess.getFrameNumber(page.getPNR());
            swapPage(frameNumber, pageNumber, processID);
            currentProcess.updatePageTable(currentProcess.getPageNumber(pageNumber), frameNumber, true, false);
        }
        currentProcess.setPageLastAccessTime(pageNumber, time);
        currentProcess.setProcessLastAccessTime(time);
        physicalAddress = Integer.parseInt(BinaryConverter.convertToBinary(frameNumber, 4) + BinaryConverter.convertToBinary(this.offset, 12), 2);
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
        processHistory.add(process);
        currentProcess = process;
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

    public Page[] getPresentPages() {
        return presentPages;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }


    public int getPhysicalAddress() {
        return physicalAddress;
    }

    public int getFrameNumber() {
        return this.frameNumber;
    }

    public List<Process> getProcesses() {
        return this.processes;
    }

    public List<Process> getProcessHistory() {
        return this.processHistory;
    }


    @Override
    public String toString() {
        return "\n -------- Ram --------" +
                "\n ----FRAMES---- \n" + Arrays.toString(presentPages) +
                "\n ----PROCESSES---- \n" + processes +
                "\n ----FRAMES/PROCESS---- \n" + framesPerProcess +
                "\n ----PROCESS COUNTER---- \n" + processCounter +
                "\n ----TOTAL WRITES---- \n" + totalWrites +
                "\n ----TOTAL READS---- \n" + totalReads +
                "\n ---------------------";
    }
}