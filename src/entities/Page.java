package entities;

public class Page {
    private int processID;
    private int frameNumber;

    public Page(int processID, int frameNumber) {
        this.processID = processID;
        this.frameNumber = frameNumber;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }
}
