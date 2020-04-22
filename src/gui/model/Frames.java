package gui.model;

import entities.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static main.Main.FRAME_NUMBER;

public class Frames extends Observable {

    private List<Integer> framePidList = new ArrayList<>();
    private List<Integer> framePnrList = new ArrayList<>();

    public void init() {
        initFrameIDs();
        initFramePNRs();
        refresh();
    }

    // _________________________ INIT _________________________
    public void initFrameIDs() {
        framePidList.clear();
        for (int i = 0; i < FRAME_NUMBER; i++) {
            framePidList.add(-1);
        }
        refresh();
    }

    public void initFramePNRs() {
        framePnrList.clear();
        for (int i = 0; i < FRAME_NUMBER; i++) {
            framePnrList.add(-1);
        }
        refresh();
    }

    // _________________________ REFRESH _________________________
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    // _________________________ GETTERS _________________________
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

    // _________________________ SETTERS _________________________
    public void countPIDs() {
        for (int i = 0; i < FRAME_NUMBER; i++) {
            int temp = framePidList.get(i);
            temp++;
            framePidList.set(i, temp);
        }
        refresh();
    }

    public void countPNRs() {
        for (int i = 0; i < FRAME_NUMBER; i++) {
            int temp = framePnrList.get(i);
            temp++;
            framePnrList.set(i, temp);
        }
        refresh();
    }

    public void setFrames(Page[] frames) {
        for (int i = 0; i < FRAME_NUMBER; i++) {
            int pid;
            int pnr;
            try {
                pid = frames[i].getPID();
                pnr = frames[i].getPNR();
            }catch(NullPointerException ne){
                pid=-1;
                pnr=-1;
            }
            framePidList.set(i, pid);
            framePnrList.set(i, pnr);

        }
        refresh();
    }
}
