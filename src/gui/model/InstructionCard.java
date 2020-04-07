package gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class InstructionCard extends Observable {


    private List<String> opColorList = new ArrayList<>();
    private String opACTIVE = "-fx-background-color: CHARTREUSE;";
    private String opINACTIVE = "-fx-background-color: WHITE;";
    private static final int OPAMOUNT=4;


    /* ******************** INIT ******************** */
    public void init() {
        opColorList.clear();
        for (int i = 0; i < OPAMOUNT; i++) {
            opColorList.add(opINACTIVE);
        }
    }

    /* ******************** REFRESH ******************** */
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    /* ******************** GETTERS ******************** */
    public String getOpColor(int i) {
        return opColorList.get(i);
    }

    /* ******************** SETTERS ******************** */
    public void setOpColor(int i) {
        init();
        opColorList.set(i,opACTIVE);
        refresh();
    }

}
