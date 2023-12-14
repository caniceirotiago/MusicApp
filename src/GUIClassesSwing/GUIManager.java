package src.GUIClassesSwing;

import src.RockStar.RockstarIncManager;
import src.RockStar.User;

import javax.swing.*;
import java.io.Serializable;

public class GUIManager implements Serializable { //O Serializable não deveria ficar aqui

    private ClientGUI clientFrame;
    private LoginRegistrationGUI loginRegistrationGUI;
    private LogRegFrame loginFrame;
    private LogRegFrame registrationFrame;
    private RockstarIncManager logicManager;
    private User currentUser;

    public GUIManager(RockstarIncManager logicManager) {
        this.logicManager = logicManager;
    }
    public void run(){
        loginRegistrationGUI = new LoginRegistrationGUI(GUIManager.this);
    }
    //Passa a lógica da tentativa de login para a classe lógica
    public void loginAttempt(String userField, String passToString, boolean isMCreator, String pin){
        logicManager.loginAttempt(userField,passToString,isMCreator,pin);
    }
    //Inicia a frame correta no caso de o login ser bem sucedido
    public void sucessfullLogin(User currentUser, boolean isMCreator){
        this.currentUser = currentUser;
        if(isMCreator){
            SwingUtilities.invokeLater(() -> {
                new MusicCreatorGUI(currentUser);
                loginRegistrationGUI.setVisible(false);
                loginFrame.dispose();
                if(registrationFrame != null) registrationFrame.dispose();
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                clientFrame = new ClientGUI(currentUser,this);
                loginRegistrationGUI.setVisible(false);
                loginFrame.dispose();
                if(registrationFrame != null) registrationFrame.dispose();
            });
        }
    }
    //Caixa de diálogo em caso de login sem sucesso
    public void unsuccessfulLogin(){
        JOptionPane.showMessageDialog(null,"Unsuccessful Login");
    };
    public void newUserAttempt(String name,String usernameField,String password,String email,boolean isMCreator, String pin){
        logicManager.newUserAttempt(name, usernameField,password,email, isMCreator, pin);
    }
    public void successfulRegistration(){
        JOptionPane.showMessageDialog(null,"New User Created");
    }
    public void unsuccessfulRegistration(int cod){
        switch (cod){
            case 1 : JOptionPane.showMessageDialog(null,"Unsuccessful Registration - The email already exists");
                break;
            case 2 : JOptionPane.showMessageDialog(null,"Unsuccessful Registration - The username already exists");
                break;
                //switches de validacao
            case 3: JOptionPane.showMessageDialog(null,"That email is not valid");
                break;
            case 4: JOptionPane.showMessageDialog(null, "The username does not have the size requirements (MIN:3 MAX 10)");
            break;
            case 5: JOptionPane.showMessageDialog(null, "The pin is not valid (MIN:4 MAX:8)");
            break;
        }

    }
    public void logoutClient(){
        clientFrame.dispose();
        currentUser = null;
        run();
    }
    public LogRegFrame creationLoginFrame(){
        LogRegFrame lf = new LogRegFrame();
        this.loginFrame = lf;
        return lf;
    }
    public LogRegFrame creationRegistrationFrame(){
        LogRegFrame rf = new LogRegFrame();
        this.registrationFrame = rf;
        return rf;
    }
}
