package gui.model;

import entities.Instruction;
import entities.InstructionReader;
import entities.Process;
import entities.Ram;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {
    //************************** ATTRIBUTES **************************

    private int clock;
    private String filename;
    private List<Integer> frame_pid = new ArrayList<Integer>();


    //************************** CONSTRUCTORS **************************
    public MainModel() throws ParserConfigurationException, SAXException, IOException {

        init();
    }

    //************************** METHODS **************************
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    public void init() throws IOException, SAXException, ParserConfigurationException {
        clock = 0;
        for (int i = 0; i < 6; i++) {
            frame_pid.add(0);
        }
        refresh();
    }

    //************************** CLOCK COUNTERS **************************
    public void countClockUp() {
        this.clock++;
        for (int i = 0; i < 6; i++) {
            frame_pid.add(i,1);
        }
        refresh();
    }

    //************************** GETTERS **************************
    public int getClock() {
        return clock;
    }

    //************************** SETTERS **************************
    public void setClock(int clock) {
        this.clock = clock;
        refresh();
    }

    public void setFilename(String filename) throws ParserConfigurationException, SAXException, IOException {
        this.filename = filename;
        init();
        refresh();
    }

    public int getFramePid(int i) {
        return frame_pid.get(i);
    }
}
