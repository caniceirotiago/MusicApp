package src.GUIClassesSwing;

import src.RockStar.RockstarIncManager;

import javax.swing.*;
import java.io.Serializable;

public class GUIManager implements Serializable {
    private ClientGUI clientFrame;
    private LoginRegistrationGUI loginRegistrationGUI;
    private LogRegFrame logRegFrame;
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
            logRegFrame.dispose();
        });
    }
    public void newUser(String name,String usernameField,String password,String email){
        logicManager.newUser(name, usernameField,password,email);
    }
    public LogRegFrame creationLogRegFrame(){
        LogRegFrame lrf = new LogRegFrame();
        this.logRegFrame = lrf;
        return lrf;
    }
}
