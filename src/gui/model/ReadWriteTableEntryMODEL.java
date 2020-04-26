package gui.model;

import entities.ReadWriteTableEntry;
import javafx.beans.property.SimpleStringProperty;

public class ReadWriteTableEntryMODEL {

    private final SimpleStringProperty processID;
    private final SimpleStringProperty readCount;
    private final SimpleStringProperty writeCount;

    public ReadWriteTableEntryMODEL(int pid, int r, int w){
        this.processID=new SimpleStringProperty(String.valueOf(pid));
        this.readCount=new SimpleStringProperty(String.valueOf(r));
        this.writeCount=new SimpleStringProperty(String.valueOf(w));

    }

    public String getProcessID() {
        return processID.get();
    }

    public void setProcessID(String processID) {
        this.processID.set(processID);
    }

    public SimpleStringProperty processIDProperty() {
        return processID;
    }

    public String getReadCount() {
        return readCount.get();
    }

    public void setReadCount(String readCount) {
        this.readCount.set(readCount);
    }

    public SimpleStringProperty readCountProperty() {
        return readCount;
    }

    public String getWriteCount() {
        return writeCount.get();
    }

    public void setWriteCount(String writeCount) {
        this.writeCount.set(writeCount);
    }

    public SimpleStringProperty writeCountProperty() {
        return writeCount;
    }
}
