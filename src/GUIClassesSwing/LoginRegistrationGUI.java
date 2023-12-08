package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class LoginRegistrationGUI extends JFrame implements Serializable {
    GUIManager guiManager;
    private JLabel background;
    private JLabel userLoginLbl;
    private JLabel passwordLoginLbl;
    private JButton btnLoginOnMain;
    private JButton btnRegistrationOnMain;
    private JButton btnExitOnMain;
    private JButton loginConfirmationBtn;
    private JButton btnConfirmRegistration;
    private JTextField usernameFieldOnLogin;
    private JPasswordField userPasswordFieldOnLogin;
    private JTextField firstNameOnRegistration;
    private JTextField usernameOnRegistration;
    private JPasswordField userPasswordFieldOnRegistation;
    private JPasswordField userPasswordFieldOnRegistationConf;
    private JTextField emailOnRegistration;
    private JPopupMenu userTypeOnRegistration;
    private JTextField pinOnRegistration;
    private LogRegFrame loginOrRegistFrame;
    private Container loginOrRegistContainer;
    private JPanel loginPanel;
    private JPanel registationPanel;

    public LoginRegistrationGUI(GUIManager guiManager) {
        super("Login and Registration"); // Define o título da janela

        this.guiManager = guiManager;
        // Inicializa os componentes gráficos
        initComponents();

        // Configurações padrão da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        //setCursor(Cursor.getDefaultCursor());
        setVisible(true);
        ImageIcon imageIcon = new ImageIcon("images/headphone.png");
        setIconImage(imageIcon.getImage());

    }
    private void initComponents() {

        //Background image input
        background =  new JLabel(new ImageIcon("images/backGroundImage.png"));

        //Creating Main Container in a BorderLayout style
        Container mainContainer = new Container();
        mainContainer.setLayout(new BorderLayout());

        //Creating Main Btn in a FlowLayout Container
        btnLoginOnMain = new JButton("Login");
        btnRegistrationOnMain = new JButton("Registration");
        btnExitOnMain = new JButton("Exit");
        Container btnContainer = new Container();
        btnContainer.setLayout(new FlowLayout());
        btnContainer.add(btnLoginOnMain);
        btnContainer.add(btnRegistrationOnMain);
        btnContainer.add(btnExitOnMain);

        //Adding both background and btn container
        mainContainer.add(btnContainer,"South");
        mainContainer.add(background);

        btnLoginOnMain.addActionListener(e -> {
            creationOfLoginAndRegistrationFrame(1);
        });

        btnRegistrationOnMain.addActionListener(e -> {
            creationOfLoginAndRegistrationFrame(2);
        });
        //adicionar action listener aqui

        btnExitOnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(mainContainer);
    }
    public void creationOfLoginAndRegistrationFrame(int option){
        loginOrRegistFrame = guiManager.creationLogRegFrame();
        GridBagConstraints constraints = loginOrRegistFrame.getConstraints();
        switch (option){
            case 1:
                loginPanel =  new JPanel(new GridBagLayout());
                userLoginLbl = new JLabel("Username");
                usernameFieldOnLogin = new JTextField("",15);
                passwordLoginLbl = new JLabel("Password");
                userPasswordFieldOnLogin = new JPasswordField("*******", 15);
                loginConfirmationBtn = new JButton("Login");
                loginConfirmationBtn.addActionListener(e -> onLoginConfirmationBtnClick());

                loginPanel.add(userLoginLbl,constraints);
                loginPanel.add(usernameFieldOnLogin,constraints);
                loginPanel.add(passwordLoginLbl,constraints);
                loginPanel.add(userPasswordFieldOnLogin, constraints);
                loginPanel.add(loginConfirmationBtn, constraints);

                loginOrRegistFrame.setContentPane(loginPanel);
                loginOrRegistFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                loginOrRegistFrame.setVisible(true);
                break;
            case 2:
                registationPanel = new JPanel(new GridBagLayout());

                firstNameOnRegistration =  new JTextField("First name",15);
                usernameOnRegistration =  new JTextField("username",15);
                userPasswordFieldOnRegistation = new JPasswordField("password",15);
                userPasswordFieldOnRegistationConf = new JPasswordField("password ",15);
                emailOnRegistration =  new JTextField("Add your email",15);
                userTypeOnRegistration =  new JPopupMenu("Choose your account type");
                pinOnRegistration =  new JTextField(5);
                btnConfirmRegistration = new JButton("Confirm Registration");
                btnConfirmRegistration.addActionListener(e -> onbtnConfirmRegistrationClick());

                registationPanel.add(firstNameOnRegistration,constraints);
                registationPanel.add(usernameOnRegistration,constraints);
                registationPanel.add(userPasswordFieldOnRegistation,constraints);
                registationPanel.add(userPasswordFieldOnRegistationConf,constraints);
                registationPanel.add(emailOnRegistration,constraints);
                registationPanel.add(userTypeOnRegistration,constraints);
                registationPanel.add(pinOnRegistration,constraints);
                registationPanel.add(btnConfirmRegistration,constraints);

                loginOrRegistFrame.setContentPane(registationPanel);
                loginOrRegistFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                loginOrRegistFrame.setVisible(true);
                break;
        }
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
