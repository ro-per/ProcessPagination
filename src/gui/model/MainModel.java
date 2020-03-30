package gui.model;

import java.util.Observable;

public class MainModel extends Observable {
    //************************** ATTRIBUTES **************************
    private Long clock;

    //************************** CONSTRUCTORS **************************
    public MainModel() {
        super();
        this.clock = Long.valueOf(0);
    }

    public MainModel(Long clock) {
        super();
        this.clock = clock;
    }

    //************************** METHODS **************************
    public void refresh() {
        setChanged();
        notifyObservers();
    }

    //************************** CLOCK COUNTERS **************************
    public void countClockUp() {
        this.clock++;
        refresh();
    }

    public void countClockDown() {
        this.clock--;
        refresh();
    }

    //************************** GETTERS **************************
    public Long getClock() {
        return clock;
    }

    //************************** SETTERS **************************
    public void setClock(Long clock) {
        this.clock = clock;
        refresh();
    }
}
