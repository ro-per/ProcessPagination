package controller;

import model.Model;

import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer {

    private Model m;

    public void setModel(Model m){
        this.m=m;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
