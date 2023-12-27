package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class MusicCreatorGUIX extends JFrame {
    private GUIManager guiManager;
    private DefaultTableModel centralTableModel;
    private DefaultTableModel searchMusicTableModel;
    private DefaultTableModel searchCollectionTableModel;
    private DefaultListModel<MusicCollection> listModelWest;
    private JPanel centerPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private MusicCollection selectedAlbum;
    private JTable centralTable;
    private JTable searchMusicTable;
    private JList<MusicCollection> albumListWest;
    private int lastPositionMouseRightClickX;
    private int lastPositionMouseRightClickY;
    private MusicCollection currentUserCollection;
    private JLabel newMusicLbl;
    private CardLayout centralCardLayout;
    private Search search;
    private JTextField searchTextField;
    private TextField musicNameTextField;
    private TextField priceTextField;
    private JComboBox<RockstarIncManager.GENRE> selectedGender;

    public MusicCreatorGUIX(String username, GUIManager guiManager){
        super("Music Creator - " + username);
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
        JMenuItem addToAlbumMenu = new JMenuItem("Add to Album");
        JMenuItem editMusicMenu = new JMenuItem("Edit Music");
        centralTablePopMenu.add(addToAlbumMenu);
        centralTablePopMenu.add(editMusicMenu);
        addToAlbumMenu.addActionListener(e -> addMusicToAlbumOnClick());
        editMusicMenu.addActionListener(e -> editMusicOnClick());

        //Central PopUpMenu when a music on an Album is selected
        JPopupMenu centralTablePUM2 = new JPopupMenu();
        JMenuItem removeFromAlbum = new JMenuItem("Remove from Album");
        JMenuItem editMusicMenu2 = new JMenuItem("Edit Music");
        centralTablePUM2.add(removeFromAlbum);
        centralTablePUM2.add(editMusicMenu2);
        removeFromAlbum.addActionListener(e -> onRemoveFromAlbumClick());
        editMusicMenu2.addActionListener(e -> editMusicOnClick());

        //West PopUpMenu when an Album is selected
        JPopupMenu westListPopMenu = new JPopupMenu();
        JMenuItem deleteAlbum = new JMenuItem("Delete Album");
        westListPopMenu.add(deleteAlbum);
        deleteAlbum.addActionListener(e -> onDeleteAlbumClick());

        //Central PopUpMenu for searchedMusic
        JPopupMenu centralTableSearchedMusicPuM = new JPopupMenu();
        JMenuItem editMusicMenu3 = new JMenuItem("Edit Music");
        centralTableSearchedMusicPuM.add(editMusicMenu3);
        editMusicMenu3.addActionListener(e -> editMusicSearchTableOnClick());



        //------------------------------------------------WEST PANEL----------------------------------------------------

        /*
        On the west panel there is a visual representation of all the albums the user has created. The first
        element on the list isn't really an album but the collection of all the music the user has created. This way
        the name of the element is "Owned Music" (with all the music) and the interaction with it is different from the
        other elements on this list. When the program runs it is the first collection that is selected and, respectively,
        presented on the central table. This list has two action listeners associated. The first simply makes the
        selected element produce the change of the central table, presenting the musics of that playlist or the owned
        music. The second action listener is only activated when the there is a right click on the albums, and not
        on the "Owned Music" element. This way, we can edit or eliminate an album.

        In this panel we also have a label that just presents the list and a button responsible for creating albums

         */

        //Panel creation
        westPanel = new JPanel(new GridBagLayout());
        JLabel playlistLabel =  new JLabel();
        playlistLabel.setText("Album");

        //ListModel creation, there is a method for updating the model in different locations of the code
        listModelWest = new DefaultListModel<>();
        updateMusicJListModel();

        //In case of a new user to is created and turn the selected element the "Owned music" element
        if(currentUserCollection == null) currentUserCollection = new Album();
        selectedAlbum = currentUserCollection;

        //Associate the model to the new list and add the action listeners
        albumListWest = new JList<>(listModelWest);
        albumListWest.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){ //Adjust the for a scrolling event
                selectedAlbum = albumListWest.getSelectedValue();
                if(selectedAlbum != null){
                    //It will update the central table and show the first card on the central panel.
                    //(the second shows the search tables)
                    updateMusicJTableModel(selectedAlbum.getMusicList());
                    centralCardLayout.show(centerPanel,"1");
                }
            }
        });
        albumListWest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //First, we save the mouse coordinate values to correctly position the popup menus
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = albumListWest.locationToIndex(e.getPoint());
                    Rectangle rectangle = albumListWest.getCellBounds(row,row);
                    if(rectangle != null && rectangle.contains(e.getPoint()) &&
                            //Adding 1 to the row logic to ensure the first element is not included
                            row >= 1 && row < listModelWest.getSize()){
                        albumListWest.setSelectedIndex(row);
                        westListPopMenu.show(e.getComponent(),lastPositionMouseRightClickX,lastPositionMouseRightClickY);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(albumListWest);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton newAlbumBtn = new JButton("New Album");
        newAlbumBtn.addActionListener(e -> onNewAlbumbtnClick());

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
        westPanel.add(newAlbumBtn, cw);

        //------------------------------------------------EAST PANEL----------------------------------------------------

        /*
        This panel have text fields and a button to create new music
         */

        //Panel creation
        eastPanel = new JPanel(new GridBagLayout());
        newMusicLbl =  new JLabel("Name");
        musicNameTextField = new TextField(20);

        RockstarIncManager.GENRE[] genres = RockstarIncManager.GENRE.values();
        selectedGender = new JComboBox<>(genres);

        JLabel priceLbl =  new JLabel("price");
        priceTextField = new TextField(20);

        JButton createMusicBtn = new JButton("New Music");
        createMusicBtn.addActionListener(e -> onCreateMusicBtnClick());

        //Creation of the gridbag constrains
        eastPanel.setPreferredSize(new Dimension(175, 0));
        GridBagConstraints ce = new GridBagConstraints();

        ce.gridx= GridBagConstraints.REMAINDER; //Ocupa o restante espaço na linha
        ce.gridy = GridBagConstraints.RELATIVE; // O compunente é colucado na linha seguinte do ultimo compunente
        ce.gridwidth = GridBagConstraints.REMAINDER;
        ce.fill = GridBagConstraints.HORIZONTAL;
        eastPanel.add(newMusicLbl, ce);
        eastPanel.add(musicNameTextField,ce);
        eastPanel.add(selectedGender, ce);
        eastPanel.add(priceLbl, ce);
        eastPanel.add(priceTextField, ce);
        eastPanel.add(createMusicBtn, ce);

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

        searchTextField = new JTextField("",20);
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
        The central panel features one card layout system. It switches between the table that shows all the music in the
        user's albums and the search panel.

        The search button on the north panel is the responsible for making the search panel visible. The back button
        or any click action on the west panel will show the first panel.

        All the tables are not editable and sorted with the method: "setAutoCreateRowSorter()". The index of the musics
        and collections is corrected in the respectively methods.

        The popup menu on the search music table will allow adding music to a specific Album and edit them.

        The popup menu on the search music allows the user to edit individual musics.

         */

        String[] columnNamesMusic = {"Title", "Artist", "Album", "Classification","Price","Genre","Active"};
        centralTableModel = new DefaultTableModel(columnNamesMusic,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Class<?> getColumnClass(int column) {
                if(column == 3 || column == 4){
                    return Double.class;
                }
                else {
                    return String.class;
                }
            }
        };

        ArrayList<Music> userAllMusic = guiManager.getUserAllMusic();
        updateMusicJTableModel(userAllMusic);
        centralTable = new JTable(centralTableModel);
        centralTable.getTableHeader().setReorderingAllowed(true);
        centralTable.setAutoCreateRowSorter(true);

        //This action listener will trigger different popup menus depending on the selected element in panel west
        centralTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                lastPositionMouseRightClickX = e.getX();
                lastPositionMouseRightClickY = e.getY();
                if(SwingUtilities.isRightMouseButton(e)){
                    int row = centralTable.rowAtPoint(e.getPoint());
                    if(row>=0 && row < centralTable.getRowCount()){
                        centralTable.setRowSelectionInterval(row,row);
                        if(selectedAlbum == null) selectedAlbum = currentUserCollection;
                        if(selectedAlbum.equals(currentUserCollection)){
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
                if(column == 3 || column == 4){
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
        JTable searchCollectionTable = new JTable(searchCollectionTableModel);
        searchCollectionTable.getTableHeader().setReorderingAllowed(true);
        searchCollectionTable.setAutoCreateRowSorter(true);

        //Creation of the back button and combobox
        JButton backToMainbtn = new JButton("Back");
        JPanel searchbtnPanel = new JPanel(new FlowLayout());

        searchbtnPanel.add(backToMainbtn);

        JScrollPane scrollPane4 = new JScrollPane(searchMusicTable);
        scrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchbtnPanel,"North");
        searchPanel.add(scrollPane4,"Center");

        //Creation of the outer cardlayout
        centralCardLayout = new CardLayout();
        centerPanel = new JPanel(centralCardLayout);

        //Adding center panels
        centerPanel.add(scrollPane3,"1");
        centerPanel.add(searchPanel,"2");
        centralCardLayout.show(centerPanel, "1");
        backToMainbtn.addActionListener(e -> centralCardLayout.show(centerPanel, "1"));

        //-----------------------------------------------SOUTH PANEL---------------------------------------------------


        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.X_AXIS));
        southPanel.setPreferredSize(new Dimension(0, 175));

        ArrayList<Integer> overallStatistics = guiManager.getStatistics();

        JLabel totalUsers = new JLabel("Total users " + overallStatistics.get(0));
        JLabel totalSongs = new JLabel("Total musics " + overallStatistics.get(1));
        JLabel totalPriceValue = new JLabel("Total Music Value " + overallStatistics.get(2));
        JLabel totalSales = new JLabel("Total Sales " + overallStatistics.get(3));
        southPanel.add(totalUsers);
        southPanel.add(totalSongs);
        southPanel.add(totalPriceValue);
        southPanel.add(totalSales);

        int totalAlbums = overallStatistics.get(4);
        int counter = 5;
        for(RockstarIncManager.GENRE ge : RockstarIncManager.GENRE.values()){
            southPanel.add(new JLabel(ge +""+ overallStatistics.get(counter)));
            counter++;
        }






        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Adding all panels to the main container BorderLayout
        mainContainer.add(northPanel,"North");
        mainContainer.add(centerPanel,"Center");
        mainContainer.add(eastPanel,"East");
        mainContainer.add(westPanel,"West");
        mainContainer.add(southPanel,"South");

        add(mainContainer);

    }
    public void updateMusicJTableModel(ArrayList<Music> selectedAlbum){
        centralTableModel.setRowCount(0);
        System.out.println("Album size: " + selectedAlbum.size());
        for(Music ms : selectedAlbum){
            Vector <Object> line = new Vector<>();
            line.add(ms.getName());
            line.add(ms.getArtistNameFromMusic());
            String albumName;
            if(ms.getAssociatedAlbum() == null) albumName = "";
            else albumName = ms.getAssociatedAlbum().getName();
            line.add(albumName);
            line.add(ms.getClassification());
            line.add(ms.getPrice());
            line.add(ms.getGenre().toString());
            String status;
            if(ms.isActive()) status = "Active";
            else status = "Inactive";
            line.add(status);
            centralTableModel.addRow(line);
        }
    }
    public void updateMusicJListModel(){
        currentUserCollection =guiManager.getCorrentUserMainCollectionMusicCreator();
        listModelWest.removeAllElements();
        listModelWest.addElement(currentUserCollection);
        for(MusicCollection cl : guiManager.getUserAllCollection()){
            listModelWest.addElement(cl);
        }
    }
    public Music getSelectedMusicOnCentralTable(){
        int row = centralTable.getSelectedRow();
        if(row != -1){
            int updatedIndex = centralTable.convertRowIndexToModel(row);
            ArrayList<Music> musics = selectedAlbum.getMusicList();
            return musics.get(updatedIndex);
        }
        return null;
    }
    public Music getSelectedMusicOnSearchTable(){
        int row = searchMusicTable.getSelectedRow();
        if(row != -1){
            int updatedIndex = searchMusicTable.convertRowIndexToModel(row);
            ArrayList<Music> musics = new ArrayList<>();
            musics = search.getFoundMusics();
            return musics.get(updatedIndex);
        }
        return null;
    }

    public MusicCollection getSelectedAlbum(){
        int row = albumListWest.getSelectedIndex()-1;
        if(row != -1){
            ArrayList<MusicCollection> playlist = guiManager.getUserAllCollection();
            return playlist.get(row);
        }
        return null;
    }
    public void onlogOutbtnClick() throws IOException, ClassNotFoundException {
        guiManager.logoutMCreator();
    }
    public void onRemoveFromAlbumClick(){
        Music selectedMusic = getSelectedMusicOnCentralTable();
        if(selectedMusic!= null){
            guiManager.removeMusicFromCollection(selectedMusic, selectedAlbum);
            selectedMusic.setAssociatedAlbum(null);
            updateMusicJTableModel(selectedAlbum.getMusicList());
            centerPanel.revalidate();
            centerPanel.repaint();
            System.out.println("Music eliminated from Album");
        }
    }
    public void addMusicToAlbumOnClick(){
        JPopupMenu albumsMenu =new JPopupMenu();

        int nOfPlaylists = guiManager.getUserAllCollection().size();

        if(nOfPlaylists == 0){
            JMenuItem emptyList = new JMenuItem("No Albums to Show");
            albumsMenu.add(emptyList);
        } else{
            for(MusicCollection al: guiManager.getUserAllCollection()){
                JMenuItem albumItem  = new JMenuItem(al.getName());
                albumItem.addActionListener(e -> {
                    Music selectedMusic = getSelectedMusicOnCentralTable();
                    if(selectedMusic != null){
                        if(al.getMusicList().contains(selectedMusic)){
                            JOptionPane.showMessageDialog(null,
                                    "This music is already on that album");
                        }
                        else if(selectedMusic.getAssociatedAlbum() != null){

                            String album = selectedMusic.getAssociatedAlbum().getName();
                            JOptionPane.showMessageDialog(null,
                                    "This music is on another album: " + album);
                        }
                        else {
                            guiManager.addMusicToCollection(selectedMusic,al);
                            updateMusicJTableModel(selectedAlbum.getMusicList());
                        }
                    }
                });
                albumsMenu.add(albumItem);
            }
        }
        albumsMenu.show(centralTable,lastPositionMouseRightClickX,lastPositionMouseRightClickY);
    }
    public void editMusicOnClick(){
        Music selectedMusic = getSelectedMusicOnCentralTable();
        guiManager.editMusicDialogCall(selectedMusic);
        updateMusicJTableModel(selectedAlbum.getMusicList());
    }
    public void editMusicSearchTableOnClick(){
        Music selectedMusic = getSelectedMusicOnSearchTable();
        guiManager.editMusicDialogCall(selectedMusic);
        updateSearchMusicTable(search.getFoundMusics());
    }
    public void onNewAlbumbtnClick(){
        centralCardLayout.show(centerPanel,"1");
        String albumName = JOptionPane.showInputDialog("Enter the name of the new album");
        if(albumName != null && !albumName.trim().isEmpty()){
            boolean nameAlreadyExists = false;
            for(MusicCollection mc: guiManager.getUserAllCollection()){
                if(mc.getName().equals(albumName)) nameAlreadyExists = true;
            }
            if(!nameAlreadyExists){
                guiManager.newCollection(albumName);
                MusicCollection newMusicCollection =
                        guiManager.getUserAllCollection().get(guiManager.getUserAllCollection().size()-1);
                selectedAlbum = newMusicCollection;
                updateMusicJListModel();
                updateMusicJTableModel(newMusicCollection.getMusicList());
                westPanel.revalidate();
                westPanel.repaint();
            }else {
                JOptionPane.showMessageDialog(null,"Album name already exists");
            }
        }
    }
    public void onCreateMusicBtnClick(){
        guiManager.newMusicAttempt(musicNameTextField.getText(), priceTextField.getText(), selectedGender.getItemAt(selectedGender.getSelectedIndex()));
    }
    public void onDeleteAlbumClick(){
        MusicCollection selected = getSelectedAlbum();
        if(selected != null){
            int confirmation = JOptionPane.showConfirmDialog(null, "Comfirm the elimination of "
                    + selected.getName());
            if(confirmation == 0){
                guiManager.removeMusicCollection(selected);
                updateMusicJListModel();
                westPanel.revalidate();
                westPanel.repaint();
                System.out.println("Album deleted");
            }
        }
    }
    public void newSearch(){
        centralCardLayout.show(centerPanel, "2");
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
                String albumName;
                if(ms.getAssociatedAlbum() == null) albumName = "";
                else albumName = ms.getAssociatedAlbum().getName();
                line.add(albumName);
                line.add(ms.getClassification());
                line.add(ms.getPrice());
                line.add(ms.getGenre().toString());
                String status;
                if(ms.isActive()) status = "Active";
                else status = "Inactive";
                line.add(status);
                searchMusicTableModel.addRow(line);
            }
        }
    }
}