import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/**
 *
 *
 *
 * This is the controller which binds the model and the view
 */
public class VMController {
    
    private VMView view;
    private VMModel model;
    private String operation;
    
    public VMController(VMView view, VMModel model) {
        this.view = view;
        this.model = model;
        this.view.addOneInstructionListener(new OneInstructionListener());
        this.view.addAllInstructionsListener(new AllInstructionsListener());
        this.view.addCancelListener(new CancelListener());
        this.view.addRadioButton1Listener(new RadioButtonListener());
        this.view.addRadioButton2Listener(new RadioButtonListener());
        this.view.addRadioButton3Listener(new RadioButtonListener());
        this.model.init();
        this.view.update(model.getCurrentInstruction(), null);
    }
    
    class RadioButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try{
                String button = ((JRadioButton) e.getSource()).getActionCommand();
                switch(button){
                    case "20 - 3":
                        model.setFileName("Instructions_30_3.xml");
                        resetView();
                        break;
                    case "20000 - 4":
                        model.setFileName("Instructions_20000_4.xml");   
                        resetView();
                        break;
                    case "20000 - 20":
                        model.setFileName("Instructions_20000_20.xml");
                        resetView();
                        break;
                        
                    default:break;
                    
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    }
    
    public void resetView() {
        model.cancel();
        view.initValues();
        view.resetPageTable();
        view.resetProcessTable();
        view.resetFrames();
        view.update(model.getCurrentInstruction(), null);
    }
    
    /*
    * Execute instructions step by step
    */
    class OneInstructionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try{
                Instruction executedInstruction = model.performOneInstruction();
                Instruction currentInstruction = model.getCurrentInstruction();
                view.update(currentInstruction, executedInstruction);
                view.updatePageTable(model.getCurrentProcess().pageTable);
                view.updateFrames(model.getFrames());
                view.updateProcessTable(model.getAllProcesses());
                view.updateTimer(model.timer);
                
                
                if(executedInstruction != null && model.pageNrAndOffset != null){
                    view.setFysiekAdres(model.getFysAdress(model.getFrameNumber(executedInstruction), model.pageNrAndOffset[1])
                                    ,model.getFrameNumber(executedInstruction),model.pageNrAndOffset[1]);
                }
                else if(executedInstruction != null && model.pageNrAndOffset == null) {
                     view.setFysiekAdres(-1,-1,-1);
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    }
    
    
    /*
    * Execute all instructions in one go
    */
    class AllInstructionsListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try{
                model.cancel();
                Instruction executedInstruction = null;
                for(int i=0; i<model.instructionList.size(); i++) {
                    executedInstruction = model.performOneInstruction();
                }
                Instruction currentInstruction = model.getCurrentInstruction();
                view.update(currentInstruction, executedInstruction);
                view.updatePageTable(model.getCurrentProcess().pageTable);
                view.updateProcessTable(model.getAllProcesses());
                view.updateFrames(model.getFrames());
                view.updateTimer(model.timer);
                
                if(executedInstruction != null && model.pageNrAndOffset != null){
                    view.setFysiekAdres(model.getFysAdress(model.getFrameNumber(executedInstruction), model.pageNrAndOffset[1])
                                    ,model.getFrameNumber(executedInstruction), model.pageNrAndOffset[1]);
                }
                else if(executedInstruction != null && model.pageNrAndOffset == null) {
                     view.setFysiekAdres(-1,-1,-1);
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    }
    
    /*
    * Reloads the data and resets all labels
    */
    class CancelListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try{
                resetView();
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    }

}
