package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {
    public ClientGUI(String username){
        super("Client - " + username);
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon(ImagePaths.APP_ICON);
        setIconImage(imageIcon.getImage());
        setMinimumSize(new Dimension(750, 750));
    }
    public void initComponents(){
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        //Criação de Label logotipo
        int newWidth = 100;
        int newHeight = 100;
        ImageIcon originalIcon = new ImageIcon(ImagePaths.APP_ICON);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel logo =  new JLabel(resizedIcon);

        //Label a dizer Playlist
        JLabel playlistLabel =  new JLabel();
        playlistLabel.setText("Playlist");


        //Criação de lista
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Item 1");
        listModel.addElement("Item 2");
        listModel.addElement("Item 3");
        // Adicione mais elementos conforme necessário

        // Cria a JList e define o modelo
        JList<String> myList = new JList<>(listModel);

        // Coloca a JList em um JScrollPane
        JScrollPane scrollPane = new JScrollPane(myList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Criação de Botão para Criar Playlist
        JButton newPlaylistbtn = new JButton("New PLaylist");

        JPanel westPanel = new JPanel(new GridBagLayout());
        westPanel.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.1; // Ajuste este valor para controlar a altura do logo
        c.fill = GridBagConstraints.BOTH;
        westPanel.add(logo, c);

        c.gridy++;
        c.weighty = 0.1; // Ajuste para a label da playlist
        c.anchor = GridBagConstraints.CENTER; // Centraliza horizontalmente
        c.fill = GridBagConstraints.NONE;
        westPanel.add(playlistLabel, c);

        c.gridy++;
        c.weighty = 0.6; // Ajuste para a scrollPane
        c.fill = GridBagConstraints.BOTH;
        westPanel.add(scrollPane, c);

        c.gridy++;
        c.weighty = 0.2; // Ajuste para o botão
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_START;
        westPanel.add(newPlaylistbtn, c);

        mainContainer.add(westPanel,"West");
        add(mainContainer);
    }
}
