package gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static main.MainNew.FRAMENUMBER;

public class Frames extends Observable {

    private List<Integer> framePidList = new ArrayList<>();
    private List<Integer> framePnrList = new ArrayList<>();

    public void init() {
        initFrameIDs();
        initFramePNRs();
        refresh();
    }
    /* ******************** INIT ******************** */
    public void initFrameIDs() {
        framePidList.clear();
        for (int i = 0; i < FRAMENUMBER; i++) {
            framePidList.add(0);
        }
        refresh();
    }

    public void initFramePNRs() {
        framePnrList.clear();
        for (int i = 0; i < FRAMENUMBER; i++) {
            framePnrList.add(0);
        }
        refresh();
    }

    /* ******************** REFRESH ******************** */
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /* ******************** GETTERS ******************** */
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
    /* ******************** SETTERS ******************** */
    public void countPIDs() {
        for (int i = 0; i < FRAMENUMBER; i++) {
            int temp = framePidList.get(i);
            temp++;
            framePidList.set(i, temp);
        }
        refresh();
    }

    public void countPNRs() {
        for (int i = 0; i < FRAMENUMBER; i++) {
            int temp = framePnrList.get(i);
            temp++;
            framePnrList.set(i, temp);
        }
        refresh();
    }
}
