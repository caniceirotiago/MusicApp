package src.GUIClassesSwing;

import src.RockStar.Client;
import src.RockStar.Music;
import src.RockStar.RockstarIncManager;
import src.RockStar.User;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static src.RockStar.Main.updateDataFile;

public class GUIManager { //O Serializable não deveria ficar aqui

    private ClientGUI clientFrame;
    private MusicCreatorGUI musicCreatorFrame;
    private LoginRegistrationGUI loginRegistrationGUI;
    private LogRegFrame loginFrame;
    private LogRegFrame registrationFrame;
    private final RockstarIncManager logicManager;
    private User currentUser;

    public GUIManager(RockstarIncManager logicManager) {
        this.logicManager = logicManager;
    }
    public void run(){
        loginRegistrationGUI = new LoginRegistrationGUI(GUIManager.this);
    }



    //---------Comunication with logicManager and creation of dialog messages-------

    public void loginAttempt(String userField, String passToString, boolean isMCreator, String pin){
        logicManager.loginAttempt(userField,passToString,isMCreator,pin);
    }
    //Inicia a frame correta no caso de o login ser bem sucedido
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
    public void randomPlaylistCreationAttempt(RockstarIncManager.GENRE selectedGenre,int nMusics){
        logicManager.newRandomPlaylist(selectedGenre,nMusics);
    }
    public void notEnoughMusicForRandom(int maxSize,boolean freeMusics){
        String freeMusicsString = "";
        if(freeMusics) freeMusicsString = "free";
        JOptionPane.showMessageDialog(null,"Not enough musics for this random playlist " +
                "\nOn the selected genre there are only " + maxSize + " " + freeMusicsString + " musics available");
    }



    //---------------------------------Frame and JDialog management--------------------------------

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
    public void logoutClient() throws IOException, ClassNotFoundException {
        clientFrame.dispose();
        currentUser = null;
        updateDataFile();
        run();
    }
    public void logoutMCreator()throws IOException, ClassNotFoundException{
        musicCreatorFrame.dispose();
        currentUser = null;
        updateDataFile();
        run();

    }
    public void sucessfullLogin(User currentUser, boolean isMCreator){
        this.currentUser = currentUser;
        if(isMCreator){
            musicCreatorFrame = new MusicCreatorGUI(currentUser, this);
            loginRegistrationGUI.setVisible(false);
            loginFrame.dispose();
            if(registrationFrame != null) registrationFrame.dispose();
        } else {
            clientFrame = new ClientGUI(currentUser,this);
            loginRegistrationGUI.setVisible(false);
            loginFrame.dispose();
            if(registrationFrame != null) registrationFrame.dispose();
        }
    }
    public int randomPlaylistPaidSongsChoose(ArrayList<String> notFreeMusicSelection, double totalPrice,boolean canBuy) {
        RandonPlaylistPay rpp = new RandonPlaylistPay(this, clientFrame, notFreeMusicSelection,totalPrice,canBuy);
        int userOption = rpp.getReturnValue();
        System.out.println(userOption);
        return userOption;
    }
    public void randomPLSuccssefullyCreated(){ //erro
        JOptionPane.showMessageDialog(null,"Random playlist created");
        clientFrame.updateMusicJTableModel(currentUser.getAllMusic());
        clientFrame.revalidate();
        clientFrame.repaint();
    }
}
