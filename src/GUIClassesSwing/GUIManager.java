package src.GUIClassesSwing;

import src.RockStar.RockstarIncManager;

import javax.swing.*;
import java.io.Serializable;

public class GUIManager implements Serializable {
    private LoginRegistrationFrame loginRegistrationFrame;
    private RockstarIncManager logicManager;

    public GUIManager(RockstarIncManager logicManager) {
        this.logicManager = logicManager;
    }
    public void run(){
        loginRegistrationFrame = new LoginRegistrationFrame(GUIManager.this);
    }
    public void login(String userField,String passToString){
        logicManager.login(userField,passToString);
    }
    public void sucessfullLogin(){
        SwingUtilities.invokeLater(() -> {
            loginRegistrationFrame.dispose();
        });
    }
    public void newUser(String name,String usernameField,String password,String email){
        logicManager.newUser(name, usernameField,password,email);
    }
}
