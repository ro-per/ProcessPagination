package entities;

public class Entry {
    private boolean isModified;
    private boolean isPersistent;
    private int lastAccessTime;
    private int frameNumber;
    private final Page page;

    public Entry(){
        isModified = false;
        isPersistent = false;
        lastAccessTime = -1;
        frameNumber = 0;
        this.page =null;
    }

    public Entry(Page page){
        isModified = false;
        isPersistent = false;
        lastAccessTime = -1;
        this.page = page;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
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
}
