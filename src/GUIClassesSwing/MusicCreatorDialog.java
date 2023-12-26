package src.GUIClassesSwing;

import src.RockStar.Album;

import javax.swing.*;
import java.awt.*;

public class MusicCreatorDialog extends JDialog {
    //costrutor tem de ser chamado quando se clica no botao de criar album
    //tem de receber um parametro do tipo int que corresponde a um valor que tem os menus
    private int dialogTyoe;

    public MusicCreatorDialog (){};


    public void newAlbumDialog(){
        JDialog albumDialog = new JDialog(this, "Create a new album", true);
        JLabel albumTitle = new JLabel("Album title");
        JTextField albumTitletext = new JTextField(20);
        JPanel newAlbumPanel = new JPanel((new GridBagLayout()));
        GridBagConstraints newAlbumConstraints = new GridBagConstraints();
        newAlbumPanel.setSize(150,150);

    }


}
