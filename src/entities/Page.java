package entities;

public class Page {
    private int processID;
    private int pageNumber;

    public Page(int processID, int pageNumber) {
        this.processID = processID;
        this.pageNumber = pageNumber;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
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

