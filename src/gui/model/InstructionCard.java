package gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class InstructionCard extends Observable {

    private static final String OP_ACTIVE = "-fx-background-color: CHARTREUSE;";
    private static final String OP_INACTIVE = "-fx-background-color: WHITE;";
    private static final int OP_AMOUNT = 4;
    private List<String> opColorList = new ArrayList<>();

    // _________________________ INIT _________________________
    public void init() {
        opColorList.clear();
        for (int i = 0; i < OP_AMOUNT; i++) {
            opColorList.add(OP_INACTIVE);
        }
    }

    // _________________________ REFRESH _________________________
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    // _________________________ GETTERS _________________________
    public String getOpColor(int i) {
        return opColorList.get(i);
    }

    // _________________________ SETTERS _________________________
    public void setOpColor(int i) {
        init();
        opColorList.set(i, OP_ACTIVE);
        refresh();
    }

}
