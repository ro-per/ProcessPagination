package entities;

public class Entry {
    private boolean isModified;
    private boolean isPresent;
    private int lastAccessTime;
    private int frameNumber;
    private final Page page;

    public Entry() {
        isModified = false;
        isPresent = false;
        lastAccessTime = -1;
        frameNumber = 0;
        page = null;
    }

    public Entry(Page page) {
        isModified = false;
        isPresent = false;
        lastAccessTime = -1;
        this.page = page;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public int getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(int lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public Page getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "\n Entry{" +
                "isModified=" + isModified +
                ", isPresent=" + isPresent +
                ", lastAccessTime=" + lastAccessTime +
                ", frameNumber=" + frameNumber +
                ", page=" + page +
                '}';
    }
}
