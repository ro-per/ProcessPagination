package gui.model;

import entities.ReadWriteTableEntry;
import javafx.beans.property.SimpleStringProperty;

public class RWTableENRTY {

    private final SimpleStringProperty processID;
    private final SimpleStringProperty readCount;
    private final SimpleStringProperty writeCount;

    public RWTableENRTY(){
        this.processID= new SimpleStringProperty(String.valueOf(-1));
        this.readCount= new SimpleStringProperty((String.valueOf(-1)));
        this.writeCount = new SimpleStringProperty(String.valueOf(-1));
    }



    public RWTableENRTY(String pid, String r, String w) {
        this.processID = new SimpleStringProperty(pid);
        this.readCount = new SimpleStringProperty(r);
        this.writeCount = new SimpleStringProperty(w);
    }
    public RWTableENRTY(int pid, int r, int w){
        this.processID=new SimpleStringProperty(String.valueOf(pid));
        this.readCount=new SimpleStringProperty(String.valueOf(r));
        this.writeCount=new SimpleStringProperty(String.valueOf(w));

    }


    public String getProcessID() {
        return processID.get();
    }

    public SimpleStringProperty processIDProperty() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID.set(processID);
    }

    public String getReadCount() {
        return readCount.get();
    }

    public SimpleStringProperty readCountProperty() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount.set(readCount);
    }

    public String getWriteCount() {
        return writeCount.get();
    }

    public SimpleStringProperty writeCountProperty() {
        return writeCount;
    }

    public void setWriteCount(String writeCount) {
        this.writeCount.set(writeCount);
    }
}
