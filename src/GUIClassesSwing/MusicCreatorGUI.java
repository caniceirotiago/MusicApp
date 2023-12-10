package src.GUIClassesSwing;

import javax.swing.*;

public class MusicCreatorGUI extends JFrame {
    public MusicCreatorGUI(String username){
        super("Music Creator - " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon("images/headphone.png");
        setIconImage(imageIcon.getImage());
    }
}
