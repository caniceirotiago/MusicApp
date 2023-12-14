package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ClientGUI extends JFrame {
    public ClientGUI(String username){
        super("Client - " + username);
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon(ImagePaths.APP_ICON);
        setIconImage(imageIcon.getImage());
        setMinimumSize(new Dimension(1100, 950));
    }
    public void initComponents(){
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());




        //---------------------PAINEL WEST---------------------------------



        //Label a dizer Playlist
        JLabel playlistLabel =  new JLabel();
        playlistLabel.setText("Playlist");


        //Criação de lista
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Item 1");
        listModel.addElement("Item 2");
        listModel.addElement("Item 3");
        listModel.addElement("Item 3");


        // Cria a JList e define o modelo
        JList<String> playlistList = new JList<>(listModel);

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

        // Nomes das colunas
        String[] columnNames = {"Column 1", "Column 2", "Column 3"};

        // Dados da tabela (substitua com os seus dados)
        Object[][] data = {
                {"Data 1-1", "Data 1-2", "Data 1-3"},
                {"Data 2-1", "Data 2-2", "Data 2-3"},
                // ... mais linhas de dados ...
        };

        // Criação do modelo da tabela e da tabela
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);

        // Colocando a tabela em um JScrollPane
        JScrollPane scrollPane3 = new JScrollPane(table);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel centerPanel = new JPanel();
        centerPanel.add(scrollPane3);



        //Adding all panels
        mainContainer.add(northPanel,"North");
        mainContainer.add(centerPanel,"Center");
        mainContainer.add(eastPanel,"East");
        mainContainer.add(westPanel,"West");

        add(mainContainer);
    }
}
