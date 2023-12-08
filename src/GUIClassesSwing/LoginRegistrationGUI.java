package src.GUIClassesSwing;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class LoginRegistrationGUI extends JFrame implements Serializable {
    GUIManager guiManager;
    private JLabel background;
    private JLabel userLoginLbl;
    private JLabel passwordLoginLbl;
    private JLabel loginPinLbl;
    private JButton btnLoginOnMain;
    private JButton btnRegistrationOnMain;
    private JButton btnExitOnMain;
    private JRadioButton musicCreatorLoginbtn;
    private JButton loginConfirmationBtn;
    private JButton btnConfirmRegistration;
    private JTextField usernameFieldOnLogin;
    private JPasswordField userPasswordFieldOnLogin;
    private JTextField firstNameOnRegistration;
    private JTextField usernameOnRegistration;
    private JTextField loginPinField;
    private JPasswordField userPasswordFieldOnRegistation;
    private JPasswordField userPasswordFieldOnRegistationConf;
    private JTextField emailOnRegistration;
    private JPopupMenu userTypeOnRegistration;
    private JTextField pinOnRegistration;
    private LogRegFrame loginFrame = null;
    private LogRegFrame registrationFrame = null;
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

        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon imageIcon = new ImageIcon(ImagePaths.APP_ICON);
        setIconImage(imageIcon.getImage());

    }
    private void initComponents() {

        //Background image input
        background =  new JLabel(new ImageIcon(ImagePaths.Login_Registration_Background));

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
        btnExitOnMain.addActionListener(e -> System.exit(0)); // nao pode ser system on exit, (fazer metodo update)

        add(mainContainer);
    }
    public void creationOfLoginAndRegistrationFrame(int option){
        //adicionar nota explicativa
        switch (option){
            case 1:
                if(loginFrame == null){
                    loginFrame = guiManager.creationLoginFrame();
                    GridBagConstraints constraints = loginFrame.getConstraints();

                    loginPanel =  new JPanel(new GridBagLayout());
                    userLoginLbl = new JLabel("Username");
                    usernameFieldOnLogin = new JTextField("",15);
                    passwordLoginLbl = new JLabel("Password");
                    userPasswordFieldOnLogin = new JPasswordField("", 15);
                    musicCreatorLoginbtn = new JRadioButton("Music Creator Access");
                    musicCreatorLoginbtn.addActionListener(e -> onRadioMusicCreatorBtnClick());
                    loginPinLbl = new JLabel("PIN");
                    loginPinField = new JTextField("",6);
                    loginConfirmationBtn = new JButton("Login");
                    loginConfirmationBtn.addActionListener(e -> onLoginConfirmationBtnClick());

                    loginPanel.add(userLoginLbl,constraints);
                    loginPanel.add(usernameFieldOnLogin,constraints);
                    loginPanel.add(passwordLoginLbl,constraints);
                    loginPanel.add(userPasswordFieldOnLogin, constraints);
                    loginPanel.add(musicCreatorLoginbtn,constraints);
                    loginPanel.add(loginPinLbl,constraints);
                    loginPanel.add(loginPinField,constraints);
                    loginPanel.add(loginConfirmationBtn, constraints);
                    loginPinLbl.setVisible(false);
                    loginPinField.setVisible(false);

                    loginFrame.setContentPane(loginPanel);
                    loginFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    loginFrame.setVisible(true);
                } else loginFrame.setVisible(true);

                break;
            case 2:
                if(registrationFrame == null){
                    registrationFrame = guiManager.creationRegistrationFrame();
                    GridBagConstraints constraints = registrationFrame.getConstraints();

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

                    registrationFrame.setContentPane(registationPanel);
                    registrationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    registrationFrame.setVisible(true);
                } else registrationFrame.setVisible(true);

                break;
        }
    }
    public void onLoginConfirmationBtnClick(){
        String userField = usernameFieldOnLogin.getText();
        char[] passField = userPasswordFieldOnLogin.getPassword();
        String passToString = new String(passField);
        String pin = loginPinField.getText();
        if(musicCreatorLoginbtn.isSelected()){
            guiManager.loginAttempt(userField,passToString,true,pin);
        } else if (!musicCreatorLoginbtn.isSelected()) {
            guiManager.loginAttempt(userField,passToString,false,pin);
        }
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
        if(!passToString.equals(passToStringConf)) System.out.println("different passwords");
        else{
            guiManager.newUser(name,usernameField,passToString,email);
        }
    };
    public void onRadioMusicCreatorBtnClick(){
        if(musicCreatorLoginbtn.isSelected()){
            loginPinLbl.setVisible(true);
            loginPinField.setVisible(true);
            loginPanel.revalidate();
            loginPanel.repaint();
        } else if (!musicCreatorLoginbtn.isSelected()) {
            loginPinLbl.setVisible(false);
            loginPinField.setVisible(false);
            loginPanel.revalidate();
            loginPanel.repaint();
        }
    }
}
