import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lander
 */
public class VMView extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public VMView() {
        initComponents();
        initValues();
        groupInstructionSetButtons();
    }
    
    /*
    * Group the radiobuttons
    */
    private void groupInstructionSetButtons( ) {
        ButtonGroup bg = new ButtonGroup( );
        bg.add(jRadioButton1);
        bg.add(jRadioButton2);
        bg.add(jRadioButton3);
        jRadioButton1.setSelected(true);
    }
    
    public void addOneInstructionListener(ActionListener oneInstructionListener){
        oneInstructionButton.addActionListener(oneInstructionListener);
    }
    
    public void addAllInstructionsListener(ActionListener allInstructionsListener){
        allInstructionsButton.addActionListener(allInstructionsListener);
    }
    
    public void addCancelListener(ActionListener cancelListener){
        cancelButton.addActionListener(cancelListener);
    }
    
    public void addRadioButton1Listener(ActionListener radioButtonListener){
        jRadioButton1.addActionListener(radioButtonListener);
    }
    
    public void addRadioButton2Listener(ActionListener radioButtonListener){
        jRadioButton2.addActionListener(radioButtonListener);
    }
    
    public void addRadioButton3Listener(ActionListener radioButtonListener){
        jRadioButton3.addActionListener(radioButtonListener);
    }
    
    /*
    * Update the Instructions
    */
    public void update(Instruction currentInstruction, Instruction previousInstruction) {
      
        // Update current Instruction
        if(currentInstruction != null) {
            this.currentIdValueLabel.setText(Integer.toString(currentInstruction.getProcessID()));
            this.currentVirtualAddressValueLabel.setText(Integer.toString(currentInstruction.getAddress()));
            this.currentOperationValueLabel.setText(currentInstruction.getOperation());
        }
        else {
            this.currentIdValueLabel.setText("");
            this.currentVirtualAddressValueLabel.setText("");
            this.currentOperationValueLabel.setText("");
        }
        //Update previous instruction
        if(previousInstruction != null) {
            this.previousIdValueLabel.setText(Integer.toString(previousInstruction.getProcessID()));
            this.previousVirtualAddressValueLabel.setText(Integer.toString(previousInstruction.getAddress()));
            this.previousOperationValueLabel.setText(previousInstruction.getOperation());
        }
    }
    
    public void updateTimer(int timer){
        this.timeLabel.setText(Integer.toString(timer));
    }
    
    public void updateProcessTable(ArrayList<Process> processList) {
        DefaultTableModel tableModel = (DefaultTableModel) processTable.getModel();
        tableModel.setRowCount(0);
        Object[] rowData = new Object[3];
        
        for(Process p: processList){
            rowData[0] = p.getProcessID();
            rowData[1] = p.getReadFromRAM();
            rowData[2] = p.getWriteToRAM();
            tableModel.addRow(rowData);         
        }
    }
    
    public void updatePageTable(List<PageTableEntry> pageTable){
        
        int page = 0;
        DefaultTableModel tableModel = (DefaultTableModel) PageTable.getModel();
        tableModel.setRowCount(0);
        
        Object[] rowData = new Object[5];
        for(PageTableEntry pte: pageTable){
            
            rowData[0] = page;
            rowData[1] = pte.getPresentBit();
            rowData[2] = pte.getModifyBit();
            rowData[3] = pte.getLastAccessTime();
            rowData[4] = pte.getFrameNumber();
            
            tableModel.addRow(rowData);        
            page++;
            
                        
        }
    }
    
    void updateFrames(Page[] frames) {
        if(frames[0] != null) {
            jLabel3.setText("PID: " + frames[0].getProcessID());
            jLabel16.setText("Page NR: " + frames[0].getPageNumber());

            jLabel18.setText("PID: " + frames[1].getProcessID());
            jLabel19.setText("Page NR: " + frames[1].getPageNumber());

            jLabel21.setText("PID: " + frames[2].getProcessID());
            jLabel22.setText("Page NR: " + frames[2].getPageNumber());

            jLabel33.setText("PID: " + frames[3].getProcessID());
            jLabel34.setText("Page NR: " + frames[3].getPageNumber());

            jLabel36.setText("PID: " + frames[4].getProcessID());
            jLabel37.setText("Page NR: " + frames[4].getPageNumber());

            jLabel24.setText("PID: " + frames[5].getProcessID());
            jLabel25.setText("Page NR: " + frames[5].getPageNumber());

            jLabel27.setText("PID: " + frames[6].getProcessID());
            jLabel28.setText("Page NR: " + frames[6].getPageNumber());

            jLabel30.setText("PID: " + frames[7].getProcessID());
            jLabel31.setText("Page NR: " + frames[7].getPageNumber());

            jLabel39.setText("PID: " + frames[8].getProcessID());
            jLabel44.setText("Page NR: " + frames[8].getPageNumber());

            jLabel61.setText("PID: " + frames[9].getProcessID());
            jLabel62.setText("Page NR: " + frames[9].getPageNumber());

            jLabel46.setText("PID: " + frames[10].getProcessID());
            jLabel47.setText("Page NR: " + frames[10].getPageNumber());

            jLabel64.setText("PID: " + frames[11].getProcessID());
            jLabel65.setText("Page NR: " + frames[11].getPageNumber());
        }
        else {
            resetFrames();
        }
    }
    
    public void resetPageTable() {
        DefaultTableModel tableModel = (DefaultTableModel) PageTable.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i=rowCount-1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }
    
    public void resetProcessTable() {
        DefaultTableModel tableModel = (DefaultTableModel) processTable.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i=rowCount-1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    public void resetFrames() {
        jLabel3.setText("empty");
        jLabel16.setText("");
        
        jLabel18.setText("empty");
        jLabel19.setText("");
        
        jLabel21.setText("empty");
        jLabel22.setText("");
        
        jLabel33.setText("empty");
        jLabel34.setText("");
        
        jLabel36.setText("empty");
        jLabel37.setText("");
        
        jLabel24.setText("empty");
        jLabel25.setText("");
        
        jLabel27.setText("empty");
        jLabel28.setText("");
        
        jLabel30.setText("empty");
        jLabel31.setText("");
        
        jLabel39.setText("empty");
        jLabel44.setText("");
       
        jLabel61.setText("empty");
        jLabel62.setText("");
        
        jLabel46.setText("empty");
        jLabel47.setText("");
       
        jLabel64.setText("empty");
        jLabel65.setText("");
    }
    
    
    public void setFysiekAdres(int fys, int frame, int offset){
        if(fys != -1 && frame != -1) {
            jLabel15.setText(Integer.toString(fys));
            jLabel53.setText(Integer.toString(frame));
            jLabel49.setText(Integer.toString(offset));
        }
        else {
            jLabel15.setText("Geen adres");
            jLabel53.setText("Geen frame nummer");
            jLabel49.setText("Geen offset");
            
        }
    }
    
    public void initValues() {
        this.timeLabel.setText("0");
        this.currentIdValueLabel.setText("");
        this.currentVirtualAddressValueLabel.setText("");
        this.currentOperationValueLabel.setText("");
        this.previousIdValueLabel.setText("");
        this.previousVirtualAddressValueLabel.setText("");
        this.previousOperationValueLabel.setText("");
        this.jLabel15.setText("");
        this.jLabel53.setText("");
        jLabel14.setText("fysiek adres: ");
        jLabel52.setText("frame nummer: ");
        jLabel48.setText("offset: ");
        jLabel49.setText("");
        resetFrames();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        allInstructionsButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        PageTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        currentIdLabel = new javax.swing.JLabel();
        currentIdValueLabel = new javax.swing.JLabel();
        currentVirtualAddressLabel = new javax.swing.JLabel();
        currentVirtualAddressValueLabel = new javax.swing.JLabel();
        currentOperationLabel = new javax.swing.JLabel();
        currentOperationValueLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        previousIdLabel = new javax.swing.JLabel();
        previousIdValueLabel = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        previousVirtualAddressLabel = new javax.swing.JLabel();
        previousVirtualAddressValueLabel = new javax.swing.JLabel();
        previousOperationLabel = new javax.swing.JLabel();
        previousOperationValueLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        timerLabel = new javax.swing.JLabel();
        Frame1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        Frame2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Frame6 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        Frame5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        Frame3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Frame4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Frame7 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        Frame8 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        Frame9 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        Frame10 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        Frame13 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        Frame14 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        oneInstructionButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        processTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        allInstructionsButton.setText("Allemaal");
        allInstructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allInstructionsButtonActionPerformed(evt);
            }
        });

        PageTable.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Page ", "present bit", "Modify bit", "Last access time", "frame number"
            }
        ));
        PageTable.setName("Page Table"); // NOI18N
        jScrollPane1.setViewportView(PageTable);

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel41.setText("Huidige instructie");

        currentIdLabel.setText("Instructie met ID: ");

        currentIdValueLabel.setText("ID");

        currentVirtualAddressLabel.setText("Virtueel adres");

        currentVirtualAddressValueLabel.setText("adres");

        currentOperationLabel.setText("Operatie");

        currentOperationValueLabel.setText("operatie");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(currentOperationLabel)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(currentIdLabel)
                            .addComponent(currentVirtualAddressLabel))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(currentVirtualAddressValueLabel)
                                    .addComponent(currentOperationValueLabel)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(currentIdValueLabel)))))
                .addContainerGap(274, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentIdLabel)
                    .addComponent(currentIdValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentVirtualAddressLabel)
                    .addComponent(currentVirtualAddressValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentOperationLabel)
                    .addComponent(currentOperationValueLabel))
                .addGap(0, 59, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        previousIdLabel.setText("Instructie met ID: ");

        previousIdValueLabel.setText("ID");

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel40.setText("Net uitgevoerde instructie");

        previousVirtualAddressLabel.setText("Virtueel adres");

        previousVirtualAddressValueLabel.setText("adres");

        previousOperationLabel.setText("Operatie");

        previousOperationValueLabel.setText("operatie");

        jLabel14.setText("jLabel14");

        jLabel15.setText("jLabel15");

        jLabel52.setText("jLabel52");

        jLabel53.setText("jLabel53");

        jLabel48.setText("jLabel48");

        jLabel49.setText("jLabel49");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(previousVirtualAddressLabel)
                    .addComponent(previousOperationLabel)
                    .addComponent(jLabel14)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(previousIdLabel)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(previousVirtualAddressValueLabel)
                                    .addComponent(previousIdValueLabel)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(previousOperationValueLabel))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel53)
                                        .addGap(57, 57, 57)
                                        .addComponent(jLabel48)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel49))))))
                    .addComponent(jLabel52))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel40)
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previousIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(previousIdValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previousVirtualAddressLabel)
                    .addComponent(previousVirtualAddressValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previousOperationLabel)
                    .addComponent(previousOperationValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53)
                    .addComponent(jLabel48)
                    .addComponent(jLabel49))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        cancelButton.setText("Reset");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        timerLabel.setText("Timer");

        jLabel2.setText("Frame 0");

        jLabel3.setText("PID");

        jLabel16.setText("pagenumber");

        javax.swing.GroupLayout Frame1Layout = new javax.swing.GroupLayout(Frame1);
        Frame1.setLayout(Frame1Layout);
        Frame1Layout.setHorizontalGroup(
            Frame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(41, 41, 41)
                .addGroup(Frame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame1Layout.setVerticalGroup(
            Frame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame1Layout.createSequentialGroup()
                .addGroup(Frame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setText("Frame 1");

        jLabel18.setText("PID");

        jLabel19.setText("pagenumber");

        javax.swing.GroupLayout Frame2Layout = new javax.swing.GroupLayout(Frame2);
        Frame2.setLayout(Frame2Layout);
        Frame2Layout.setHorizontalGroup(
            Frame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame2Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addGap(41, 41, 41)
                .addGroup(Frame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame2Layout.setVerticalGroup(
            Frame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame2Layout.createSequentialGroup()
                .addGroup(Frame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel29.setText("Frame 7");

        jLabel30.setText("PID");

        jLabel31.setText("pagenumber");

        javax.swing.GroupLayout Frame6Layout = new javax.swing.GroupLayout(Frame6);
        Frame6.setLayout(Frame6Layout);
        Frame6Layout.setHorizontalGroup(
            Frame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame6Layout.createSequentialGroup()
                .addComponent(jLabel29)
                .addGap(41, 41, 41)
                .addGroup(Frame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame6Layout.setVerticalGroup(
            Frame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame6Layout.createSequentialGroup()
                .addGroup(Frame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel26.setText("Frame 6");

        jLabel27.setText("PID");

        jLabel28.setText("pagenumber");

        javax.swing.GroupLayout Frame5Layout = new javax.swing.GroupLayout(Frame5);
        Frame5.setLayout(Frame5Layout);
        Frame5Layout.setHorizontalGroup(
            Frame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame5Layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(41, 41, 41)
                .addGroup(Frame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame5Layout.setVerticalGroup(
            Frame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame5Layout.createSequentialGroup()
                .addGroup(Frame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addContainerGap())
        );

        jLabel20.setText("Frame 2");

        jLabel21.setText("PID");

        jLabel22.setText("pagenumber");

        javax.swing.GroupLayout Frame3Layout = new javax.swing.GroupLayout(Frame3);
        Frame3.setLayout(Frame3Layout);
        Frame3Layout.setHorizontalGroup(
            Frame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame3Layout.createSequentialGroup()
                .addComponent(jLabel20)
                .addGap(41, 41, 41)
                .addGroup(Frame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame3Layout.setVerticalGroup(
            Frame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame3Layout.createSequentialGroup()
                .addGroup(Frame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel23.setText("Frame 5");

        jLabel24.setText("PID");

        jLabel25.setText("pagenumber");

        javax.swing.GroupLayout Frame4Layout = new javax.swing.GroupLayout(Frame4);
        Frame4.setLayout(Frame4Layout);
        Frame4Layout.setHorizontalGroup(
            Frame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame4Layout.createSequentialGroup()
                .addComponent(jLabel23)
                .addGap(41, 41, 41)
                .addGroup(Frame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame4Layout.setVerticalGroup(
            Frame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame4Layout.createSequentialGroup()
                .addGroup(Frame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel32.setText("Frame 3");

        jLabel33.setText("PID");

        jLabel34.setText("pagenumber");

        javax.swing.GroupLayout Frame7Layout = new javax.swing.GroupLayout(Frame7);
        Frame7.setLayout(Frame7Layout);
        Frame7Layout.setHorizontalGroup(
            Frame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame7Layout.createSequentialGroup()
                .addComponent(jLabel32)
                .addGap(41, 41, 41)
                .addGroup(Frame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame7Layout.setVerticalGroup(
            Frame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame7Layout.createSequentialGroup()
                .addGroup(Frame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(72, 72, 72))
        );

        jLabel35.setText("Frame 4");

        jLabel36.setText("PID");

        jLabel37.setText("pagenumber");

        javax.swing.GroupLayout Frame8Layout = new javax.swing.GroupLayout(Frame8);
        Frame8.setLayout(Frame8Layout);
        Frame8Layout.setHorizontalGroup(
            Frame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame8Layout.createSequentialGroup()
                .addComponent(jLabel35)
                .addGap(41, 41, 41)
                .addGroup(Frame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame8Layout.setVerticalGroup(
            Frame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame8Layout.createSequentialGroup()
                .addGroup(Frame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel38.setText("Frame 8");

        jLabel39.setText("PID");

        jLabel44.setText("pagenumber");

        javax.swing.GroupLayout Frame9Layout = new javax.swing.GroupLayout(Frame9);
        Frame9.setLayout(Frame9Layout);
        Frame9Layout.setHorizontalGroup(
            Frame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame9Layout.createSequentialGroup()
                .addComponent(jLabel38)
                .addGap(41, 41, 41)
                .addGroup(Frame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel39))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame9Layout.setVerticalGroup(
            Frame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame9Layout.createSequentialGroup()
                .addGroup(Frame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel45.setText("Frame 10");

        jLabel46.setText("PID");

        jLabel47.setText("pagenumber");

        javax.swing.GroupLayout Frame10Layout = new javax.swing.GroupLayout(Frame10);
        Frame10.setLayout(Frame10Layout);
        Frame10Layout.setHorizontalGroup(
            Frame10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame10Layout.createSequentialGroup()
                .addComponent(jLabel45)
                .addGap(41, 41, 41)
                .addGroup(Frame10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jLabel46))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame10Layout.setVerticalGroup(
            Frame10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame10Layout.createSequentialGroup()
                .addGroup(Frame10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel60.setText("Frame 9");

        jLabel61.setText("PID");

        jLabel62.setText("pagenumber");

        javax.swing.GroupLayout Frame13Layout = new javax.swing.GroupLayout(Frame13);
        Frame13.setLayout(Frame13Layout);
        Frame13Layout.setHorizontalGroup(
            Frame13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame13Layout.createSequentialGroup()
                .addComponent(jLabel60)
                .addGap(41, 41, 41)
                .addGroup(Frame13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addComponent(jLabel61))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame13Layout.setVerticalGroup(
            Frame13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame13Layout.createSequentialGroup()
                .addGroup(Frame13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel63.setText("Frame 11");

        jLabel64.setText("PID");

        jLabel65.setText("pagenumber");

        javax.swing.GroupLayout Frame14Layout = new javax.swing.GroupLayout(Frame14);
        Frame14.setLayout(Frame14Layout);
        Frame14Layout.setHorizontalGroup(
            Frame14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame14Layout.createSequentialGroup()
                .addComponent(jLabel63)
                .addGap(41, 41, 41)
                .addGroup(Frame14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65)
                    .addComponent(jLabel64))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame14Layout.setVerticalGroup(
            Frame14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame14Layout.createSequentialGroup()
                .addGroup(Frame14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel65)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        timeLabel.setText("0");

        oneInstructionButton.setText("1 per 1");
        oneInstructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneInstructionButtonActionPerformed(evt);
            }
        });

        processTable.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PID", "Inlezen", "Wegschrijven"
            }
        ));
        jScrollPane2.setViewportView(processTable);

        jLabel1.setText("Instructie set:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );

        jRadioButton1.setText("20 - 3");

        jRadioButton2.setText("20000 - 4");

        jRadioButton3.setText("20000 - 20");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(allInstructionsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(oneInstructionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton3))))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Frame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Frame14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Frame1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(Frame13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Frame14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addComponent(oneInstructionButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(allInstructionsButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton))
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jRadioButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton3)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed

    }//GEN-LAST:event_cancelButtonActionPerformed

    private void allInstructionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allInstructionsButtonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_allInstructionsButtonActionPerformed

    private void oneInstructionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneInstructionButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oneInstructionButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Frame1;
    private javax.swing.JPanel Frame10;
    private javax.swing.JPanel Frame13;
    private javax.swing.JPanel Frame14;
    private javax.swing.JPanel Frame2;
    private javax.swing.JPanel Frame3;
    private javax.swing.JPanel Frame4;
    private javax.swing.JPanel Frame5;
    private javax.swing.JPanel Frame6;
    private javax.swing.JPanel Frame7;
    private javax.swing.JPanel Frame8;
    private javax.swing.JPanel Frame9;
    private javax.swing.JTable PageTable;
    private javax.swing.JButton allInstructionsButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel currentIdLabel;
    private javax.swing.JLabel currentIdValueLabel;
    private javax.swing.JLabel currentOperationLabel;
    private javax.swing.JLabel currentOperationValueLabel;
    private javax.swing.JLabel currentVirtualAddressLabel;
    private javax.swing.JLabel currentVirtualAddressValueLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton oneInstructionButton;
    private javax.swing.JLabel previousIdLabel;
    private javax.swing.JLabel previousIdValueLabel;
    private javax.swing.JLabel previousOperationLabel;
    private javax.swing.JLabel previousOperationValueLabel;
    private javax.swing.JLabel previousVirtualAddressLabel;
    private javax.swing.JLabel previousVirtualAddressValueLabel;
    private javax.swing.JTable processTable;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel timerLabel;
    // End of variables declaration//GEN-END:variables


    

}
