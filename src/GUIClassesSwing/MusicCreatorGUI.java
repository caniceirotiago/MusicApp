package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
    private int lastPositionMouseRightClickX;
    private int lastPositionMouseRightClickY;
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

//---------------------------------menus and popup menus
        JPopupMenu centralTablePUM =  new JPopupMenu();
        addToAlbum = new JMenuItem("Add to album");
        //ver o que colocar aqui no caso do criador de musica
        JMenuItem evaluateMusic = new JMenuItem("Evaluate music");
        centralTablePUM.add(addToAlbum);
        centralTablePUM.add(evaluateMusic);
        addToAlbum.addActionListener(e-> addMusicToAlbumOnClick());


//-------------------------------------------------------

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

        //aqui falta adicionar as funcionalidades das musicas

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
        btnLogOut.addActionListener(e -> {
            try {
                onbtnLogOutClick();
            } catch (IOException | ClassNotFoundException ex){
                throw new RuntimeException(ex);
            }
        });


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
        npConstraints.gridx++;
        npConstraints.weighty = 0.25;
        npConstraints.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(Box.createHorizontalStrut(0), npConstraints);

        //campo de pesquisa
        npConstraints.gridx++;
        npConstraints.weightx = 0;
        npConstraints.fill = GridBagConstraints.NONE;
        northPanel.add(searchArea, npConstraints);
        //botao de pesquisa
        npConstraints.gridx++;
        npConstraints.weightx = 0;
        npConstraints.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(searchButton,npConstraints);

        //Espacador invisivel
        npConstraints.gridx++;
        npConstraints.weighty = 0.25;
        npConstraints.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(Box.createHorizontalStrut(0), npConstraints);

        //configuracao botao logout
        npConstraints.gridx ++;
        npConstraints.weightx = 0.01;
        npConstraints.anchor =GridBagConstraints.EAST;
        npConstraints.insets = new Insets(0,0,0,40);
        northPanel.add(btnLogOut, npConstraints);
//------------------------------END OF NORTH PANEL

//------------------------------center panel
        String [] columnNames = {"Title", "Author", "Genre", "Price"};
        centralTableModel =  new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (Music ms : currentUser.getAllMusic()){
            Vector<Object> line =  new Vector<>();
            line.add(ms.getName());
            line.add(ms.getMusicCreator());
            line.add(ms.getGenre());
            line.add(ms.getPrice());
            centralTableModel.addRow(line);
        }

        centralTable =  new JTable(centralTableModel);
        centralTable.getTableHeader().setReorderingAllowed(false);
        centralTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //guarda a posicao do click para abrir o submenu
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if (SwingUtilities.isRightMouseButton(e)){
                    int row = centralTable.rowAtPoint(e.getPoint());
                    if (row > 0 && row <centralTable.getRowCount()){
                        centralTable.setRowSelectionInterval(row,row);
                        if (selectedPlayList.equals(currentUserCollection)){
                            centralTablePUM.show(e.getComponent(), lastPositionMouseRightClickX, lastPositionMouseRightClickY);
                        } else {

                        }

                    }
                }
            }
        });
//------------------------------end of center panel
//------------------------------south panel
//------------------------------end of south panel
        //painel center
        //painel south (estatisticas)
        mainContainer.add(westPanel,"West");
        mainContainer.add(eastPanel,"East");
        mainContainer.add(northPanel, "North");
        mainContainer.add(centerPanel,"Center");
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

    }
    public void onbtnLogOutClick() throws IOException, ClassNotFoundException {
        guiManager.logoutMCreator();
    }
    public Music getSelectedMusic(){
        int row = centralTable.getSelectedRow();
        if (row != -1){
            ArrayList<Music> musics = selectedPlayList.getMusicList();
            return musics.get(row);
        }
        return null;
    }
    public void addMusicToAlbumOnClick(){
        JPopupMenu albumMenu =  new JPopupMenu();
        int noAlbums = currentUser.getAllCollections().size();
        if (noAlbums == 0) {
            JMenuItem emptyAlbum = new JMenuItem("No albums to show");
            albumMenu.add(emptyAlbum);
        }else {
            for (MusicCollection al : currentUser.getAllCollections()){
                JMenuItem albumItem = new JMenuItem(al.getName());
                albumItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Music selectedMusic = getSelectedMusic();
                        if (selectedMusic != null){
                            currentUser.addMusicToCollection(selectedMusic, al);
                        }
                    }
                });
                albumMenu.add(albumItem);
            }
            albumMenu.show(centralTable,lastPositionMouseRightClickX,lastPositionMouseRightClickY);

        }
    }

}
