package src.GUIClassesSwing;

import src.RockStar.RockstarIncManager;

import javax.swing.*;
import java.io.Serializable;

public class GUIManager implements Serializable { //O Serializable não deveria ficar aqui

    private ClientGUI clientFrame;
    private LoginRegistrationGUI loginRegistrationGUI;
    private LogRegFrame loginFrame;
    private LogRegFrame registrationFrame;
    private RockstarIncManager logicManager;

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
    public void sucessfullLogin(String username, boolean isMCreator){
        if(isMCreator){
            SwingUtilities.invokeLater(() -> {
                new MusicCreatorGUI(username);
                loginRegistrationGUI.setVisible(false);
                loginFrame.dispose();
                if(registrationFrame != null) registrationFrame.dispose();
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                new ClientGUI(username);
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
        }

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
