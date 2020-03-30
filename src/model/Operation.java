package model;

public class Operation {

    public Operation(){
    }

    //start process
    public static final Operation S =new Operation();
    //read process virtual address
    public static final  Operation R = new Operation();
    //write process virtual address
    public static final Operation W = new Operation();
    //end process
    public static final Operation T = new Operation();
}
