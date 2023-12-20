package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ClientGUI extends JFrame {
    private User currentUser;
    private GUIManager guiManager;
    private DefaultTableModel centralTableModel;
    private DefaultTableModel searchMusicTableModel;
    private DefaultTableModel searchCollectionTableModel;
    private DefaultListModel<MusicCollection> listModelWest;
    private DefaultListModel<Music> listModelEast;
    private JPanel centerPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel searchTablesPanel;
    private MusicCollection selectedPlaylist;
    private JTable centralTable;
    private JTable searchMusicTable;
    private JTable searchCollectionTable;
    private JList<MusicCollection> playlistListWest;
    private JList<Music> basketList;
    private JMenuItem addToPlaylistMenu;
    private JMenuItem evaluateMusicMenu;
    private int lastPositionMouseRightClickX;
    private int lastPositionMouseRightClickY;
    private MusicCollection currentUserCollection;
    private JLabel balancelbl;
    private JLabel totallbl;
    private CardLayout centralCardLayout;
    private CardLayout searchCardLayout;
    private Search search;
    private JTextField searchTextField;
    private JComboBox comboSearchBox;

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
        setMinimumSize(new Dimension(1200, 700));
    }
    public void initComponents(){
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        //---------------------ADDING POPUPMENUS AND SUBMENUS---------------------

        //<----Unfold
        //Central PopMenu when a music on allMusic is selected
        JPopupMenu centralTablePopMenu = new JPopupMenu();
        addToPlaylistMenu = new JMenuItem("Add to Playlit");
        evaluateMusicMenu = new JMenuItem("Evaluate Music");
        centralTablePopMenu.add(addToPlaylistMenu);
        centralTablePopMenu.add(evaluateMusicMenu);
        addToPlaylistMenu.addActionListener(e -> addMusicToPlaylistOnClick());
        evaluateMusicMenu.addActionListener(e -> addEvaluationClick());

        //Central PopMenu when a music on a playlist is selected
        JPopupMenu centralTablePUM2 = new JPopupMenu();
        JMenuItem removeFromPlaylist = new JMenuItem("Remove from Playlit");
        JMenuItem evaluateMusic2 = new JMenuItem("Evaluate Music");
        centralTablePUM2.add(removeFromPlaylist);
        centralTablePUM2.add(evaluateMusic2);
        removeFromPlaylist.addActionListener(e -> onRemoveFromPlaylistClick());
        evaluateMusic2.addActionListener(e -> addEvaluationClick());

        //West PopMenu when a playlist is selected
        JPopupMenu westListPopMenu = new JPopupMenu();
        JMenuItem deletePlaylist = new JMenuItem("Delete Playlist");
        JMenuItem changeVisibility = new JMenuItem("Change Visibility");
        westListPopMenu.add(deletePlaylist);
        westListPopMenu.add(changeVisibility);
        deletePlaylist.addActionListener(e -> onDeletePlaylistClick());
        changeVisibility.addActionListener(e -> onVisibilityClick());

        //Central PopMenu for searchedMusic
        JPopupMenu centralTableSearchedMusicPuM = new JPopupMenu();
        JMenuItem acquireMusic = new JMenuItem("Acquire Music");
        JMenuItem seePriceHistoric = new JMenuItem("See Historic Prices");
        centralTableSearchedMusicPuM.add(acquireMusic);
        centralTableSearchedMusicPuM.add(seePriceHistoric);
        acquireMusic.addActionListener(e -> onAcquireMusicClick());
        seePriceHistoric.addActionListener(e -> onPriceHistoricClick());

        //East PopMenu Shopping Basket
        JPopupMenu basketMusicPuM = new JPopupMenu();
        JMenuItem removeFromBasket = new JMenuItem("Remove From Basket");
        JMenuItem seePriceHistoric2 = new JMenuItem("See Historic Prices");
        JMenuItem cleanBasket = new JMenuItem("Clean Basket");
        basketMusicPuM.add(removeFromBasket);
        basketMusicPuM.add(seePriceHistoric2);
        basketMusicPuM.add(cleanBasket);
        removeFromBasket.addActionListener(e -> onRemoveFromBasketClick());
        seePriceHistoric2.addActionListener(e -> onPriceHistoricBascketClick());
        cleanBasket.addActionListener(e -> onCleanBasketClick());


        //-----------------------------PANEL WEST--------------------------------

        //<----Unfold
        westPanel = new JPanel(new GridBagLayout());
        //Label a dizer Playlist
        JLabel playlistLabel =  new JLabel();
        playlistLabel.setText("Playlist");

        //Criação de lista
        //Nas seguintes linhas de código é criado o modelo da lista
        //Em primeiro lugar é adicionada a lista de musicas das quais o cliente é proprietario
        //Para este efeito é criado um novo objeto playlist temporario pois esta lista está guardada como arraylist
        //no User, como a MusicCollection é uma classe abstrata temos de criar uma plylist aqui e será um album no caso do
        //Music creator. Depois disso dão adicionadas as restantes playlists do utilizador com o ciclo

        //Cria modelo lista
        listModelWest = new DefaultListModel<>();
        updateMusicJListModel(currentUser.getAllCollections());
        if(currentUserCollection == null) currentUserCollection = new Playlist();  // em caso de novo utilizador
        selectedPlaylist = currentUserCollection; //Define a playlist selecionada na tabela como as musicas totais do user em primeiro lugar

        // Cria a JList e define o modelo
        playlistListWest = new JList<>(listModelWest);
        //Listner de seleção de playlist

        playlistListWest.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){ //Ajusta tendo em conta que é um scrollabel panel
                selectedPlaylist = playlistListWest.getSelectedValue();
                if(selectedPlaylist != null){
                    updateMusicJTableModel(selectedPlaylist.getMusicList());
                    centralCardLayout.show(centerPanel,"1");
                }
            }
        });
        playlistListWest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Primeiro guarda a posição do click para depois localizar o submenu
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = playlistListWest.locationToIndex(e.getPoint());
                    Rectangle rectangle = playlistListWest.getCellBounds(row,row); //É necessário definir o retangulo do item da lista
                    if(rectangle != null && rectangle.contains(e.getPoint()) &&
                            row >= 1 && row < listModelWest.getSize()){ //mais 1 de forma a ignorar o primeiro elemento que não +e uma playlist tipica
                        playlistListWest.setSelectedIndex(row);
                        westListPopMenu.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                    }
                }
            }
        });
        // Coloca a JList em um JScrollPane
        JScrollPane scrollPane = new JScrollPane(playlistListWest);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Criação de Botão para Criar Playlist
        JButton newPlaylistbtn = new JButton("New PLaylist");
        newPlaylistbtn.addActionListener(e -> onNewPlaylistbtnClick());

        //Criação de painel com GRIDBAGLAYOUT

        westPanel.setPreferredSize(new Dimension(175, 0));
        GridBagConstraints cw = new GridBagConstraints();

        cw.gridx = 0;
        cw.gridy = 0;
        cw.weightx = 0.2;
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
        cw.anchor = GridBagConstraints.NORTH;
        westPanel.add(newPlaylistbtn, cw);

        //-----------------------------PANEL EAST--------------------------------

        //<----Unfold
        eastPanel = new JPanel(new GridBagLayout());
        //Label a dizer o Balance
        balancelbl =  new JLabel();
        balancelbl.setText("Balance " +
                "\n " + ((Client)currentUser).getBalance() + "€");

        //Criação botão addBalance
        JButton addBalancebtn = new JButton("Add Money");
        addBalancebtn.addActionListener(e -> onAddBalancebtnClick());

        //Label a dizer o Basket
        JLabel Basketlbl =  new JLabel();
        Basketlbl.setText("Basket");

        //Criação de lista de compras
        listModelEast = new DefaultListModel<>();
        updateBascketJListModel();

        // Cria a JList e define o modelo
        basketList = new JList<>(listModelEast);

        basketList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Primeiro guarda a posição do click para depois localizar o submenu
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = basketList.locationToIndex(e.getPoint());
                    Rectangle rectangle = basketList.getCellBounds(row,row); //É necessário definir o retangulo do item da lista
                    if(rectangle != null && rectangle.contains(e.getPoint()) &&
                            row >= 0 && row < listModelEast.getSize()){
                        basketList.setSelectedIndex(row);
                        basketMusicPuM.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                    }
                }
            }
        });

        // Coloca a JList num JScrollPane
        JScrollPane scrollPane2 = new JScrollPane(basketList);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Label a dizer o Total
        totallbl =  new JLabel();
        double totalPrice = updateTotalBascketPrice();
        totallbl.setText("Total " + totalPrice + "€");

        //Criação de Botão para Criar Purchase
        JButton purchasebtn = new JButton("Purchase");
        purchasebtn.addActionListener(e -> onPurchasebtnClick());

        //Criação de painel com GRIDBAGLAYOUT

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
        ce.weighty = 0.05;
        eastPanel.add(addBalancebtn,ce);

        ce.gridy++;
        ce.weighty = 0.05;
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

        //-----------------------------PANEL NORTH--------------------------------

        //<----Unfold
        //Criação de Label logotipo
        int newWidth = 100;
        int newHeight = 100;
        ImageIcon originalIcon = new ImageIcon(ImagePaths.APP_ICON);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel logo =  new JLabel(resizedIcon);

        searchTextField = new JTextField("",20);
        JButton searchbtn = new JButton("\uD83D\uDD0D");
        searchbtn.addActionListener(e -> newSearch());

        //Criação de Botão para Criar logout
        JButton logOutbtn = new JButton("Logout");
        logOutbtn.addActionListener(e -> {
            try {
                onlogOutbtnClick();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });


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

        //-----------------------------PANEL CENTER--------------------------------

        //<----Unfold
        String[] columnNamesMusic = {"Title", "Artist", "Album", "Classification"};
        centralTableModel = new DefaultTableModel(columnNamesMusic,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Retorna false para todas as células, tornando-as não editáveis
                return false;
            }
            public Class<?> getColumnClass(int column) {
                // de forma a garantir que no momento da ordenmação é visto como um double
                if(column == 3){
                    return Double.class;
                }
                else {
                    return String.class;
                }
            }
        };
        for(Music ms : currentUser.getAllMusic()){
            Vector <Object> line = new Vector<>();
            line.add(ms.getName());
            line.add(ms.getArtistNameFromMusic());
            line.add(ms.getAssociatedAlbum());
            line.add(ms.getClassification());
            centralTableModel.addRow(line);
        }
        centralTable = new JTable(centralTableModel);
        centralTable.getTableHeader().setReorderingAllowed(true);
        centralTable.setAutoCreateRowSorter(true); //Essencial para o ordenamento

        //Reparar que este mouse listener se comporta de maneira diferente consoante a playlist selecionada
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
                        if(selectedPlaylist == null) selectedPlaylist = currentUserCollection;
                        if(selectedPlaylist.equals(currentUserCollection)){
                            centralTablePopMenu.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
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



        //Criar paineis de pesquisa ---Musica
        if(search == null) search = new Search();
        searchMusicTableModel = new DefaultTableModel(columnNamesMusic,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Retorna false para todas as células, tornando-as não editáveis
                return false;
            }
            public Class<?> getColumnClass(int column) {
                // de forma a garantir que no momento da ordenmação é visto como um double
                if(column == 3){
                    return Double.class;
                }
                else {
                    return String.class;
                }
            }
        };
        updateSearchMusicTable(search.getFoundMusics());
        searchMusicTable = new JTable(searchMusicTableModel);
        searchMusicTable.getTableHeader().setReorderingAllowed(true);
        searchMusicTable.setAutoCreateRowSorter(true); //Essencial para o ordenamento

        searchMusicTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Primeiro guarda a posição do click para depois localizar o submenu
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = searchMusicTable.rowAtPoint(e.getPoint());
                    if(row>=0 && row < searchMusicTable.getRowCount()){
                        searchMusicTable.setRowSelectionInterval(row,row);
                        centralTableSearchedMusicPuM.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                    }
                }
            }
        });


        //Criação painel pesquisa coleções-----------------------------------------
        String[] columnNamesCollection = {"Collection", "Type", "Creator"};
        searchCollectionTableModel = new DefaultTableModel(columnNamesCollection,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Retorna false para todas as células, tornando-as não editáveis
                return false;
            }
        };
        updateSearchCollectionTable();
        searchCollectionTable = new JTable(searchCollectionTableModel);
        searchCollectionTable.getTableHeader().setReorderingAllowed(true);
        searchCollectionTable.setAutoCreateRowSorter(true); //Essencial para o ordenamento

        //---------------------------------------------------------------

        JButton backToMainbtn = new JButton("Back");
        String[] filterOptions = {"Music", "Music By Artist","Collections"}; // , "Artist",  eventualmente adicionar estes dois
        comboSearchBox = new JComboBox<>(filterOptions);
        comboSearchBox.addActionListener(e -> onSearchComboBoxClick());
        JPanel searchbtnPanel = new JPanel(new FlowLayout());

        searchbtnPanel.add(backToMainbtn);
        searchbtnPanel.add(comboSearchBox);

        JScrollPane scrollPane4 = new JScrollPane(searchMusicTable);
        scrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JScrollPane scrollPane5 = new JScrollPane(searchCollectionTable);
        scrollPane5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Formação de cardlayout interior
        searchCardLayout  = new CardLayout();
        searchTablesPanel = new JPanel(searchCardLayout);
        searchTablesPanel.add(scrollPane4,"1");
        searchTablesPanel.add(scrollPane5,"2");
        searchCardLayout.show(searchTablesPanel,"1");


        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchbtnPanel,"North");
        searchPanel.add(searchTablesPanel,"Center");


        //Formação de cardLayout
        centralCardLayout = new CardLayout();
        centerPanel = new JPanel(centralCardLayout);

        centerPanel.add(scrollPane3,"1");
        centerPanel.add(searchPanel,"2");
        centralCardLayout.show(centerPanel, "1");

        backToMainbtn.addActionListener(e -> centralCardLayout.show(centerPanel, "1"));
        //--------------------------------------------------------------------------
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
            line.add(ms.getArtistNameFromMusic());
            line.add(ms.getAssociatedAlbum());
            line.add(ms.getClassification());
            centralTableModel.addRow(line);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
        //Aparentemente parece que não precisa de repaint e revalidate mas optei por deixar para já
    };
    public void updateMusicJListModel(ArrayList<MusicCollection> playlists){
        currentUserCollection = new Playlist("Owned Music",(Client) currentUser,currentUser.getAllMusic());
        listModelWest.removeAllElements();
        listModelWest.addElement(currentUserCollection);
        for(MusicCollection cl : currentUser.getAllCollections()){
            listModelWest.addElement(cl);
        }
        //westPanel.revalidate();
        //westPanel.repaint();
        //Aparentemente parece que não precisa de repaint e revalidate mas optei por deixar para já
    };
    public void updateBascketJListModel(){
        listModelEast.removeAllElements();
        for(Music m : ((Client)currentUser).getListOfMusicsToBuy()){
            listModelEast.addElement(m);
            System.out.println("Adicionando música ao basket: " + m);
        }

        eastPanel.revalidate();
        eastPanel.repaint();
        //Aparentemente parece que não precisa de repaint e revalidate mas optei por deixar para já
    }
    public void updateBalance(){
        balancelbl.setText("Balance" +
                "\n" + ((Client)currentUser).getBalance() + "€");
    }
    public double updateTotalBascketPrice(){
        double totalPrice = 0;
        for(Music m : ((Client)currentUser).getListOfMusicsToBuy()){
            totalPrice += m.getPrice();
        }
        totallbl.setText("Total " + totalPrice + "€");
        return totalPrice;
    }
    public Music getSelectedMusicOnCentralTable(){
        int row = centralTable.getSelectedRow();
        if(row != -1){
            int updatedIndex = centralTable.convertRowIndexToModel(row); //Este metodo atualiza o index do elemento
            ArrayList<Music> musics = selectedPlaylist.getMusicList();
            return musics.get(updatedIndex);
        }
        return null;
    }
    public Music getSelectedMusicOnSearchTable(){
        int row = searchMusicTable.getSelectedRow();
        if(row != -1){
            int updatedIndex = searchMusicTable.convertRowIndexToModel(row); //Este metodo atualiza o index do elemento
            ArrayList<Music> musics = new ArrayList<>();
            if(comboSearchBox.getSelectedIndex() == 0) musics = search.getFoundMusics();
            else if(comboSearchBox.getSelectedIndex() == 1) musics = search.getFoundMusicsByArtist();
            return musics.get(updatedIndex);
        }
        return null;
    }
    public Music getSelectedMusicOnBascket(){
        int row = basketList.getSelectedIndex();
        if(row != -1){
            ArrayList<Music> music = ((Client)currentUser).getListOfMusicsToBuy();
            return music.get(row);
        }
        return null;
    }
    public MusicCollection getSelectedPlaylist(){
        int row = playlistListWest.getSelectedIndex()-1;
        if(row != -1){
            ArrayList<MusicCollection> playlist = currentUser.getAllCollections();
            return playlist.get(row);
        }
        return null;
    }
    public void onlogOutbtnClick() throws IOException, ClassNotFoundException {
        guiManager.logoutClient();
    }
    public void onRemoveFromPlaylistClick(){
        Music selectedMusic = getSelectedMusicOnCentralTable();
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
                        Music selectedMusic = getSelectedMusicOnCentralTable();
                        if(selectedMusic != null){
                            if(!pl.getMusicList().contains(selectedMusic)){
                                currentUser.addMusicToCollection(selectedMusic,pl);
                            }
                            else JOptionPane.showMessageDialog(null,
                                    "This music is already on that playlist");
                        }
                    }
                });
                playlistMenu.add(playlistItem);
            }
        }
        playlistMenu.show(centralTable,lastPositionMouseRightClickX,lastPositionMouseRightClickY);
    }
    public void addEvaluationClick(){
        JPopupMenu evaluationMenu =new JPopupMenu();
        for(int i = 0; i < 11; i++){
            JMenuItem evaluationItem  = new JMenuItem(String.valueOf(i));
            int finalI = i;
            evaluationItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Music selectedMusic = getSelectedMusicOnCentralTable();
                    if(selectedMusic != null){
                        selectedMusic.addEvaluation((Client)currentUser, finalI);
                        updateMusicJTableModel(selectedPlaylist.getMusicList());
                    }
                }
            });
            evaluationMenu.add(evaluationItem);
        }

        evaluationMenu.show(centralTable,lastPositionMouseRightClickX,lastPositionMouseRightClickY);
    }
    public void onNewPlaylistbtnClick(){
        centralCardLayout.show(centerPanel,"1");
        String[] options = {"Empty Playlist","Random Playlist"};
        int userChoice = JOptionPane.showOptionDialog(null,"Create Playlist","Type of Playlist",
                JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if(userChoice == 0){
            String playlistName = JOptionPane.showInputDialog("Enter the name of the new playlist");
            if(playlistName != null && !playlistName.trim().isEmpty()){
                boolean nameAlreadyExists = false;
                for(MusicCollection mc: currentUser.getAllCollections()){
                    if(mc.getName().equals(playlistName)) nameAlreadyExists = true;
                }
                if(!nameAlreadyExists){
                    currentUser.newCollection(playlistName);
                    updateMusicJListModel(currentUser.getAllCollections());
                    westPanel.revalidate();
                    westPanel.repaint();
                }else {
                    JOptionPane.showMessageDialog(null,"Playlist name already exists");
                }

            }
        } else if(userChoice == 1){

            RockstarIncManager.GENRE[] genres = RockstarIncManager.GENRE.values();
            RockstarIncManager.GENRE selectedGenre = (RockstarIncManager.GENRE) JOptionPane.showInputDialog(null,
                    "Chose the genre: ","Genre", JOptionPane.QUESTION_MESSAGE,null, genres,genres[0]);
            if(selectedGenre != null){
                String nMusicsString = JOptionPane.showInputDialog("Type the number of musics");
                int nMusics;
                try {
                    nMusics = Integer.parseInt(nMusicsString);
                    if(nMusics <= 0)  JOptionPane.showMessageDialog(null,"Please insert a valid number");
                    else{
                        guiManager.randomPlaylistCreationAttempt(selectedGenre,nMusics);
                        updateMusicJListModel(currentUser.getAllCollections());
                    }
                } catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Please insert a valid number");
                }
            }
        }
    }
    public void onPurchasebtnClick(){
        if(updateTotalBascketPrice() > ((Client)currentUser).getBalance()){
            JOptionPane.showMessageDialog(null,"There are not enough money");
        }
        else {
            int userOption = JOptionPane.showConfirmDialog(null,
                    "Confirm purchase", "Buy",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            switch (userOption){
                case 0: ((Client)currentUser).validationOfAquisition(((Client)currentUser).getListOfMusicsToBuy());
                    ((Client)currentUser).getListOfMusicsToBuy().clear();
                    updateBalance();
                    updateBascketJListModel();
                    updateTotalBascketPrice();
                    updateMusicJTableModel(currentUserCollection.getMusicList());
                    JOptionPane.showMessageDialog(null,"All the musics were acquired");
                    westPanel.revalidate();
                    westPanel.repaint();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null,"Canceled");
                    break;
            }

        }
    }
    public void onAddBalancebtnClick(){

        String moneyToAdd = JOptionPane.showInputDialog("How much do you want to add?");

        double money;
        if(moneyToAdd != null){
            try {
                money = Integer.parseInt(moneyToAdd);
                if(money < 5 || money > 999)  JOptionPane.showMessageDialog(null,"Minimum is 5€\nMaximum is 999€");
                else{
                    ((Client)currentUser).addMoney(money);
                    updateBalance();
                }
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Please insert a valid number\nHas to be an Integer");
            }
        }
    }
    public void onDeletePlaylistClick(){
        MusicCollection selected = getSelectedPlaylist();
        if(selected != null){
            int confirmation = JOptionPane.showConfirmDialog(null, "Comfirm the elimination of " + selected.getName());
            if(confirmation == 0){
                currentUser.removeMusicCollection(selected);
                updateMusicJListModel(currentUser.getAllCollections());
                westPanel.revalidate();
                westPanel.repaint();
                System.out.println("Playlist deleted");
            }
        }
    }
    public void onVisibilityClick(){
        MusicCollection selected = getSelectedPlaylist();
        if(selected != null){
            boolean isPublic = ((Playlist)selected).getPublicState();
            String playlistState = "";
            if(isPublic) playlistState = "public";
            else playlistState = "private";
            String[] options = {"Public","Private"};

            int confirmation = JOptionPane.showOptionDialog(null, "The Playlist is " +
                    playlistState + ". Select an option.", "Visibility",JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

            if(confirmation == 0){
                ((Playlist) selected).setPublicState(true);
            }
            else {
                ((Playlist) selected).setPublicState(false);
            }
            updateMusicJListModel(currentUser.getAllCollections());
            westPanel.revalidate();
            westPanel.repaint();
            System.out.println("Playlist changed");
        }
    }
    public void newSearch(){
        // Tivemos de desligar o actionListner para não interferir com a seleção ca bombo box
        ActionListener listener = comboSearchBox.getActionListeners()[0];
        comboSearchBox.removeActionListener(listener);
        comboSearchBox.setSelectedIndex(0);
        comboSearchBox.addActionListener(listener);

        centralCardLayout.show(centerPanel, "2");
        searchCardLayout.show(searchTablesPanel,"1");
        search = guiManager.newSearch(searchTextField.getText());
        updateSearchMusicTable(search.getFoundMusics());
        centerPanel.revalidate();
        centerPanel.repaint();
    }
    public void updateSearchMusicTable(ArrayList<Music> list){
        searchMusicTableModel.setRowCount(0);
        if(list != null){
            for(Music ms : list){
                Vector <Object> line = new Vector<>();
                line.add(ms.getName());
                line.add(ms.getArtistNameFromMusic());
                line.add(ms.getAssociatedAlbum());
                line.add(ms.getClassification());
                searchMusicTableModel.addRow(line);
            }
        }
    }
    public void updateSearchCollectionTable(){
        searchCollectionTableModel.setRowCount(0);
        if(search.getFoundMusicCollections() != null){
            for(MusicCollection mc : search.getFoundMusicCollections()){
                boolean isAlbum = mc instanceof Album;
                Vector <Object> line = new Vector<>();
                line.add(mc.getName());
                if(isAlbum) {
                    line.add("Album");
                    line.add(((Album) mc).getMainCreator().getName());
                }
                else {
                    line.add("Playlist");
                    line.add(((Playlist) mc).getClientCreator().getName());
                }
                searchCollectionTableModel.addRow(line);
            }
        }
    }
    public void onSearchComboBoxClick(){
        //eventualmete adicionar a pesquisa de colleções e de artistas
        int comboSelection = comboSearchBox.getSelectedIndex();
        switch (comboSelection){
            case 0:
                updateSearchMusicTable(search.getFoundMusics());
                searchCardLayout.show(searchTablesPanel,"1");
                break;
            case 1:
                updateSearchMusicTable(search.getFoundMusicsByArtist());
                searchCardLayout.show(searchTablesPanel,"1");
                break;
            case 2:
                updateSearchCollectionTable();
                searchCardLayout.show(searchTablesPanel,"2");
                break;
        }
    }
    public void onAcquireMusicClick(){
        Music selectedMusic = getSelectedMusicOnSearchTable();
        if(currentUser.getAllMusic().contains(selectedMusic)) JOptionPane.showMessageDialog(null, "The song was already acquired");
        else{
            if(selectedMusic.getPrice() == 0) {
                JOptionPane.showMessageDialog(null, "The song is free. Song acquired, check it on your main collection");
                currentUser.newMusicToAllCollection(selectedMusic);
            }
            else{
                JOptionPane.showMessageDialog(null, "The song costs is " + selectedMusic.getPrice() + "€ . Check it on your shopping basket");
                ((Client)currentUser).addMusicToMusicToBuy(selectedMusic);
                updateBascketJListModel();
            }
        }
    }
    public void onPriceHistoricClick(){
        Music selectedMusic = getSelectedMusicOnSearchTable();

        DefaultListModel<String> model = new DefaultListModel<>();
        for(PriceHistory ph : selectedMusic.getPriceHistory()){
            model.addElement(ph.getNewPrice() + "€ at " + ph.getPriceChangeDate().getDayOfMonth() + "/" +
                    ph.getPriceChangeDate().getMonthValue() + "/" +
                    ph.getPriceChangeDate().getYear());
        }
        JList<String> listOfPrices = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(listOfPrices);
        scrollPane.setPreferredSize(new Dimension(300,100));
        JOptionPane.showMessageDialog(null, scrollPane);
    }
    public void onPriceHistoricBascketClick(){
        Music selectedMusic = getSelectedMusicOnBascket();

        DefaultListModel<String> model = new DefaultListModel<>();
        for(PriceHistory ph : selectedMusic.getPriceHistory()){
            model.addElement(ph.getNewPrice() + "€ at " + ph.getPriceChangeDate().getDayOfMonth() + "/" +
                    ph.getPriceChangeDate().getMonthValue() + "/" +
                    ph.getPriceChangeDate().getYear());
        }
        JList<String> listOfPrices = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(listOfPrices);
        scrollPane.setPreferredSize(new Dimension(300,100));
        JOptionPane.showMessageDialog(null, scrollPane);
    }
    public void onRemoveFromBasketClick(){
        Music selectedMusic = getSelectedMusicOnBascket();
        ((Client)currentUser).getListOfMusicsToBuy().remove(selectedMusic);
        updateBascketJListModel();
        updateTotalBascketPrice();
    }
    public void onCleanBasketClick(){
        int option = JOptionPane.showConfirmDialog(null, "All the elements on the basket will be eliminated, are you shore?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0){
            ((Client)currentUser).getListOfMusicsToBuy().clear();
            updateBascketJListModel();
            updateTotalBascketPrice();
        }
    }
}