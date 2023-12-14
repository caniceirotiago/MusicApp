package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;

public class LogRegFrame extends JFrame {
    GridBagConstraints constraints;
    public GridBagConstraints getConstraints() {
        return constraints;
    }
    public LogRegFrame()  {
        setSize(250,350);
        setBackground(Color.GRAY);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane();
        constraints =  new GridBagConstraints();
        constraints.gridx= GridBagConstraints.REMAINDER; //Ocupa o restante espaço na linha
        constraints.gridy = GridBagConstraints.RELATIVE; // O compunente é colucado na linha seguinte do ultimo compunente
        constraints.gridwidth = GridBagConstraints.REMAINDER;
    }
}
