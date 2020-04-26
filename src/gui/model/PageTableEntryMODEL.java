package gui.model;

import javafx.beans.property.SimpleStringProperty;

public class PageTableEntryMODEL {
    private final SimpleStringProperty page;
    private final SimpleStringProperty presentBit;
    private final SimpleStringProperty modifyBit;
    private final SimpleStringProperty lastTimeAccessed;
    private final SimpleStringProperty frameNumber;

    public PageTableEntryMODEL(String p, String pb, String mb, String lta, String fn) {
        this.page = new SimpleStringProperty(p);
        this.presentBit = new SimpleStringProperty(pb);
        this.modifyBit = new SimpleStringProperty(mb);
        this.lastTimeAccessed = new SimpleStringProperty(lta);
        this.frameNumber = new SimpleStringProperty(fn);
    }

    public String getPage() {
        return page.get();
    }

    public void setPage(String fName) {
        page.set(fName);
    }

    public String getPresentBit() {
        return presentBit.get();
    }

    public void setPresentBit(String fName) {
        presentBit.set(fName);
    }

    public String getModifyBit() {
        return modifyBit.get();
    }

    public void setModifyBit(String fName) {
        modifyBit.set(fName);
    }

    public String getLastTimeAccessed() {
        return lastTimeAccessed.get();
    }

    public void setLastTimeAccessed(String lastTimeAccessed) {
        this.lastTimeAccessed.set(lastTimeAccessed);
    }

    public SimpleStringProperty lastTimeAccessedProperty() {
        return lastTimeAccessed;
    }

    public String getFrameNumber() {
        return frameNumber.get();
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber.set(frameNumber);
    }

    public SimpleStringProperty frameNumberProperty() {
        return frameNumber;
    }
}
