package gui.model;

import entities.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static main.Main.FRAME_NUMBER;

public class FramesMODEL extends Observable {
    /* ------------------------------------------ ATTRIBUTES ------------------------------------------ */
    private List<Integer> frameProcessIDs = new ArrayList<>();
    private List<Integer> frameProcessNRs = new ArrayList<>();

    /* ------------------------------------------ CONSTRUCTORS ------------------------------------------ */
    public FramesMODEL() {
        initFrameProcessIDs();
        initFrameProcessNRs();
        refresh();
    }

    /* ------------------------------------------ INIT ------------------------------------------ */
    public void initFrameProcessIDs() {
        frameProcessIDs.clear();
        for (int i = 0; i < FRAME_NUMBER; i++) {
            frameProcessIDs.add(-1);
        }
        refresh();
    }

    public void initFrameProcessNRs() {
        frameProcessNRs.clear();
        for (int i = 0; i < FRAME_NUMBER; i++) {
            frameProcessNRs.add(-1);
        }
        refresh();
    }

    /* ------------------------------------------ REFRESH ------------------------------------------ */
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /* ------------------------------------------ GETTERS AND SETTERS ------------------------------------------ */
    public int getFrameProcessID(int i) {
        return frameProcessIDs.get(i);
    }

    public int getFrameProcessNR(int i) {
        return frameProcessNRs.get(i);
    }

    public void setFrames(Page[] frames) {
        for (int i = 0; i < FRAME_NUMBER; i++) {
            int pid;
            int pnr;
            try {
                pid = frames[i].getPID();
                pnr = frames[i].getPNR();
            } catch (NullPointerException ne) {
                pid = -1;
                pnr = -1;
            }
            frameProcessIDs.set(i, pid);
            frameProcessNRs.set(i, pnr);

        }
        refresh();
    }
}
