package entities;

public class Ram {

    //Ram has max. 12 frames or 12 pages
    Page[] frames = new Page[12];
    private int frameTotal;

    //In Ram max 4 times same process
    //Each process has exactly the same amount of frames:
    // 1 process:   12 frames/process
    // 2 processes: 6 frames/process
    // 3 processes: 4 frames/process
    // 4 processes: 3 frames/process

    public Ram(int frameTotal){
        this.frameTotal = frameTotal;
    }

    public void removePage(int processId){
        //TODO
    }

    public void addPage(){
        //TODO
    }

    public void swapPage(){
        //TODO
    }

    public void adjustFrames(){
        //TODO
    }



}