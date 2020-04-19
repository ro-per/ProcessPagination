package entities;

public class Page {

    private int processID;
    private int pageNumber;

    public Page(int processID, int pageNumber) {
        this.processID = processID;
        this.pageNumber = pageNumber;
    }

    public int getPID() {
        return processID;
    }

    public void setPID(int processID) {
        this.processID = processID;
    }

    public int getPNR() {
        return pageNumber;
    }

    public void setPNR(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "\n Page{" +
                "processID=" + processID +
                ", pageNumber=" + pageNumber +
                '}';
    }
}

