package src.GUIClassesSwing;

import src.RockStar.RockstarIncManager;

import javax.swing.*;
import java.io.Serializable;

public class GUIManager implements Serializable {
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
    public void login(String userField,String passToString){
        logicManager.login(userField,passToString);
    }
    public void sucessfullLogin(){
        SwingUtilities.invokeLater(() -> {
            new ClientGUI();
            loginRegistrationGUI.setVisible(false);
            loginFrame.dispose();
            if(registrationFrame != null) registrationFrame.dispose();
        });
    }
    public void newUser(String name,String usernameField,String password,String email){
        logicManager.newUser(name, usernameField,password,email);
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
