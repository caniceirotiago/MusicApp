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
    private JLabel background;
    private Container c;

    public LoginRegistrationFrame(RockstarIncManager manager) {
        super("Login and Registration"); // Define o título da janela

        this.manager = manager;
        // Inicializa os componentes gráficos
        initComponents();

        // Configurações padrão da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setCursor(Cursor.getDefaultCursor());
        setVisible(true);

    }
    private void initComponents() {
        // Layout da janela
        c = new Container();
        c = getContentPane();
        pack();

        //botao login
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //fazer o novo painel
                JPanel loginPanel =  new JPanel(new GridBagLayout());
                GridBagConstraints constraints =  new GridBagConstraints();
                constraints.gridx= GridBagConstraints.REMAINDER;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.gridwidth = GridBagConstraints.REMAINDER;
                background =  new JLabel(new ImageIcon("/Users/utilizador/Desktop/Acertar o Rumo/Project/imageLogin.jpeg"));
                background.setLayout(new BorderLayout());
                //elementos para meter no novo painel
                JTextField userName = new JTextField("Set your username",15);
                JPasswordField userPass = new JPasswordField(15);
                JButton userLogin = new JButton("login");
                userLogin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Esta a funcionar");
                    }
                });
                JButton userGoBack = new JButton("Go Back");
                userGoBack.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getContentPane().remove(loginPanel);
                        setContentPane(c);
                        revalidate();
                        repaint();
                    }
                });
                //adicional estes elementos ao painel
                loginPanel.add(userName,constraints);
                loginPanel.add(userPass, constraints);
                loginPanel.add(userLogin, constraints);
                loginPanel.add(userGoBack, constraints);
                loginPanel.add(background,constraints);
                getContentPane().remove(c);
                setContentPane(loginPanel);
                revalidate();
                repaint();
                loginPanel.setVisible(true);
            }
        });
        //adicionar o action listener aqui ao botao;
        registo = new JButton("registo");
        registo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel registoPanel = new JPanel(new GridBagLayout());
                GridBagConstraints constraints =  new GridBagConstraints();
                constraints.gridx= GridBagConstraints.REMAINDER;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.gridwidth = GridBagConstraints.REMAINDER;
                background =  new JLabel(new ImageIcon("/Users/utilizador/Desktop/Acertar o Rumo/Project/imageRegisto.jpeg"));
                background.setLayout(new BorderLayout());

                //atributos deste painel
                JTextField firstName =  new JTextField("First name",15);
                JTextField lastName =  new JTextField(15);
                JTextField email =  new JTextField("Add your email",15);
                JPopupMenu userType =  new JPopupMenu("Choose your account type");
                JTextField pin =  new JTextField(5);
                JButton goToLoginPage = new JButton("Go to login");
                goToLoginPage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getContentPane().remove(registoPanel);
                        setContentPane(c);//aqui falta colocar a opcao de ir diretamente para o login e vice versa
                        //problema é que o panel do login esta fora de scope e nao sei como o ir buscar;
                        revalidate();
                        repaint();
                    }
                });
                JButton goToMainPage = new JButton("Go to main page");
                goToMainPage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getContentPane().remove(registoPanel);
                        setContentPane(c);
                        revalidate();
                        repaint();
                    }
                });
                registoPanel.add(firstName,constraints);
                registoPanel.add(lastName,constraints);
                registoPanel.add(email,constraints);
                registoPanel.add(userType,constraints);
                registoPanel.add(pin,constraints);
                registoPanel.add(goToLoginPage,constraints);
                registoPanel.add(goToMainPage,constraints);
                registoPanel.add(background,constraints);
                getContentPane().remove(c);
                setContentPane(registoPanel);
                revalidate();
                repaint();
            }
        });
        //adicionar action listener aqui
        sair = new JButton("Sair");
        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //adicionar action listener aqui


        //imagem de fundo do painel da app
        background =  new JLabel(new ImageIcon("/Users/utilizador/Desktop/Acertar o Rumo/Project/backGroundImage.png"));//adicionar ficheiro de imagem );
        background.setLayout(new BorderLayout());
        c.add(background,BorderLayout.CENTER);

        //painel botoes
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(login);
        buttonsPanel.add(registo);
        buttonsPanel.add(sair);
        c.add(buttonsPanel, BorderLayout.SOUTH);
    }
}
