/**
 *
 * @author Ruben
 */
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
    
    public int getPageNumber() {
        return pageNumber;
    }
}
