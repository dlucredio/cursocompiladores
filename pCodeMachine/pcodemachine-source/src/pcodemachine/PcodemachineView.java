package pcodemachine;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class PcodemachineView extends JFrame {

    public PcodemachineView() {
        initComponents();
        
        init();
    }

    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaProgram = new javax.swing.JTextArea();
        jScrollPaneStack = new javax.swing.JScrollPane();
        jTextAreaStack = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaConsole = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldInput = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableMemory = new javax.swing.JTable();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextAreaProgram.setColumns(20);
        jTextAreaProgram.setRows(5);
        jTextAreaProgram.setName("jTextAreaProgram"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaProgram);

        mainPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 140, 250));

        jScrollPaneStack.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneStack.setName("jScrollPaneStack"); // NOI18N

        jTextAreaStack.setColumns(20);
        jTextAreaStack.setRows(5);
        jTextAreaStack.setName("jTextAreaStack"); // NOI18N
        jScrollPaneStack.setViewportView(jTextAreaStack);

        mainPanel.add(jScrollPaneStack, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 120, 250));

        jLabel1.setText("Pilha"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel2.setText("Memória de programa"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        mainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel3.setText("Console"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        mainPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTextAreaConsole.setColumns(20);
        jTextAreaConsole.setRows(5);
        jTextAreaConsole.setName("jTextAreaConsole"); // NOI18N
        jScrollPane3.setViewportView(jTextAreaConsole);

        mainPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 490, 150));

        jLabel4.setText("Entrada"); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        mainPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, -1, -1));

        jTextFieldInput.setName("jTextFieldInput"); // NOI18N
        mainPanel.add(jTextFieldInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 355, 100, -1));

        jButton1.setText("Reiniciar"); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        mainPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 100, -1));

        jButton2.setText("Passo"); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        mainPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 100, -1));

        jLabel5.setText("Memória principal"); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        mainPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        jButton3.setText("Limpar"); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        mainPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 355, 100, -1));

        jButton4.setText("Rodar"); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        mainPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 100, -1));

        jScrollPane4.setColumnHeader(null);
        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTableMemory.setModel(new javax.swing.table.DefaultTableModel());
        jTableMemory.setName("jTableMemory"); // NOI18N
        jTableMemory.setRowHeight(26);
        jTableMemory.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTableMemory);

        mainPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 210, 250));
        
        getContentPane().add(mainPanel);
        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        resetProgram();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        step();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        clearConsole();
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        execute();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPaneStack;
    private javax.swing.JTable jTableMemory;
    private javax.swing.JTextArea jTextAreaConsole;
    private javax.swing.JTextArea jTextAreaProgram;
    private javax.swing.JTextArea jTextAreaStack;
    private javax.swing.JTextField jTextFieldInput;
    private javax.swing.JPanel mainPanel;
//    private javax.swing.JMenuBar menuBar;
//    private javax.swing.JProgressBar progressBar;
//    private javax.swing.JLabel statusAnimationLabel;
//    private javax.swing.JLabel statusMessageLabel;
//    private javax.swing.JPanel statusPanel;

    private PcodeInterpreter interpreter;

    private void init() {
        jTextAreaProgram.setSelectedTextColor(Color.white);
        jTextAreaProgram.setSelectionColor(Color.lightGray);
        interpreter = new PcodeInterpreter();
    }

    private void loadProgram() {
        resetProgram();
    }

    private void resetProgram() {
        interpreter.reset(jTextAreaProgram.getText());
        update();
    }

    private void execute() {
        interpreter.setInput(jTextFieldInput.getText());
        jTextFieldInput.setText("");
        while(!interpreter.isHalted()) {
            String output = interpreter.step();
            if(output != null) {
                writeToConsole("Saída: "+output);
            }
            if(interpreter.isWaitingForInput()) break;
        }
        update();
    }

    private void step() {
        String currentInstruction = jTextAreaProgram.getSelectedText();
        interpreter.setInput(jTextFieldInput.getText());
        jTextFieldInput.setText("");
        if (currentInstruction != null) {
            currentInstruction = currentInstruction.trim();
            String output = interpreter.step();
            if(output != null) {
                writeToConsole("Saída: "+output);
            }
            update();
        }
    }

    private void update() {
        updateLineHighlight();
        updateStack();
        updateMemory();

    }

    private void updateLineHighlight() {
        int selectionStart = 0;
        String program = jTextAreaProgram.getText();
        int selectionEnd = program.indexOf('\n');
        for (int i = 0; i < interpreter.getCurrentInstruction(); i++) {
            selectionStart = selectionEnd;
            selectionEnd = program.indexOf('\n', selectionStart + 1);
            if (selectionEnd == -1) {
                selectionEnd = program.length();
            }
        }
        jTextAreaProgram.setCaretPosition(selectionStart);
        jTextAreaProgram.moveCaretPosition(selectionEnd);
        jTextAreaProgram.getCaret().setSelectionVisible(true);
    }

    private void updateStack() {
        jTextAreaStack.setText(interpreter.getStackDescription());
        jTextAreaStack.setCaretPosition(0);
    }

    private void updateMemory() {
        String[] memory = interpreter.getMemoryDescription();
        DefaultTableModel dtm = (DefaultTableModel)jTableMemory.getModel();
        String[][] memoryDataVector = new String[memory.length][2];
        for(int i=0;i<memory.length;i++) {
            memoryDataVector[i][0]=Integer.toString(i);
            memoryDataVector[i][1]=memory[i];
        }
        dtm.setDataVector(memoryDataVector, new String[]{"End.","Conteúdo"});
    }

    private void writeToConsole(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        jTextAreaConsole.append(sdf.format(new Date()) + ":" + str + "\n");
    }

    private void clearConsole() {
        jTextAreaConsole.setText("");
    }
}
