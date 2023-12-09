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
    private JLabel registPinLbl;
    private JLabel lblFirstNameOnReg;
    private JLabel lblUsernameOnReg;
    private JLabel lblPasswordOnReg;
    private JLabel lblPasswordConfOnReg;
    private JLabel lblEmailOnReg;
    private JButton btnLoginOnMain;
    private JButton btnRegistrationOnMain;
    private JButton btnExitOnMain;
    private JRadioButton musicCreatorLoginbtn;
    private JRadioButton musicCreatorRegistbtn;
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
    private JTextField registPinField;
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
                    musicCreatorLoginbtn.addActionListener(e -> onRadioLoginMusicCreatorBtnClick());
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
                    lblFirstNameOnReg = new JLabel("First Name");
                    firstNameOnRegistration =  new JTextField("",15);
                    lblUsernameOnReg = new JLabel("Username");
                    usernameOnRegistration =  new JTextField("",15);
                    lblPasswordOnReg = new JLabel("Password");
                    userPasswordFieldOnRegistation = new JPasswordField("",15);
                    lblPasswordConfOnReg = new JLabel("Password Confirmation");
                    userPasswordFieldOnRegistationConf = new JPasswordField("",15);
                    lblEmailOnReg = new JLabel("Email");
                    emailOnRegistration =  new JTextField("",15);
                    musicCreatorRegistbtn = new JRadioButton("Music Creator Registration");
                    musicCreatorRegistbtn.addActionListener(e -> onRadioRegistMusicCreatorBtnClick());
                    registPinLbl = new JLabel("PIN");
                    registPinField = new JTextField("",6);
                    btnConfirmRegistration = new JButton("Confirm Registration");
                    btnConfirmRegistration.addActionListener(e -> onbtnConfirmRegistrationClick());

                    registationPanel.add(lblFirstNameOnReg,constraints);
                    registationPanel.add(firstNameOnRegistration,constraints);
                    registationPanel.add(lblUsernameOnReg,constraints);
                    registationPanel.add(usernameOnRegistration,constraints);
                    registationPanel.add(lblPasswordOnReg,constraints);
                    registationPanel.add(userPasswordFieldOnRegistation,constraints);
                    registationPanel.add(lblPasswordConfOnReg,constraints);
                    registationPanel.add(userPasswordFieldOnRegistationConf,constraints);
                    registationPanel.add(lblEmailOnReg,constraints);
                    registationPanel.add(emailOnRegistration,constraints);
                    registationPanel.add(musicCreatorRegistbtn,constraints);
                    registationPanel.add(registPinLbl,constraints);
                    registationPanel.add(registPinField,constraints);
                    registationPanel.add(btnConfirmRegistration,constraints);
                    registPinLbl.setVisible(false);
                    registPinField.setVisible(false);

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
        String passToString = new String(passField);
        String passToStringConf = new String(passFieldConfirmation);
        String pin = registPinField.getText();
        if(!passToString.equals(passToStringConf)) {
            System.out.println("different passwords");
            JOptionPane.showMessageDialog(null,"The two passwords are different");
        }
        else{
            if(musicCreatorRegistbtn.isSelected()){
                guiManager.newUserAttempt(name,usernameField,passToString,email,true,pin);
            } else if (!musicCreatorRegistbtn.isSelected()) {
                guiManager.newUserAttempt(name,usernameField,passToString,email, false,pin);
            }
        }

    };
    public void onRadioLoginMusicCreatorBtnClick(){
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
    public void onRadioRegistMusicCreatorBtnClick(){
        if(musicCreatorRegistbtn.isSelected()){
            registPinLbl.setVisible(true);
            registPinField.setVisible(true);
            registationPanel.revalidate();
            registationPanel.repaint();
        } else if (!musicCreatorRegistbtn.isSelected()) {
            registPinLbl.setVisible(false);
            registPinField.setVisible(false);
            registationPanel.revalidate();
            registationPanel.repaint();
        }
    }
}
