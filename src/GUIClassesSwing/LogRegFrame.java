package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;

public class LogRegFrame extends JFrame {
    GridBagConstraints constraints;
    public GridBagConstraints getConstraints() {
        return constraints;
    }
    public LogRegFrame()  {
        setSize(450,450);
        setBackground(Color.GRAY);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane();
        constraints =  new GridBagConstraints();
        constraints.gridx= GridBagConstraints.REMAINDER;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
    }
}
