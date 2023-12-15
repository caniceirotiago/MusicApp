package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class MusicCreatorGUI extends JFrame {
    private User currentUser;
    private GUIManager guiManager;
    private MusicCollection selectedPlayList;
    private DefaultTableModel centralTableModel;
    private JPanel centerPanel;
    public MusicCreatorGUI(User currentUser){
        super("Music Creator - " + currentUser.getUsername());
        this.currentUser = currentUser;
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
        DefaultListModel<MusicCollection> listModel = new DefaultListModel<>();
        MusicCollection currentUserCollection = new Album("Owned Music", currentUser.getAllMusic(), (MusicCreator) currentUser);
        listModel.addElement(currentUserCollection);
        for (MusicCollection cl : currentUser.getAllCollections()){
            listModel.addElement(cl);
        }

        //criar jlist e definir modelo
        JList <MusicCollection> albumList =  new JList<>(listModel);
        //istener de seleção de album
        selectedPlayList = currentUserCollection;
        albumList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                selectedPlayList = albumList.getSelectedValue();
                if (selectedPlayList != null){
                  updateMusicJtableModel(selectedPlayList.getMusicList());  ;
                }
            }
        });


        //painel east
        //painel north
        //painel center
        //painel south (estatisticas)
    }
    //criacao da lista
    public void updateMusicJtableModel(ArrayList<Music> selectedPlaylist ){
        centralTableModel.setRowCount(0);
        System.out.println("numero de musicas " + selectedPlaylist);
        for (Music ms : selectedPlaylist){
            Vector<Object> line = new Vector<>();
            line.add(ms.getName());
            line.add(ms.getGenre());
            line.add(ms.getPrice());
            line.add(ms.getMusicCreator());
            centralTableModel.addRow(line);
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }




}
