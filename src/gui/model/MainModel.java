package gui.model;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static main.MainNew.FRAMENUMBER;

public class MainModel extends Observable {

    /* ******************** ATTRIBUTES ******************** */

    private int CLK;
    private String filename;



    private List<String> opColorList = new ArrayList<>();
    private String opACTIVE="-fx-background-color: CHARTREUSE;";
    private String opINACTIVE="-fx-background-color: firebrick;";

    private List<Integer> framePidList = new ArrayList<>();
    private List<Integer> framePnrList = new ArrayList<>();


    /* ******************** CONSTRUCTORS ******************** */
    public MainModel() throws ParserConfigurationException, SAXException, IOException {
        init();
    }

    /* ******************** INIT ******************** */
    public void init() throws IOException, SAXException, ParserConfigurationException {
        //ANCHOR_LEFT
        CLK = 0;

        initOpColors();

        //ANCHOR_FRAMES
        initFrameIDs();
        initFramePNRs();
        //REFRESH
        refresh();
    }
    public void initOpColors(){
        opColorList.clear();
        for (int i = 0; i < 8; i++) {
            opColorList.add(opINACTIVE);
        }
    }

    public void initFrameIDs() {
        framePidList.clear();
        for (int i = 0; i < FRAMENUMBER; i++) {
            framePidList.add(0);
        }
    }

    public void initFramePNRs() {
        framePnrList.clear();
        for (int i = 0; i < FRAMENUMBER; i++) {
            framePnrList.add(0);
        }
    }
    /* ******************** REFRESH ******************** */

    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /* ******************** COUNTERS ******************** */
    public void countCLK() {
        this.CLK++;
        refresh();
    }

    /* ******************** GETTERS ******************** */
    public int getCLK() {
        return CLK;
    }

    public int getFramePidSingle(int i) {
        return framePidList.get(i);
    }

    public List<Integer> getFramePidList() {
        return framePidList;
    }

    public int getFramePnrSingle(int i) {
        return framePnrList.get(i);
    }

    public List<Integer> getFramePnrList() {
        return framePnrList;
    }
    public String getOpColorSingle(int i) {
        return opColorList.get(i);
    }

    /* ******************** SETTERS ******************** */
    //MENU
    public void setFilename(String filename) throws ParserConfigurationException, SAXException, IOException {
        this.filename = filename;
        init();
        refresh();
    }

    //ANCHOR_LEFT
    public void setCLK(int clock) {
        this.CLK = clock;
        refresh();
    }
    public void setCurOpColorACTIVE(int i) {
        opColorList.set(i,opACTIVE);
        refresh();
    }
    public void setCurOpColorINACTIVE(int i) {
        opColorList.set(i,opINACTIVE);
        refresh();
    }
    //ANCHOR_PAGES

    //ANCHOR_FRAMES
    public void setFramePIDs() {
        for (int i = 0; i < FRAMENUMBER; i++) {
            int temp = framePidList.get(i);
            temp++;
            framePidList.set(i, temp);
        }
        refresh();
    }

    public void setFramePNRs() {
        for (int i = 0; i < FRAMENUMBER; i++) {
            int temp = framePnrList.get(i);
            temp++;
            framePnrList.set(i, temp);
        }
        refresh();
    }
    //ANCHOR_PROCESSES

}
