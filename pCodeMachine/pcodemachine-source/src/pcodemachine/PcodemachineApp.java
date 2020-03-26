package pcodemachine;

import javax.swing.JFrame;

public class PcodemachineApp  {
    public static void main(String[] args) {
        PcodemachineView pv = new PcodemachineView();
        pv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pv.setVisible(true);
    }
}
