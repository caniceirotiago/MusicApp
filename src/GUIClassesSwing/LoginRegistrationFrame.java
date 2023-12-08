package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class LoginRegistrationFrame extends JFrame implements Serializable {
    GUIManager guiManager;
    private JButton btnLoginOnMain;
    private JButton btnRegistrationOnMain;
    private JButton btnExitOnMain;
    private JLabel background;
    private Container c;
    private JTextField usernameFieldOnLogin;
    private JPasswordField userPasswordFieldOnLogin;
    private JTextField firstNameOnRegistration;
    private JTextField usernameOnRegistration;
    private JPasswordField userPasswordFieldOnRegistation;
    private JPasswordField userPasswordFieldOnRegistationConf;
    private JTextField emailOnRegistration;
    private JPopupMenu userTypeOnRegistration;
    private JTextField pinOnRegistration;

    public LoginRegistrationFrame(GUIManager guiManager) {
        super("Login and Registration"); // Define o título da janela

        this.guiManager = guiManager;
        // Inicializa os componentes gráficos
        initComponents();

        // Configurações padrão da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
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
        btnLoginOnMain = new JButton("Login");
        btnLoginOnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //fazer o novo painel
                JPanel loginPanel =  new JPanel(new GridBagLayout());
                GridBagConstraints constraints =  new GridBagConstraints();
                constraints.gridx= GridBagConstraints.REMAINDER;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.gridwidth = GridBagConstraints.REMAINDER;
                background =  new JLabel(new ImageIcon("imageLogin.jpeg"));
                background.setLayout(new BorderLayout());

                //elementos para meter no novo painel
                usernameFieldOnLogin = new JTextField("Set your username",15);
                userPasswordFieldOnLogin = new JPasswordField(15);
                JButton loginConfirmationBtn = new JButton("Login");
                loginConfirmationBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onLoginConfirmationBtnClick();
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
                loginPanel.add(usernameFieldOnLogin,constraints);
                loginPanel.add(userPasswordFieldOnLogin, constraints);
                loginPanel.add(loginConfirmationBtn, constraints);
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
        btnRegistrationOnMain = new JButton("registo");
        btnRegistrationOnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel registoPanel = new JPanel(new GridBagLayout());
                GridBagConstraints constraints =  new GridBagConstraints();
                constraints.gridx= GridBagConstraints.REMAINDER;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.gridwidth = GridBagConstraints.REMAINDER;
                background =  new JLabel(new ImageIcon("imageRegisto.jpeg"));
                background.setLayout(new BorderLayout());

                //atributos deste painel
                firstNameOnRegistration =  new JTextField("First name",15);
                usernameOnRegistration =  new JTextField("username",15);
                userPasswordFieldOnRegistation = new JPasswordField("password",15);
                userPasswordFieldOnRegistationConf = new JPasswordField("password ",15);
                emailOnRegistration =  new JTextField("Add your email",15);
                userTypeOnRegistration =  new JPopupMenu("Choose your account type");
                pinOnRegistration =  new JTextField(5);
                JButton btnConfirmRegistration = new JButton("Confirm Registration");
                btnConfirmRegistration.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onbtnConfirmRegistrationClick();
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
                registoPanel.add(firstNameOnRegistration,constraints);
                registoPanel.add(usernameOnRegistration,constraints);
                registoPanel.add(userPasswordFieldOnRegistation,constraints);
                registoPanel.add(userPasswordFieldOnRegistationConf,constraints);
                registoPanel.add(emailOnRegistration,constraints);
                registoPanel.add(userTypeOnRegistration,constraints);
                registoPanel.add(pinOnRegistration,constraints);
                registoPanel.add(btnConfirmRegistration,constraints);
                registoPanel.add(goToMainPage,constraints);
                registoPanel.add(background,constraints);
                getContentPane().remove(c);
                setContentPane(registoPanel);
                revalidate();
                repaint();
            }
        });
        //adicionar action listener aqui
        btnExitOnMain = new JButton("Sair");
        btnExitOnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //adicionar action listener aqui


        //imagem de fundo do painel da app
        background =  new JLabel(new ImageIcon("backGroundImage.png"));//adicionar ficheiro de imagem );
        background.setLayout(new BorderLayout());
        c.add(background,BorderLayout.CENTER);

        //painel botoes
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(btnLoginOnMain);
        buttonsPanel.add(btnRegistrationOnMain);
        buttonsPanel.add(btnExitOnMain);
        c.add(buttonsPanel, BorderLayout.SOUTH);
    }
    public void onLoginConfirmationBtnClick(){
        String userField = usernameFieldOnLogin.getText();
        char[] passField = userPasswordFieldOnLogin.getPassword();
        String passToString = new String(passField);
        guiManager.login(userField,passToString);
    }
    public void onbtnConfirmRegistrationClick(){
        String name = firstNameOnRegistration.getText();
        String usernameField = usernameOnRegistration.getText();
        char[] passField = userPasswordFieldOnRegistation.getPassword();
        char[] passFieldConfirmation = userPasswordFieldOnRegistationConf.getPassword();
        String email = emailOnRegistration.getText();

        //falta o tipo de user para já apenas regista client

        String passToString = new String(passField);
        String passToStringConf = new String(passFieldConfirmation);
        if(!passToString.equals(passToStringConf)) System.out.println("diferent passwords");
        else{
            guiManager.newUser(name,usernameField,passToString,email);
        }
    };
}
