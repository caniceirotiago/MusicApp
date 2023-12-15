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
    private JTable centralTable;
    private JMenuItem addToAlbum;
    private int lastPositionMouseRightClick;
    private int lastPositionMouseLeftClick;
    public MusicCreatorGUI(User currentUser, GUIManager guiManager){
        super("Music Creator - " + currentUser.getUsername());
        this.currentUser = currentUser;
        this.guiManager = guiManager;
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

//---------------------------------WEST PANEL------------------------------------

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
        //colocar a jlist num JScrollPane
        JScrollPane scrollPane = new JScrollPane(albumList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //criacao do botao para criar album
        JButton newAlbumButton = new JButton("New Album");

        //criacao do painel com GRIDBAG
        JPanel westPanel = new JPanel(new GridBagLayout());
        westPanel.setPreferredSize(new Dimension(175,0));
        GridBagConstraints wpConstraints = new GridBagConstraints();// wp é west panel
        wpConstraints.gridx= 0;
        wpConstraints.gridy = 0;
        wpConstraints.weightx=1;

        wpConstraints.anchor=GridBagConstraints.CENTER;
        wpConstraints.fill = GridBagConstraints.NONE;
        westPanel.add(albumLabel,wpConstraints);

        wpConstraints.gridy++;
        wpConstraints.weighty = 0.6;
        wpConstraints.fill = GridBagConstraints.BOTH;
        westPanel.add(scrollPane, wpConstraints);

        wpConstraints.gridy++;
        wpConstraints.weighty = 0.2;
        wpConstraints.fill = GridBagConstraints.NONE;
        wpConstraints.anchor = GridBagConstraints.PAGE_START;
        westPanel.add(newAlbumButton, wpConstraints);

//------------------------------END OF WEST PANEL-----------------


//-------------------------------EAST PANEL-----------------------
//criar uma label e um botao para adicionar uma nova musica
        JLabel newMusiclbl = new JLabel();
        newMusiclbl.setText("Add new music");
        JButton addMusicButton =  new JButton("New Music");

        //criar painel east
        JPanel eastPanel = new JPanel(new GridBagLayout());
        GridBagConstraints epConstraints =  new GridBagConstraints();
        epConstraints.anchor = GridBagConstraints.CENTER;
        epConstraints.gridy = 0;
        epConstraints.fill = GridBagConstraints.BOTH;
        eastPanel.setPreferredSize(new Dimension(175,0));
        eastPanel.add(newMusiclbl,epConstraints);
        eastPanel.add(addMusicButton,epConstraints);

//-------------------------------END OF EAST PANEL-----------------------
// ------------------------------NORTH PANEL------------------------------
        //criacao do label logotipo
        int newWidth = 100;
        int newHeigth = 100;
        ImageIcon originalIcon = new ImageIcon(ImagePaths.APP_ICON);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeigth, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel logo = new JLabel(resizedIcon);

        JTextField searchArea = new JTextField("Search", 20);
        JButton searchButton =  new JButton("\uD83D\uDD0D");
        //adicionar aqui botao logout
        JButton btnLogOut =  new JButton("LogOut");
        //btnLogOut.addEventListener


        JPanel northPanel =  new JPanel(new GridBagLayout());
        GridBagConstraints npConstraints =  new GridBagConstraints(); //np é northpanel
        northPanel.setPreferredSize(new Dimension(0,100));

        npConstraints.gridx = 0;
        npConstraints.gridy = 0;
        npConstraints.weightx = 0.01;
        npConstraints.weighty = 1;
        npConstraints.fill = GridBagConstraints.NONE;
        npConstraints.anchor = GridBagConstraints.WEST;
        npConstraints.insets = new Insets(0,40,0,0);
        northPanel.add(logo, npConstraints);

        //espacador invisivel para centralizar







//------------------------------END OF NORTH PANEL
        //painel center
        //painel south (estatisticas)
        mainContainer.add(westPanel,"West");
        mainContainer.add(eastPanel,"East");
        add(mainContainer);
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
