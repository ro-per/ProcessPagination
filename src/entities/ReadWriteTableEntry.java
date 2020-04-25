package entities;

public class ReadWriteTableEntry {

    private int processID;
    private int readCount;
    private int writeCount;

    public ReadWriteTableEntry() {
        this.processID = -1;
        this.readCount = -1;
        this.writeCount = -1;
    }

    public ReadWriteTableEntry(int p, int r, int w) {
        this.processID = p;
        this.readCount = r;
        this.writeCount = w;
    }

    @Override
    public String toString() {
        return "\n Entry{" +
                "pid=" + processID +
                ", read count=" + readCount +
                ", write count=" + writeCount +
                '}';
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(int writeCount) {
        this.writeCount = writeCount;
    }

    public void updateValues(ReadWriteTableEntry entry) {
        this.readCount = entry.readCount;
        this.writeCount = entry.writeCount;

    }
}
