package src.GUIClassesSwing;

import src.RockStar.User;

import javax.swing.*;
import java.awt.*;

public class MusicCreatorGUI extends JFrame {
    private User currentUsername;
    public MusicCreatorGUI(User currentUser){
        super("Music Creator - " + currentUser.getUsername());
        this.currentUsername = currentUser;
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon("images/headphone.png");
        setIconImage(imageIcon.getImage());
        setMinimumSize(new Dimension(900, 700));
    }
    public void initComponents() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());


        //painel west
        //label albuns
        JLabel albumLabel = new JLabel();
        albumLabel.setText("Albums");
    }
    //criacao da liast



}
