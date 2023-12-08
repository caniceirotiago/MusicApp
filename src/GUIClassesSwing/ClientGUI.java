package src.GUIClassesSwing;

import javax.swing.*;

public class ClientGUI extends JFrame {
    public ClientGUI(){
        super("Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750,750);
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon("images/headphone.png");
        setIconImage(imageIcon.getImage());
    }
}
