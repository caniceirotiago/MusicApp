package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import java.awt.*;
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
    private JList<MusicCollection> playlistListWest;
    private JList<Music> basketList;
    private int lastPositionMouseRightClickX;
    private int lastPositionMouseRightClickY;
    private MusicCollection currentUserCollection;
    private JLabel balanceLbl;
    private JLabel totalLbl;
    private CardLayout centralCardLayout;
    private CardLayout searchCardLayout;
    private Search search;
    private JTextField searchTextField;
    private JComboBox<String> comboSearchBox;

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

        //Central PopUpMenu when a music on allMusic is selected
        JPopupMenu centralTablePopMenu = new JPopupMenu();
        JMenuItem addToPlaylistMenu = new JMenuItem("Add to Playlist");
        JMenuItem evaluateMusicMenu = new JMenuItem("Evaluate Music");
        centralTablePopMenu.add(addToPlaylistMenu);
        centralTablePopMenu.add(evaluateMusicMenu);
        addToPlaylistMenu.addActionListener(e -> addMusicToPlaylistOnClick());
        evaluateMusicMenu.addActionListener(e -> addEvaluationClick());

        //Central PopUpMenu when a music on a playlist is selected
        JPopupMenu centralTablePUM2 = new JPopupMenu();
        JMenuItem removeFromPlaylist = new JMenuItem("Remove from Playlist");
        JMenuItem evaluateMusic2 = new JMenuItem("Evaluate Music");
        centralTablePUM2.add(removeFromPlaylist);
        centralTablePUM2.add(evaluateMusic2);
        removeFromPlaylist.addActionListener(e -> onRemoveFromPlaylistClick());
        evaluateMusic2.addActionListener(e -> addEvaluationClick());

        //West PopUpMenu when a playlist is selected
        JPopupMenu westListPopMenu = new JPopupMenu();
        JMenuItem deletePlaylist = new JMenuItem("Delete Playlist");
        JMenuItem changeVisibility = new JMenuItem("Change Visibility");
        westListPopMenu.add(deletePlaylist);
        westListPopMenu.add(changeVisibility);
        deletePlaylist.addActionListener(e -> onDeletePlaylistClick());
        changeVisibility.addActionListener(e -> onVisibilityClick());

        //Central PopUpMenu for searchedMusic
        JPopupMenu centralTableSearchedMusicPuM = new JPopupMenu();
        JMenuItem acquireMusic = new JMenuItem("Acquire Music");
        JMenuItem seePriceHistoric = new JMenuItem("See Historic Prices");
        centralTableSearchedMusicPuM.add(acquireMusic);
        centralTableSearchedMusicPuM.add(seePriceHistoric);
        acquireMusic.addActionListener(e -> onAcquireMusicClick());
        seePriceHistoric.addActionListener(e -> onPriceHistoricClick());

        //East PopUpMenu Shopping Basket
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


        //------------------------------------------------WEST PANEL----------------------------------------------------

        /*
        On the west panel there is a visual representation of all the playlists the user has created. The first
        element on the list isn't really a playlist but the collection of all the music the user owns. This way
        the name of the element is "Owned Music" (with all the music) and the interaction with it is different from the
        other elements on this list. When the program runs it is the first collection that is selected and, respectively,
        presented on the central table. This list has two action listeners associated. The first simply makes the
        selected element produce the change of the central table, presenting the musics of that playlist or the owned
        music. The second action listener is only activated when the there is a right click on the playlists, and not
        on the "Owned Music" element. This way, we can change the visibility of a playlist to public or private and
        eliminate a playlist.

        In this panel we also have a label that just presents the list and a button responsible for adding new playlists
        There are two different ways of creating a new playlist. The first option simply creates an empty playlists and
        the user is asked for the name of the new playlist. It doesn't allow the user to create a playlist with an
        already used name. The second way is to generate randomly a playlist by giving a genre and the number of musics.
        This way, we have to ensure that the user already owns the selected musics. If there is any music or musics
        that the user does not own it will show a list of musics, the prices and three possibilities:

        -Add to the basket: It will create a playlist with the free or the already owned musics and the others
        will be added to the shopping basket (the generated playlist will not have all the music element that the user
        asked for).
        -Buy the musics: this button will only be enabled if the user has enough money and if pressed will create a new
        playlist and make the purchase. (will generate a playlist with all the elements the user asked for)
        -Only Free music: this button will generate another list with only free music. If there aren't enough free
        musics on that genre, the user will be informed.
        All the elements are added with a GridBag Layout

         */

        //Panel creation
        westPanel = new JPanel(new GridBagLayout());
        JLabel playlistLabel =  new JLabel();
        playlistLabel.setText("Playlist");

        //ListModel creation, there is a method for updating the model in different locations of the code
        listModelWest = new DefaultListModel<>();
        updateMusicJListModel(currentUser.getAllCollections());

        //In case of a new user to is created and turn the selected element the "Owned music" element
        if(currentUserCollection == null) currentUserCollection = new Playlist();
        selectedPlaylist = currentUserCollection;

        //Associate the model to the new list and add the action listeners
        playlistListWest = new JList<>(listModelWest);
        playlistListWest.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){ //Adjust the for a scrolling event
                selectedPlaylist = playlistListWest.getSelectedValue();
                if(selectedPlaylist != null){
                    //It will update the central table and show the first card on the central panel.
                    //(the second shows the search tables)
                    updateMusicJTableModel(selectedPlaylist.getMusicList());
                    centralCardLayout.show(centerPanel,"1");
                }
            }
        });
        playlistListWest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //First, we save the mouse coordinate values to correctly position the popup menus
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = playlistListWest.locationToIndex(e.getPoint());
                    Rectangle rectangle = playlistListWest.getCellBounds(row,row);
                    if(rectangle != null && rectangle.contains(e.getPoint()) &&
                            //Adding 1 to the row logic to ensure the first element is not included
                            row >= 1 && row < listModelWest.getSize()){
                        playlistListWest.setSelectedIndex(row);
                        westListPopMenu.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(playlistListWest);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton newPlaylistBtn = new JButton("New Playlist");
        newPlaylistBtn.addActionListener(e -> onNewPlaylistbtnClick());

        //Creation of the gridbag constrains
        westPanel.setPreferredSize(new Dimension(175, 0));
        GridBagConstraints cw = new GridBagConstraints();

        cw.gridx = 0;
        cw.gridy = 0;
        cw.weightx = 0.2;
        cw.anchor = GridBagConstraints.CENTER;
        cw.fill = GridBagConstraints.NONE;
        westPanel.add(playlistLabel, cw);

        cw.gridy++;
        cw.weighty = 0.6;
        cw.fill = GridBagConstraints.BOTH;
        westPanel.add(scrollPane, cw);

        cw.gridy++;
        cw.weighty = 0.2;
        cw.fill = GridBagConstraints.NONE;
        cw.anchor = GridBagConstraints.NORTH;
        westPanel.add(newPlaylistBtn, cw);

        //------------------------------------------------EAST PANEL----------------------------------------------------

        /*
        The east panel has the shopping elements. It has a label with the real time balance, a list of musics to buy,
        a button that allows the user to add money, and a purchase button.
        There are two different ways to add elements to this list, by adding individual musics on the search tabels or
        adding one or more musics at a time from the randomly created playlist event. The popup menu on the list allows
        the user to remove an individual music, remove all the music or present the price history. The user can add
        money at the range of 5€ to 999€. The purchase has a confirmation box, and it is not allowed to become with a
        negative balance.
         */

        //Panel creation
        eastPanel = new JPanel(new GridBagLayout());
        balanceLbl =  new JLabel();
        balanceLbl.setText("Balance " + "\n " + ((Client)currentUser).getBalance() + "€");
        JButton addBalanceBtn = new JButton("Add Money");
        addBalanceBtn.addActionListener(e -> onAddBalancebtnClick());
        JLabel BasketLbl =  new JLabel("Basket");

        //ListModel creation, there is a method for updating the model in different locations of the code
        listModelEast = new DefaultListModel<>();
        updateBasketJListModel();

        //Associate the model to the new list and add the action listener
        basketList = new JList<>(listModelEast);
        basketList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = basketList.locationToIndex(e.getPoint());
                    Rectangle rectangle = basketList.getCellBounds(row,row);
                    if(rectangle != null && rectangle.contains(e.getPoint()) &&
                            row >= 0 && row < listModelEast.getSize()){
                        basketList.setSelectedIndex(row);
                        basketMusicPuM.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                    }
                }
            }
        });

        JScrollPane scrollPane2 = new JScrollPane(basketList);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        totalLbl =  new JLabel();
        double totalPrice = updateTotalBascketPrice();
        totalLbl.setText("Total " + totalPrice + "€");

        JButton purchaseBtn = new JButton("Purchase");
        purchaseBtn.addActionListener(e -> onPurchasebtnClick());

        //Creation of the gridbag constrains
        eastPanel.setPreferredSize(new Dimension(175, 0));
        GridBagConstraints ce = new GridBagConstraints();
        ce.gridx = 0;
        ce.gridy = 0;
        ce.weightx = 1;
        ce.weighty = 0.1;
        ce.fill = GridBagConstraints.NONE;
        ce.anchor = GridBagConstraints.CENTER;
        eastPanel.add(balanceLbl, ce);

        ce.gridy++;
        ce.weighty = 0.05;
        eastPanel.add(addBalanceBtn,ce);

        ce.gridy++;
        ce.weighty = 0.05;
        eastPanel.add(BasketLbl, ce);

        ce.gridy++;
        ce.weighty = 0.6;
        ce.fill = GridBagConstraints.BOTH;
        eastPanel.add(scrollPane2, ce);

        ce.gridy++;
        ce.weighty = 0.05;
        ce.anchor = GridBagConstraints.PAGE_START;
        ce.fill = GridBagConstraints.NONE;
        eastPanel.add(totalLbl, ce);

        ce.gridy++;
        ce.weighty = 0.05;
        ce.anchor = GridBagConstraints.NORTH;
        eastPanel.add(purchaseBtn, ce);

        //------------------------------------------------NORTH PANEL---------------------------------------------------

        /*
        The north panel only has the logo, the search text filed, the search button and the logout button added in a
        GridBag Layout
         */

        int newWidth = 100;
        int newHeight = 100;
        ImageIcon originalIcon = new ImageIcon(ImagePaths.APP_ICON);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel logo =  new JLabel(resizedIcon);

        searchTextField = new JTextField("",30);
        JButton searchBtn = new JButton("\uD83D\uDD0D");
        searchBtn.addActionListener(e -> newSearch());

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
        cn.insets = new Insets(0, 40, 0, 0);
        northPanel.add(logo, cn);

        cn.gridx++;
        cn.weightx = 0.25;
        cn.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(Box.createHorizontalStrut(0), cn);

        cn.gridx++;
        cn.weightx = 0;
        cn.fill = GridBagConstraints.NONE;
        northPanel.add(searchTextField, cn);

        cn.gridx++;
        cn.weightx = 0;
        cn.fill = GridBagConstraints.NONE;
        northPanel.add(searchBtn, cn);

        cn.gridx++;
        cn.weightx = 0.25;
        cn.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(Box.createHorizontalStrut(0), cn);

        cn.gridx++;
        cn.weightx = 0.01;
        cn.anchor = GridBagConstraints.EAST;
        cn.insets = new Insets(0, 0, 0, 40);
        northPanel.add(logOutbtn, cn);

        //-----------------------------------------------CENTER PANEL---------------------------------------------------

        /*
        The central panel is the most complex in this frame. It features two card layout systems. The first one switches
        between the table that shows all the music in the user's playlist and the search panel. The other cardLayout is
        within the search panel and switches between the table that shows music and the table that will show music
        collections.

        The search button on the north panel is the responsible for making the search panel visible. The back button
        or any click action on the west panel will show the first panel. When the search panel is visible, also appears
        a combo box with 3 options:
        -Musics: will show all the musics with the searched term
        -Musics by artist: will show all the musics associated with a term searched on the artist name
        -Collections: will show all the albums and all the public playlists

        All the tables are not editable and sorted with the method: "setAutoCreateRowSorter()". The index of the musics
        and collections is corrected in the respectively methods.

        The popup menu on the search music table will allow adding music to a specific playlist and evaluate them.

        The popup menu on the search music allows the user to acquire individual musics. This will have different
        behaviors depending on whether the music is free or not. If the music is free, it will be added to the
        "Owned Music" list. If not it will be added to the basket. It will also allow the user to see the price history.
         */

        String[] columnNamesMusic = {"Title", "Artist", "Album", "Classification"};
        centralTableModel = new DefaultTableModel(columnNamesMusic,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Class<?> getColumnClass(int column) {
                if(column == 3){
                    return Double.class;
                }
                else {
                    return String.class;
                }
            }
        };

        updateMusicJTableModel(currentUser.getAllMusic());
        centralTable = new JTable(centralTableModel);
        centralTable.getTableHeader().setReorderingAllowed(true);
        centralTable.setAutoCreateRowSorter(true);

        //This action listner will trigger different popup menus depending on the selected element in panel west
        centralTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
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

        //Creates an empty search object
        if(search == null) search = new Search();
        searchMusicTableModel = new DefaultTableModel(columnNamesMusic,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Class<?> getColumnClass(int column) {
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
        searchMusicTable.setAutoCreateRowSorter(true);

        searchMusicTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
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

        //Creation of the collection search table on a nested cardlayout
        String[] columnNamesCollection = {"Collection", "Type", "Creator"};
        searchCollectionTableModel = new DefaultTableModel(columnNamesCollection,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        updateSearchCollectionTable();
        JTable searchCollectionTable = new JTable(searchCollectionTableModel);
        searchCollectionTable.getTableHeader().setReorderingAllowed(true);
        searchCollectionTable.setAutoCreateRowSorter(true);

        //Creation of the back button and combobox
        JButton backToMainbtn = new JButton("Back");
        String[] filterOptions = {"Music", "Music By Artist","Collections"};
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

        //Creation of the inner cardlayout
        searchCardLayout  = new CardLayout();
        searchTablesPanel = new JPanel(searchCardLayout);
        searchTablesPanel.add(scrollPane4,"1");
        searchTablesPanel.add(scrollPane5,"2");
        searchCardLayout.show(searchTablesPanel,"1");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchbtnPanel,"North");
        searchPanel.add(searchTablesPanel,"Center");

        //Creation of the outer cardlayout
        centralCardLayout = new CardLayout();
        centerPanel = new JPanel(centralCardLayout);

        //Adding center panels
        centerPanel.add(scrollPane3,"1");
        centerPanel.add(searchPanel,"2");
        centralCardLayout.show(centerPanel, "1");
        backToMainbtn.addActionListener(e -> centralCardLayout.show(centerPanel, "1"));

        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Adding all panels to the main container BorderLayout
        mainContainer.add(northPanel,"North");
        mainContainer.add(centerPanel,"Center");
        mainContainer.add(eastPanel,"East");
        mainContainer.add(westPanel,"West");

        add(mainContainer);

    }
    public void updateMusicJTableModel(ArrayList<Music> selectedPlaylist){
        centralTableModel.setRowCount(0);
        System.out.println("Playlist size: " + selectedPlaylist.size());
        for(Music ms : selectedPlaylist){
            Vector <Object> line = new Vector<>();
            line.add(ms.getName());
            line.add(ms.getArtistNameFromMusic());
            line.add(ms.getAssociatedAlbum());
            line.add(ms.getClassification());
            centralTableModel.addRow(line);
        }
    }
    public void updateMusicJListModel(ArrayList<MusicCollection> playlists){
        currentUserCollection = new Playlist("Owned Music",(Client) currentUser,currentUser.getAllMusic());
        listModelWest.removeAllElements();
        listModelWest.addElement(currentUserCollection);
        for(MusicCollection cl : currentUser.getAllCollections()){
            listModelWest.addElement(cl);
        }
    }
    public void updateBasketJListModel(){
        listModelEast.removeAllElements();
        for(Music m : ((Client)currentUser).getListOfMusicsToBuy()){
            listModelEast.addElement(m);
            System.out.println("Adicionando música ao basket: " + m);
        }
    }
    public void updateBalance(){
        balanceLbl.setText("Balance" +
                "\n" + ((Client)currentUser).getBalance() + "€");
    }
    public double updateTotalBascketPrice(){
        double totalPrice = 0;
        for(Music m : ((Client)currentUser).getListOfMusicsToBuy()){
            totalPrice += m.getPrice();
        }
        totalLbl.setText("Total " + totalPrice + "€");
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
                playlistItem.addActionListener(e -> {
                    Music selectedMusic = getSelectedMusicOnCentralTable();
                    if(selectedMusic != null){
                        if(!pl.getMusicList().contains(selectedMusic)){
                            currentUser.addMusicToCollection(selectedMusic,pl);
                        }
                        else JOptionPane.showMessageDialog(null,
                                "This music is already on that playlist");
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
            evaluationItem.addActionListener(e -> {
                Music selectedMusic = getSelectedMusicOnCentralTable();
                if(selectedMusic != null){
                    selectedMusic.addEvaluation((Client)currentUser, finalI);
                    updateMusicJTableModel(selectedPlaylist.getMusicList());
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
                    updateBasketJListModel();
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
            String playlistState;
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
                updateBasketJListModel();
                updateTotalBascketPrice();
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
        updateBasketJListModel();
        updateTotalBascketPrice();
    }
    public void onCleanBasketClick(){
        int option = JOptionPane.showConfirmDialog(null, "All the elements on the basket will be eliminated, are you shore?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0){
            ((Client)currentUser).getListOfMusicsToBuy().clear();
            updateBasketJListModel();
            updateTotalBascketPrice();
        }
    }
}