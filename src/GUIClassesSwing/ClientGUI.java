package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ClientGUI extends JFrame {
    private User currentUser;
    private GUIManager guiManager;
    private DefaultTableModel centralTableModel;
    private JPanel centerPanel;
    private MusicCollection selectedPlaylist;
    private JTable centralTable;
    private JMenuItem addToPlaylist;
    private int lastPositionMouseRightClickX;
    private int lastPositionMouseRightClickY;
    public ClientGUI(User currentUser, GUIManager guiManager){
        super("Client - " + currentUser.getUsername());
        this.currentUser = currentUser;
        this.guiManager = guiManager;
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon(ImagePaths.APP_ICON);
        setIconImage(imageIcon.getImage());
        setMinimumSize(new Dimension(900, 700));
    }
    public void initComponents(){
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        //---------------------ADDING POPUPMENUS AND SUBMENUS-----------------------------


        //Central PUM when allMusic selected
        JPopupMenu centralTablePUM = new JPopupMenu();
        addToPlaylist = new JMenuItem("Add to Playlit");
        JMenuItem evaluateMusic = new JMenuItem("Evaluate Music");
        centralTablePUM.add(addToPlaylist);
        centralTablePUM.add(evaluateMusic);
        addToPlaylist.addActionListener(e -> addMusicToPlaylistOnClick());

        //Central PUM when a playlist is selected
        JPopupMenu centralTablePUM2 = new JPopupMenu();
        JMenuItem removeFromPlaylist = new JMenuItem("Remove from Playlit");
        JMenuItem evaluateMusic2 = new JMenuItem("Evaluate Music");
        centralTablePUM2.add(removeFromPlaylist);
        centralTablePUM2.add(evaluateMusic2);
        removeFromPlaylist.addActionListener(e -> onRemoveFromPlaylistClick());



        //---------------------PAINEL WEST---------------------------------


        //Label a dizer Playlist
        JLabel playlistLabel =  new JLabel();
        playlistLabel.setText("Playlist");

        //Criação de lista
        //Nas seguintes linhas de código é criado o modelo da lista
        //Em primeiro lugar é adicionada a lista de musicas das quais o cliente é proprietario
        //Para este efeito é criado um novo objeto playlist temporario pois esta lista está guardada como arraylist
        //no User, como a MusicCollection é uma classe abstrata temos de criar uma plylist aqui e será um album no caso do
        //Music creator. Depois disso dão adicionadas as restantes playlists do utilizador com o ciclo

        DefaultListModel<MusicCollection> listModel = new DefaultListModel<>();
        MusicCollection currentUserCollection = new Playlist("Owned Music",(Client) currentUser,currentUser.getAllMusic());
        listModel.addElement(currentUserCollection);
        for(MusicCollection cl : currentUser.getAllCollections()){
            listModel.addElement(cl);
        }

        // Cria a JList e define o modelo
        JList<MusicCollection> playlistList = new JList<>(listModel);
        //Listner de seleção de playlist
        selectedPlaylist = currentUserCollection; //Define a playlist selecionada na tabela como as musicas totais do user em primeiro lugar
        playlistList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                selectedPlaylist = playlistList.getSelectedValue();
                if(selectedPlaylist != null){
                    updateMusicJTableModel(selectedPlaylist.getMusicList());
                }
            }
        });
        // Coloca a JList em um JScrollPane
        JScrollPane scrollPane = new JScrollPane(playlistList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Criação de Botão para Criar Playlist
        JButton newPlaylistbtn = new JButton("New PLaylist");

        //Criação de painel com GRIDBAGLAYOUT
        JPanel westPanel = new JPanel(new GridBagLayout());
        westPanel.setPreferredSize(new Dimension(175, 0));
        GridBagConstraints cw = new GridBagConstraints();
        cw.gridx = 0;
        cw.gridy = 0;
        cw.weightx = 1;

        cw.anchor = GridBagConstraints.CENTER; // Centraliza horizontalmente
        cw.fill = GridBagConstraints.NONE;
        westPanel.add(playlistLabel, cw);

        cw.gridy++;
        cw.weighty = 0.6; // Ajuste para a scrollPane
        cw.fill = GridBagConstraints.BOTH;
        westPanel.add(scrollPane, cw);

        cw.gridy++;
        cw.weighty = 0.2; // Ajuste para o botão
        cw.fill = GridBagConstraints.NONE;
        cw.anchor = GridBagConstraints.PAGE_START;
        westPanel.add(newPlaylistbtn, cw);




        //----------------------------------PAINEL EAST--------------------------------



        //Label a dizer o Balance
        JLabel balancelbl =  new JLabel();
        balancelbl.setText("Balance");

        //Label a dizer o Basket
        JLabel Basketlbl =  new JLabel();
        Basketlbl.setText("Basket");

        //Criação de lista de compras
        DefaultListModel<String> listModel1 = new DefaultListModel<>();
        listModel1.addElement("Item 1");
        listModel1.addElement("Item 2");
        listModel1.addElement("Item 3");
        listModel1.addElement("Item 3");


        // Cria a JList e define o modelo
        JList<String> basketList = new JList<>(listModel1);

        // Coloca a JList em um JScrollPane
        JScrollPane scrollPane2 = new JScrollPane(basketList);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Label a dizer o Total
        JLabel totallbl =  new JLabel();
        totallbl.setText("Total");

        //Criação de Botão para Criar Purchase
        JButton purchasebtn = new JButton("Purchase");

        //Criação de painel com GRIDBAGLAYOUT
        JPanel eastPanel = new JPanel(new GridBagLayout());
        eastPanel.setPreferredSize(new Dimension(175, 0));
        GridBagConstraints ce = new GridBagConstraints();
        ce.gridx = 0;
        ce.gridy = 0;
        ce.weightx = 1;
        ce.weighty = 0.1; // Ajuste este valor para controlar a altura do logo
        ce.fill = GridBagConstraints.NONE;
        ce.anchor = GridBagConstraints.CENTER;
        eastPanel.add(balancelbl, ce);

        ce.gridy++;
        ce.weighty = 0.1;
        eastPanel.add(Basketlbl, ce);

        ce.gridy++;
        ce.weighty = 0.6;
        ce.fill = GridBagConstraints.BOTH;
        eastPanel.add(scrollPane2, ce);

        ce.gridy++;
        ce.weighty = 0.05;
        ce.anchor = GridBagConstraints.PAGE_START;
        ce.fill = GridBagConstraints.NONE;
        eastPanel.add(totallbl, ce);

        ce.gridy++;
        ce.weighty = 0.05;
        ce.anchor = GridBagConstraints.NORTH;
        eastPanel.add(purchasebtn, ce);



        //----------------------------------PAINEL NORTH--------------------------------

        //Criação de Label logotipo
        int newWidth = 100;
        int newHeight = 100;
        ImageIcon originalIcon = new ImageIcon(ImagePaths.APP_ICON);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel logo =  new JLabel(resizedIcon);

        JTextField searchTextField = new JTextField("Search",20);
        JButton searchbtn = new JButton("\uD83D\uDD0D");

        //Criação de Botão para Criar logout
        JButton logOutbtn = new JButton("Logout");
        logOutbtn.addActionListener(e -> onlogOutbtnClick());


        JPanel northPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cn = new GridBagConstraints();
        northPanel.setPreferredSize(new Dimension(0, 100));

        cn.gridx = 0;
        cn.gridy = 0;
        cn.weightx = 0.01;
        cn.weighty = 1;
        cn.fill = GridBagConstraints.NONE;
        cn.anchor = GridBagConstraints.WEST;
        cn.insets = new Insets(0, 40, 0, 0); // 10 pixels de margem da borda esquerda
        northPanel.add(logo, cn);

        // Espaçador invisível para centralizar
        cn.gridx++;
        cn.weightx = 0.25; // Ajuste conforme necessário para centralizar
        cn.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(Box.createHorizontalStrut(0), cn);

        // Configurações para o campo de texto de pesquisa
        cn.gridx++;
        cn.weightx = 0; // Não expandir
        cn.fill = GridBagConstraints.NONE;
        northPanel.add(searchTextField, cn);

        // Configurações para o botão de pesquisa
        cn.gridx++;
        cn.weightx = 0;
        cn.fill = GridBagConstraints.NONE;
        northPanel.add(searchbtn, cn);

        // Outro espaçador invisível
        cn.gridx++;
        cn.weightx = 0.25; // Ajuste conforme necessário
        cn.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(Box.createHorizontalStrut(0), cn);

        // Configurações para o botão de logout
        cn.gridx++;
        cn.weightx = 0.01; // Manter fixo à direita
        cn.anchor = GridBagConstraints.EAST;
        cn.insets = new Insets(0, 0, 0, 40); // 10 pixels de margem da borda direita
        northPanel.add(logOutbtn, cn);


        //----------------------------------PAINEL CENTER--------------------------------

        String[] columnNames = {"Title", "Album", "Clasification"};
        centralTableModel = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Retorna false para todas as células, tornando-as não editáveis
                return false;
            }
        };


        for(Music ms : currentUser.getAllMusic()){
            Vector <Object> line = new Vector<>();
            line.add(ms.getName());
            line.add(ms.getAssociatedAlbum());
            line.add(ms.getClassification());
            centralTableModel.addRow(line);
        }
        centralTable = new JTable(centralTableModel);
        centralTable.getTableHeader().setReorderingAllowed(false);

        //Reparar que este ,ouse listener se comporta de maneira diferente consuante a playlist selecionada
        centralTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Primeiro guarda a posição do click para depois localizar o submenu
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = centralTable.rowAtPoint(e.getPoint());
                    if(row>=0 && row < centralTable.getRowCount()){
                        centralTable.setRowSelectionInterval(row,row);
                        if(selectedPlaylist.equals(currentUserCollection)){
                            centralTablePUM.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                        } else {
                            centralTablePUM2.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane3 = new JScrollPane(centralTable);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane3,"Center");


        //Adding all panels
        mainContainer.add(northPanel,"North");
        mainContainer.add(centerPanel,"Center");
        mainContainer.add(eastPanel,"East");
        mainContainer.add(westPanel,"West");

        add(mainContainer);
    }
    public void updateMusicJTableModel(ArrayList<Music> selectedPlaylist){
        centralTableModel.setRowCount(0);
        System.out.println("Número de músicas: " + selectedPlaylist.size());
        for(Music ms : selectedPlaylist){
            Vector <Object> line = new Vector<>();
            line.add(ms.getName());
            line.add(ms.getAssociatedAlbum());
            line.add(ms.getClassification());
            centralTableModel.addRow(line);
        }
        centerPanel.revalidate();
        centerPanel.repaint();
        //Aparentemente parece que não precisa de repaint e revalidate mas optei por deixar para já
    };
    public void onlogOutbtnClick(){
        guiManager.logoutClient();
    }
    public Music getSelectedMusic(){
        int row = centralTable.getSelectedRow();
        if(row != -1){
            ArrayList<Music> musics = selectedPlaylist.getMusicList();
            return musics.get(row);
        }
        return null;
    }
    public void onRemoveFromPlaylistClick(){
        Music selectedMusic = getSelectedMusic();
        if(selectedMusic!= null){
            currentUser.removeMusicFromCollection(selectedMusic,selectedPlaylist);
            updateMusicJTableModel(selectedPlaylist.getMusicList());
            centerPanel.revalidate();
            centerPanel.repaint();
            System.out.println("Music eliminated from playlist");
        }
    }
    public void addMusicToPlaylistOnClick(){ //Este metodo tambem cria um submenu para o efeito
        JPopupMenu playlistMenu =new JPopupMenu();

        int nOfPlaylists = currentUser.getAllCollections().size();

        if(nOfPlaylists == 0){
            JMenuItem emptyList = new JMenuItem("No Playlists to Show");
            playlistMenu.add(emptyList);
        } else{
            for(MusicCollection pl: currentUser.getAllCollections()){
                JMenuItem playlistItem  = new JMenuItem(pl.getName());
                playlistItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Music selectedMusic = getSelectedMusic();
                        if(selectedMusic != null){
                            currentUser.addMusicToCollection(selectedMusic,pl);
                        }
                    }
                });
                playlistMenu.add(playlistItem);
            }
        }
        playlistMenu.show(centralTable,lastPositionMouseRightClickX,lastPositionMouseRightClickY);
    }
}