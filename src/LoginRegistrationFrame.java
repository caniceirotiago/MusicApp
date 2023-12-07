package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class LoginRegistrationFrame extends JFrame implements Serializable {
    private JButton login;
    private JButton registo;
    private JButton sair;
    private RockstarIncManager manager;
    JLabel background;
    Container c;
    public LoginRegistrationFrame(RockstarIncManager manager) {
        super("Login and Registration"); // Define o título da janela

        this.manager = manager;

        // Inicializa os componentes gráficos
        initComponents();

        // Configurações padrão da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);
        setCursor(Cursor.getDefaultCursor());
        setVisible(true);
    }
    private void initComponents() {
        // Layout da janela
        setLayout(new BorderLayout());
        login = new JButton("Login");
        registo = new JButton("registo");
        sair = new JButton("Sair");

        background =  new JLabel(new ImageIcon("C:\\Users\\canic\\Desktop\\cao.png"));
        background.setLayout(new BorderLayout());
        c = new Container();
        c = getContentPane();
        c.add(background,BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(login);
        buttonsPanel.add(registo);
        buttonsPanel.add(sair);
        c.add(buttonsPanel, BorderLayout.SOUTH);
    }
}
