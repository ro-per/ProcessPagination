package gui.model;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Observable;

public class MainModel extends Observable {

    /* ******************** ATTRIBUTES ******************** */

    private int clk;
    private String file;

    private Frames frames = new Frames();
    private CurrentInstruction cOP = new CurrentInstruction();
    private PreviousInstruction pOP = new PreviousInstruction();

    /* ******************** CONSTRUCTORS ******************** */
    public MainModel() throws ParserConfigurationException, SAXException, IOException {
        resetModel();
    }

    /* ******************** INIT ******************** */
    public void resetModel() {
        initMain();
        frames.init();
        cOP.init();
        pOP.init();
        refresh();
    }
    public void initMain() {
        //ANCHOR_LEFT
        clk = 0;
        //REFRESH
        refresh();
    }


    /* ******************** REFRESH ******************** */

    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /* ******************** COUNTERS ******************** */
    public void countCLK() {
        this.clk++;
        refresh();
    }

    /* ******************** GETTERS ******************** */
    public int getClk() {
        return clk;
    }
    public String getCopColors(int i) {
        return cOP.getOpColor(i);
    }
    public String getPopColors(int i) {
        return pOP.getOpColor(i);
    }
    public int getFramePid(int i) {
        return frames.getFramePidSingle(i);
    }
    public int getFramePnr(int i){
        return frames.getFramePnrSingle(i);
    }

    /* ******************** SETTERS ******************** */
    public void setFile(String file) throws ParserConfigurationException, SAXException, IOException {
        this.file = file;
        initMain();
        refresh();
    }
    public void setClk(int clock) {
        this.clk = clock;
        refresh();
    }
    public void setFramePIDs() {
        frames.countPIDs();
    }
    public void setFramePNRs() {
        frames.countPNRs();
    }
    public void setOpColors(char c, int i) {
        if (c=='C'){
            cOP.setOpColor(i);
        }else{
            pOP.setOpColor(i);
        }
    }
}
