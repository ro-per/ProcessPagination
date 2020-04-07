package gui.controller;

import gui.model.MainModel;

import java.util.Observable;
import java.util.Observer;

public class FileChooserController implements Observer {

    private MainModel model;

    public void setModel(MainModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
