package src.GUIClassesSwing;

import src.RockStar.User;

import javax.swing.*;

public class MusicCreatorGUI extends JFrame {
    private User currentUsername;
    public MusicCreatorGUI(User currentUser){
        super("Music Creator - " + currentUser.getUsername());
        this.currentUsername = currentUser;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon("images/headphone.png");
        setIconImage(imageIcon.getImage());
    }
}
