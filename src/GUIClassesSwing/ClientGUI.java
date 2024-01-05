/**
 * @Authors Tiago Caniceiro & Pedro Monteiro
 * @Version 1.0
 */
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

/**
 * Classe Graphical User Interface associada ao cliente
 * Carregada depois da validação do login na interface Grafica do login e registo
 *
 */
public class ClientGUI extends JFrame {
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

    /**
     * Construtor do GUI do cliente, que inicializa os componentes da frame associada ao utilizador
     * @param username O username associado ao utilizador Cliente.
     * @param guiManager O gestor gráfico associado ao Cliente.
     */
    public ClientGUI(String username, GUIManager guiManager){
        super("Client - " + username);
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

    /**
     * Método que inicializa os componentes gráficos
     * No geral, os componentes são divididos em paineis Norte, Este, Centro e Oeste e os seus detalhes, como botões,
     * Pop-up menus e outros detalhes que correspondem a cada painel individualmente.
     * O painel Norte é responsável por ter o simbolo da musica, a barra de pesquisa e o botão de logout para ser
     * possível ao utilizador sair da sua frame de Cliente e voltar à frame de registo/login.
     *
     * O painel Oeste é o painel responsável por lidar com a coleção geral de musicas do utilizador e por ter uma lista
     * onde é possível criar e manipular novas playlists. Ao criar uma nova playlist, um botão aparece ao utilizador a
     * perguntar se quer criar uma playlist aleatória ou uma playlist vazia.
     *
     * O painel Central é o responsável por mostrar ao utilizador as musicas que possuí, as musicas
     * que inclui nas playlists que criou e tambem as musicas que existem no programa quando o utilizador procura
     * por musicas novas pela pesquisa.
     *
     * O painel Este mostra ao utilizador quanto dinheiro é que tem na carteira, permite adicionar mais musicas à
     * sua coleção e permite adquirir mais musicas.
     */
    public void initComponents(){
        //Container principal, ao qual vão ser incorporados os paineis e suas especificidades
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        //---------------------ADDING POPUPMENUS AND SUBMENUS---------------------
        /**
         *Esta seção destina-se aos detalhes como popup menus e submenus que são utilizados nos paineis da frame do
         * Cliente. Foram colocados noutro local para permitir melhor visualização e organização do código.
         */

        //PopUp menu central para adicionar musicas a playlist ou avaliar musica quando as musicas NÃO ESTÃO inseridas
        //em nenhuma playlist e se encontram apenas na coleção geral do cliente
        JPopupMenu centralTablePopMenu = new JPopupMenu();
        JMenuItem addToPlaylistMenu = new JMenuItem("Add to Playlist");
        JMenuItem evaluateMusicMenu = new JMenuItem("Evaluate Music");
        centralTablePopMenu.add(addToPlaylistMenu);
        centralTablePopMenu.add(evaluateMusicMenu);
        addToPlaylistMenu.addActionListener(e -> addMusicToPlaylistOnClick());
        evaluateMusicMenu.addActionListener(e -> addEvaluationClick());

        //Popup menu central para remover musicas de uma playlist ou avaliar. Este popup menu só surge
        //quando se clica na musica DENTRO de uma playlist.
        JPopupMenu centralTablePUM2 = new JPopupMenu();
        JMenuItem removeFromPlaylist = new JMenuItem("Remove from Playlist");
        JMenuItem evaluateMusic2 = new JMenuItem("Evaluate Music");
        centralTablePUM2.add(removeFromPlaylist);
        centralTablePUM2.add(evaluateMusic2);
        removeFromPlaylist.addActionListener(e -> onRemoveFromPlaylistClick());
        evaluateMusic2.addActionListener(e -> addEvaluationClick());

        //PopUp menu oeste que abre opções relacionadas com playlists criadas pelo Cliente. É possível apagar playlsts
        //ou alterar a sua visibilidade
        JPopupMenu westListPopMenu = new JPopupMenu();
        JMenuItem deletePlaylist = new JMenuItem("Delete Playlist");
        JMenuItem changeVisibility = new JMenuItem("Change Visibility");
        westListPopMenu.add(deletePlaylist);
        westListPopMenu.add(changeVisibility);
        deletePlaylist.addActionListener(e -> onDeletePlaylistClick());
        changeVisibility.addActionListener(e -> onVisibilityClick());

        //Central PopUpMenu que aparece quando as musicas no painel central são as musicas
        //que aparecem através da pesquisa
        JPopupMenu centralTableSearchedMusicPuM = new JPopupMenu();
        JMenuItem acquireMusic = new JMenuItem("Acquire Music");
        JMenuItem seePriceHistoric = new JMenuItem("See Historic Prices");
        centralTableSearchedMusicPuM.add(acquireMusic);
        centralTableSearchedMusicPuM.add(seePriceHistoric);
        acquireMusic.addActionListener(e -> onAcquireMusicClick());
        seePriceHistoric.addActionListener(e -> onPriceHistoricClick());

        //East PopUpMenu que seleciona as musicas que estão no cesto, que permitem ao utilizador escolher as opções
        //de remover as musicas do cesto, ver as alterações de preço que a musica sofreu ao longo do tempo e também
        //a opção de limpar o cesto
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

        /**
         * Funcionalidades que correspondem ao painel Oeste
         * O painel Oeste representa visualmente as coleções e playlists que o Cliente tem. Este painel tem associado
         * dois action listeners e a possibilidade de criar novas playlists, vazias ou aleatórias.
         *
         * Inicialmente há sempre uma coleção de musica presente (Owned Music), que representa todas as musicas que o
         * utilizador adquiriu. Tem funcionalidades diferentes das outras playlists que possam ser criadas, já que o seu
         * primeiro action listener (botão esquerdo do rato) demonstra as musicas que possui no painel central. No entanto
         * esta playlist não tem funcão nenhuma associada ao botao direito do rato, ao contrario das outras playlists
         * que possam ser criadas.
         *
         * As outras playlists criadas possuem igualmente a funcionalidade associada ao botão esquerdo do rato, mas o
         * segundo action listener permite eliminar uma playlist ou alterar a sua visibilidade.
         *
         * Há também a possibilidade de se criar novas playlists através da funcionalidade associada a um botão
         * "new Playlist", que permite criar novas playlists vazias ou aleatórias. As novas playlists vazias tem
         * obrigatoriamente um nome e não é possivel repetir nomes.
         *
         * As playlists aleatórias permitem a criação de uma nova playlist com um nome e um número definido pelo
         * utilizador de quantas musicas essa playlist terá e o género que quer. Se houver um numero suficiente de musicas adquiridas
         * pelo utilizador para criar a playlist ela é criada. Se o utilizador não tiver musicas suficientes, há 3 opções
         * que o utilizador pode escolher:
         * Adicionar musicas ao cesto: Vão ser adicionadas ao cesto musicas aleatórias que o utilizador não tem possuí
         * e cria a playlist apenas com as que o utilizador tem.
         *Comprar as musicas: Esta funcionalidade só está disponivel se o utilizador tiver saldo suficiente para comprar
         * as musicas e se assim for, compra as musicas e adiciona à playlist, criando a playlist com os elementos que o
         * utilizador escolheu.
         * Só musica grátis: Esta funcionalidade tambem está disponivel no momento de criação da playlist aleatória e
         * quando escolhida, cria a playlist apenas com musicas que estejam disponiveis gratuitamente no sistema, avisando
         * o utilizador no caso de não haverem musicas suficientes para o numero que o utilizador escolheu.
         */

        /**
         * Criação do painel Oeste e implementação das diversas funcionalidades
         */
        westPanel = new JPanel(new GridBagLayout());
        JLabel playlistLabel =  new JLabel();
        playlistLabel.setText("Playlist");

        //ListModel creation, there is a method for updating the model in different locations of the code
        listModelWest = new DefaultListModel<>();
        updateMusicJListModel();

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
        /**
         * O painel Este tem as funcionalidades e elementos relacionados à compra e aquisição de musicas.
         * Tem uma etiqueta com o balanço a tempo real do utilizador, uma lista que corresponde ao cesto com as musicas
         * que o utilizador quer comprar, um botão que permite adicionar saldo à carteira do cliente e um botão de compra
         * de musicas que estejam no cesto.
         * Existem duas maneiras de adicionar musicas ao cesto. Através da seleção manual de musicas pela pesquisa de musicas
         * ou através de musicas que sejam inseridas no cesto pela criação de uma playlist aleatoria.
         * Este cesto tem um popup menu com a funcionalidade de remover a musica selecionada do cesto (quando carregada
         * individualmente), remover todas as musicas no cesto ou visualizar o historico de preços associado às musicas.
         * A compra de musicas tem uma caixa de confirmação, que impede a compra caso não haja saldo suficiente.
         *
         * Relativamente à carteira, o utilizador pode adicionar dinheiro desde 5€ até 999€.
         */

        //Criação do painel Este
        eastPanel = new JPanel(new GridBagLayout());
        balanceLbl =  new JLabel();
        double userBalance = guiManager.getUserBalance();
        balanceLbl.setText("Balance " + "\n " + userBalance + "€");
        JButton addBalanceBtn = new JButton("Add Money");
        addBalanceBtn.addActionListener(e -> onAddBalancebtnClick());
        JLabel BasketLbl =  new JLabel("Basket");

        //Criação da lista modelo para inserir no painel Este do cesto
        listModelEast = new DefaultListModel<>();
        updateBasketJListModel();

        //Associação da lista modelo à lista para definir as funcionalidades
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

        /**
         * Painel Norte, que contem o logo, a barra e botão de pesquisa e o botão de LogOut da aplicação
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

        /**
         * O painel Central tem dois sistemas de CardLayout. O primeiro altera entre a tabela que mostra a musica que o
         * utilizador possui e as musicas disponiveis no sistema quando se carrega na procura. O outro sistema altera
         * dentro do painel de pesquisa, quando se procura musicas ou quando se procuram coleções.
         * O botão de pesquisa é responsável por permitir a visibilidade do painel de pesquisa. Ao premir o botão back
         * ou carregar no painel Oeste, o painel central volta a mostrar o primeiro painel, que mostra as musicas do
         * utilizador.
         * Quando o painel de pesquisa está visivel, há uma comboBox com 3 opções:
         * Musica: mostra no painel central todas as musicas com os termos de pesquisa inseridos;
         * Musicas por artista: Mostra todas as musicas associadas a um artista cujo nome foi pesquisado;
         * Coleções: Mostra todos os albuns e coleções publicas disponiveis no sistema;
         *
         * As tabelas não são editáveis e são ordenadas através do método "setAutoCreateRowSorter()". O index das
         * musicas e das coleções é corrigido no método respectivo.
         *
         * O menu de popup na tabela de pesquisa permite adicionar musica a uma playlist especifica e avaliar a musica
         * respectivamente. Este popup menu também permite ao utilizador adquirir musica individual, com comportamento
         * diferente no caso de a musica ser gratuita ou não. No caso de ser gratuita, é adicionada automaticamente à
         * coleção (owned music) do utilizador. No caso de ser paga, é adicionada ao cesto. Permite também ver
         * o historico dos preços da musica.
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

        ArrayList<Music> userAllMusic = guiManager.getUserAllMusic();
        updateMusicJTableModel(userAllMusic);
        centralTable = new JTable(centralTableModel);
        centralTable.getTableHeader().setReorderingAllowed(false);
        centralTable.setAutoCreateRowSorter(true);

        //Este action listener acciona popups diferentes consoante o elemento selecionado no painel oeste
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

        //Cria um objeto tipo search vazio
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
        searchMusicTable.getTableHeader().setReorderingAllowed(false);
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


        //Criação da pesquisa por coleções num cardLayout aninhado
        String[] columnNamesCollection = {"Collection", "Type", "Creator"};
        searchCollectionTableModel = new DefaultTableModel(columnNamesCollection,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        updateSearchCollectionTable();
        JTable searchCollectionTable = new JTable(searchCollectionTableModel);
        searchCollectionTable.getTableHeader().setReorderingAllowed(false);
        searchCollectionTable.setAutoCreateRowSorter(true);

        //Criação do botão de retroceder e combobox
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

        //Criação do cardLayout interior
        searchCardLayout  = new CardLayout();
        searchTablesPanel = new JPanel(searchCardLayout);
        searchTablesPanel.add(scrollPane4,"1");
        searchTablesPanel.add(scrollPane5,"2");
        searchCardLayout.show(searchTablesPanel,"1");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchbtnPanel,"North");
        searchPanel.add(searchTablesPanel,"Center");

        //Criação do painel CardLayout exterior
        centralCardLayout = new CardLayout();
        centerPanel = new JPanel(centralCardLayout);

        centerPanel.add(scrollPane3,"1");
        centerPanel.add(searchPanel,"2");
        centralCardLayout.show(centerPanel, "1");
        backToMainbtn.addActionListener(e -> centralCardLayout.show(centerPanel, "1"));

        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------


        mainContainer.add(northPanel,"North");
        mainContainer.add(centerPanel,"Center");
        mainContainer.add(eastPanel,"East");
        mainContainer.add(westPanel,"West");

        add(mainContainer);

    }

    /**
     * Método que atualiza a tabela central consoante a playlist que for seleciona e as musicas que contem
     * @param selectedPlaylist é a playlist selecionada que atualiza a tabela central
     */
    public void updateMusicJTableModel(ArrayList<Music> selectedPlaylist){
        centralTableModel.setRowCount(0);
        System.out.println("Playlist size: " + selectedPlaylist.size());
        for(Music ms : selectedPlaylist){
            Vector <Object> line = new Vector<>();
            line.add(ms.getName());
            line.add(ms.getArtistNameFromMusic());
            String albumName;
            if(ms.getAssociatedAlbum() == null) albumName = "Single";
            else albumName = ms.getAssociatedAlbum().getName();
            line.add(albumName);
            line.add(ms.getClassification());
            centralTableModel.addRow(line);
        }
    }

    /**
     * Método que atualiza as musicas na tabela central aquando integração de uma musica nova
     */
    public void updateMusicJListModel(){
        currentUserCollection =guiManager.getCorrentUserMainCollectionClient();
        listModelWest.removeAllElements();
        listModelWest.addElement(currentUserCollection);
        for(MusicCollection cl : guiManager.getUserAllCollection()){
            listModelWest.addElement(cl);
        }
    }

    /**
     * Método que atualiza o cesto consoante se adicionam musicas para compra
     */
    public void updateBasketJListModel(){
        listModelEast.removeAllElements();
        ArrayList<Music> musicsToBuy = guiManager.getListOfMusicsToBuy();
        for(Music m : musicsToBuy){
            listModelEast.addElement(m);
        }
    }
    public void updateBalance(){

        balanceLbl.setText("Balance" +
                "\n" + guiManager.getUserBalance() + "€");
    }
    public double updateTotalBascketPrice(){
        double totalPrice = 0;
        for(Music m : guiManager.getListOfMusicsToBuy()){
            totalPrice += m.getPrice();
        }
        totalLbl.setText("Total " + totalPrice + "€");
        return totalPrice;
    }

    /**
     * Método que atualuza o index do elemento selecionado;
     * @return retorna nulo no caso de a fila ser -1;
     */
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
            ArrayList<Music> music = guiManager.getListOfMusicsToBuy();
            return music.get(row);
        }
        return null;
    }
    public MusicCollection getSelectedPlaylist(){
        int row = playlistListWest.getSelectedIndex()-1;
        if(row != -1){
            ArrayList<MusicCollection> playlist = guiManager.getUserAllCollection();
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
            guiManager.removeMusicFromCollection(selectedMusic,selectedPlaylist);
            updateMusicJTableModel(selectedPlaylist.getMusicList());
            centerPanel.revalidate();
            centerPanel.repaint();
            System.out.println("Music eliminated from playlist");
        }
    }

    /**
     * Método que adicional musica a uma playlist
     * método que se adiciona a um action listener para adicionar uma musica selecionada a uma playlist do utilizador.
     */
    public void addMusicToPlaylistOnClick(){
        JPopupMenu playlistMenu =new JPopupMenu();

        int nOfPlaylists = guiManager.getUserAllCollection().size();

        if(nOfPlaylists == 0){
            JMenuItem emptyList = new JMenuItem("No Playlists to Show");
            playlistMenu.add(emptyList);
        } else{
            for(MusicCollection pl: guiManager.getUserAllCollection()){
                JMenuItem playlistItem  = new JMenuItem(pl.getName());
                playlistItem.addActionListener(e -> {
                    Music selectedMusic = getSelectedMusicOnCentralTable();
                    if(selectedMusic != null){
                        if(!pl.getMusicList().contains(selectedMusic)){
                            if(!selectedMusic.isActive()){
                                JOptionPane.showMessageDialog(null,
                                        "This music is inactive.");
                            }
                            else guiManager.addMusicToCollection(selectedMusic,pl);
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
                    guiManager.evaluateMusic(finalI, selectedMusic);
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
                for(MusicCollection mc: guiManager.getUserAllCollection()){
                    if(mc.getName().equals(playlistName)) nameAlreadyExists = true;
                }
                if(!nameAlreadyExists){
                    guiManager.newCollection(playlistName);
                    MusicCollection newMusicCollection =
                            guiManager.getUserAllCollection().get(guiManager.getUserAllCollection().size()-1);
                    selectedPlaylist = newMusicCollection;
                    updateMusicJListModel();
                    updateMusicJTableModel(newMusicCollection.getMusicList());
                    westPanel.revalidate();
                    westPanel.repaint();
                }else {
                    JOptionPane.showMessageDialog(null,"Playlist name already exists");
                }
            }
        } else if(userChoice == 1){

            Genre.GENRE[] genres = Genre.GENRE.values();
            Genre.GENRE selectedGenre = (Genre.GENRE) JOptionPane.showInputDialog(null,
                    "Chose the genre: ","Genre", JOptionPane.QUESTION_MESSAGE,null, genres,genres[0]);
            if(selectedGenre != null){
                String nMusicsString = JOptionPane.showInputDialog("Type the number of musics");
                int nMusics;
                try {
                    nMusics = Integer.parseInt(nMusicsString);
                    if(nMusics <= 0)  JOptionPane.showMessageDialog(null,"Please insert a valid number");
                    else{
                        guiManager.randomPlaylistCreationAttempt(selectedGenre,nMusics);
                        updateMusicJListModel();
                    }
                } catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Please insert a valid number");
                }
            }
        }
    }

    /**
     *
     */
    public void onPurchasebtnClick(){
        if(updateTotalBascketPrice() > guiManager.getUserBalance()){
            JOptionPane.showMessageDialog(null,"There are not enough money");
        } else if (guiManager.getListOfMusicsToBuy().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Empty list");
        } else {
            int userOption = JOptionPane.showConfirmDialog(null,
                    "Confirm purchase", "Buy",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            switch (userOption){
                case 0:
                    guiManager.validationOfAquisition();
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
        selectedPlaylist = currentUserCollection;
        updateMusicJTableModel(selectedPlaylist.getMusicList());
    }
    public void onAddBalancebtnClick(){
        String moneyToAdd = JOptionPane.showInputDialog("How much do you want to add?");
        double money;
        if(moneyToAdd != null){
            try {
                money = Integer.parseInt(moneyToAdd);
                if(money < 5 || money > 999)  JOptionPane.showMessageDialog(null,"Minimum is 5€\nMaximum is 999€");
                else{
                    guiManager.addMoney(money);
                    updateBalance();
                }
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Please insert a valid number\nHas to be an Integer");
            }
        }
    }

    /**
     *
     */
    public void onDeletePlaylistClick(){
        MusicCollection selected = getSelectedPlaylist();
        if(selected != null){
            int confirmation = JOptionPane.showConfirmDialog(null, "Comfirm the elimination of " + selected.getName());
            if(confirmation == 0){
                guiManager.removeMusicCollection(selected);
                updateMusicJListModel();
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
            updateMusicJListModel();
            westPanel.revalidate();
            westPanel.repaint();
            System.out.println("Playlist changed");
        }
    }

    /**
     * Método de nova pesquisa que altera paineis consoante a pesquisa for por musica ou coleções
     */
    public void newSearch(){
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

    /**
     * Método que atualiza a tabela de pesquisa consoante os termos de musica inseridos
     * @param list lista de musicas presente na coleção de musicas do sistema
     */
    public void updateSearchMusicTable(ArrayList<Music> list){
        searchMusicTableModel.setRowCount(0);
        if(list != null){
            for(Music ms : list){
                Vector <Object> line = new Vector<>();
                line.add(ms.getName());
                line.add(ms.getArtistNameFromMusic());
                String albumName;
                if(ms.getAssociatedAlbum() == null) albumName = "Single";
                else albumName = ms.getAssociatedAlbum().getName();
                line.add(albumName);
                line.add(ms.getClassification());
                searchMusicTableModel.addRow(line);
            }
        }
    }

    /**
     * Método que atualiza a tabela de pesquisa consoante os termos de coleção pesquisados
     */
    public void updateSearchCollectionTable(){
        searchCollectionTableModel.setRowCount(0);
        if(search.getFoundMusicCollections() != null){
            for(MusicCollection mc : search.getFoundMusicCollections()){
                boolean isAlbum = mc instanceof Album;
                Vector <Object> line = new Vector<>();
                line.add(mc.getName());
                if(isAlbum) {
                    line.add("Album");
                    line.add(((Album) mc).getCreator().getName());
                }
                else {
                    line.add("Playlist");
                    line.add(((Playlist) mc).getClientCreator().getName());
                }
                searchCollectionTableModel.addRow(line);
            }
        }
    }

    /**
     * Método para a comboBox de pesquisa com os codigos e casos associados
     */
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
        if(guiManager.getUserAllMusic().contains(selectedMusic)) JOptionPane.showMessageDialog(null,
                "The song was already acquired");
        else if(!selectedMusic.isActive()){
            JOptionPane.showMessageDialog(null,
                    "This music is inactive.");
        } else{
            if(selectedMusic.getPrice() == 0) {
                JOptionPane.showMessageDialog(null,
                        "The song is free. Song acquired, check it on your main collection");
                guiManager.newMusicToAllCollection(selectedMusic);
            }
            else{
                JOptionPane.showMessageDialog(null, "The song costs is " +
                        selectedMusic.getPrice() + "€ . Check it on your shopping basket");
                guiManager.addMusicToMusicToBuy(selectedMusic);
                updateBasketJListModel();
                updateTotalBascketPrice();
            }
        }
    }

    /**
     * Método para ao clicar escolher ver o historico de preços da musica selecionada
     */
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

    /**
     * Método que permite, ao clicar na lista do cesto, verificar o historico de preços das musicas selecionadas
     */
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
        guiManager.getListOfMusicsToBuy().remove(selectedMusic);
        updateBasketJListModel();
        updateTotalBascketPrice();
    }
    public void onCleanBasketClick(){
        int option = JOptionPane.showConfirmDialog(null, "All the elements on the basket will be eliminated, are you sure?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0){
            guiManager.getListOfMusicsToBuy().clear();
            updateBasketJListModel();
            updateTotalBascketPrice();
        }
    }
}