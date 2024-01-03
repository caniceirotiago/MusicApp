package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class GUIManager  {
    private ClientGUI clientFrame;
    private MusicCreatorGUIX musicCreatorFrame;
    private LoginRegistrationGUI loginRegistrationGUI;
    private LogRegFrame loginFrame;
    private LogRegFrame registrationFrame;
    private final RockstarIncManager logicManager;

    /**
     *
     * @param logicManager
     */
    public GUIManager(RockstarIncManager logicManager) {
        this.logicManager = logicManager;
    }

    /**
     *
     */
    public void run(){
        loginRegistrationGUI = new LoginRegistrationGUI(GUIManager.this);
    }



    //---------Comunication with logicManager and creation of dialog messages-------

    /**
     *
     * @param userField
     * @param passToString
     * @param isMCreator
     * @param pin
     */
    public void loginAttempt(String userField, String passToString, boolean isMCreator, String pin){
        logicManager.loginAttempt(userField,passToString,isMCreator,pin);
    }
    //Inicia a frame correta no caso de o login ser bem sucedido

    /**
     *
     */
    public void unsuccessfulLogin(){
        JOptionPane.showMessageDialog(null,"Unsuccessful Login");
    };

    /**
     *
     * @param name
     * @param usernameField
     * @param password
     * @param email
     * @param isMCreator
     * @param pin
     */
    public void newUserAttempt(String name,String usernameField,String password,String email,boolean isMCreator, String pin){
        logicManager.newUserAttempt(name, usernameField,password,email, isMCreator, pin);
    }

    /**
     *
     */
    public void successfulRegistration(){
        JOptionPane.showMessageDialog(null,"New User Created");
    }

    /**
     *
     * @param cod
     */
    public void unsuccessfulRegistration(int cod){
        switch (cod){
            case 1 : JOptionPane.showMessageDialog(null,"Unsuccessful Registration - The email already exists");
                break;
            case 2 : JOptionPane.showMessageDialog(null,"Unsuccessful Registration - The username already exists");
                break;
            case 3: JOptionPane.showMessageDialog(null,"That email is not valid");
                break;
            case 4: JOptionPane.showMessageDialog(null, "The username have size requirements (MIN:3 MAX 20) special characters allowed");
                break;
            case 5: JOptionPane.showMessageDialog(null, "The pin is not valid (MIN:4 MAX:8) only digits");
                break;
            case 6: JOptionPane.showMessageDialog(null, "Invalid Name (MIN:3 MAX 20) only letters");
                break;
            case 7: JOptionPane.showMessageDialog(null, "Invalid Password (MIN:3 MAX 20)");
                break;
        }

    }

    /**
     *
     * @param selectedGenre
     * @param nMusics
     */
    public void randomPlaylistCreationAttempt(Genre.GENRE selectedGenre,int nMusics){
        logicManager.newRandomPlaylistAttempt(selectedGenre,nMusics);
    }

    /**
     *
     * @param maxSize
     * @param freeMusics
     */
    public void notEnoughMusicForRandom(int maxSize,boolean freeMusics){
        String freeMusicsString = "";
        if(freeMusics) freeMusicsString = "free";
        JOptionPane.showMessageDialog(null,"Not enough musics for this random playlist " +
                "\nOn the selected genre there are only " + maxSize + " " + freeMusicsString + " musics available");
    }



    //---------------------------------Frame and JDialog management--------------------------------

    /**
     *
     * @return
     */
    public LogRegFrame creationLoginFrame(){
        LogRegFrame lf = new LogRegFrame();
        this.loginFrame = lf;
        return lf;
    }

    /**
     *
     * @return
     */
    public LogRegFrame creationRegistrationFrame(){
        LogRegFrame rf = new LogRegFrame();
        this.registrationFrame = rf;
        return rf;
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void logoutClient() throws IOException, ClassNotFoundException {
        clientFrame.dispose();
        logicManager.logout();
        run();
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void logoutMCreator()throws IOException, ClassNotFoundException{
        musicCreatorFrame.dispose();
        logicManager.logout();
        run();
    }

    /**
     *
     * @param username
     * @param isMCreator
     */
    public void sucessfullLogin(String username, boolean isMCreator){
        if(isMCreator){
            musicCreatorFrame = new MusicCreatorGUIX(username, this);
            loginRegistrationGUI.setVisible(false);
            loginFrame.dispose();
            if(registrationFrame != null) registrationFrame.dispose();
        } else {
            clientFrame = new ClientGUI(username,this);
            loginRegistrationGUI.setVisible(false);
            loginFrame.dispose();
            if(registrationFrame != null) registrationFrame.dispose();
        }
    }

    /**
     *
     * @param notFreeMusicSelection
     * @param totalPrice
     * @param canBuy
     * @return
     */
    public int randomPlaylistToPaySongsChoose(ArrayList<Music> notFreeMusicSelection, double totalPrice, boolean canBuy) {
        RandonPlaylistPay rpp = new RandonPlaylistPay(this, clientFrame, notFreeMusicSelection,totalPrice,canBuy);
        int userOption = rpp.getReturnValue();
        System.out.println(userOption);
        return userOption;
    }

    /**
     *
     */
    public void randomPLSuccssefullyCreated(){ //erro
        JOptionPane.showMessageDialog(null,"Random playlist created");
        clientFrame.updateMusicJTableModel(logicManager.getCurrentUserALlMusic());
        clientFrame.updateBasketJListModel();
        clientFrame.updateBalance();
        clientFrame.updateTotalBascketPrice();
    }

    /**
     *
     * @param searchTextField
     * @return
     */
    public Search newSearch(String searchTextField){
        return logicManager.search(searchTextField);
    }

    /**
     *
     * @return
     */
    public ArrayList<MusicCollection> getUserAllCollection(){
        return logicManager.getCurretUserAllCollections();
    }

    /**
     *
     * @return
     */
    public double getUserBalance(){
        return logicManager.getCurrentUserBalance();
    }

    /**
     *
     * @return
     */
    public ArrayList<Music> getUserAllMusic(){
        return logicManager.getCurrentUserALlMusic();
    }

    /**
     *
     * @return
     */
    public Playlist getCorrentUserMainCollectionClient(){
        return logicManager.getClientAllMusicAsCollection();
    }

    /**
     *
     * @return
     */
    public Album getCorrentUserMainCollectionMusicCreator(){
        return logicManager.getMusicCreatorAllMusicAsCollection();
    }

    /**
     *
     * @return
     */
    public ArrayList<Music> getListOfMusicsToBuy(){
        return logicManager.getUserBasketList();
    }

    /**
     *
     * @param selectedMusic
     * @param selectedPlaylist
     */
    public void removeMusicFromCollection(Music selectedMusic,MusicCollection selectedPlaylist){
        logicManager.removeMusicFromCollection(selectedMusic,selectedPlaylist);
    }

    /**
     *
     * @param selectedMusic
     * @param cl
     */
    public void addMusicToCollection(Music selectedMusic,MusicCollection cl){
        logicManager.addMusicToCollection(selectedMusic,cl);
    }

    /**
     *
     * @param evaluation
     * @param selectedMusic
     */
    public void evaluateMusic(int evaluation, Music selectedMusic){
        logicManager.evaluateMusic(evaluation, selectedMusic);
    }

    /**
     *
     * @param playlistName
     */
    public void newCollection(String playlistName){
       logicManager.newCollection(playlistName);
    }

    /**
     *
     */
    public void validationOfAquisition(){
        logicManager.validationOfAquisition();
    }

    /**
     *
     * @param money
     */
    public void addMoney(double money){
        logicManager.addMoney(money);
    }

    /**
     *
     * @param selected
     */
    public void removeMusicCollection(MusicCollection selected){
        logicManager.removeMusicCollection(selected);
    }

    /**
     *
     * @param selectedMusic
     */
    public void newMusicToAllCollection(Music selectedMusic){
        logicManager.newMusicToAllCollection(selectedMusic);
    }

    /**
     *
     * @param selectedMusic
     */
    public void addMusicToMusicToBuy(Music selectedMusic){
        logicManager.addMusicToMusicToBuy(selectedMusic);
    }

    /**
     *
     * @param musicNameTextField
     * @param priceTextField
     * @param selectedGender
     */
    public void newMusicAttempt(String musicNameTextField, String priceTextField, Genre.GENRE selectedGender){
        logicManager.newMusic(musicNameTextField, priceTextField, selectedGender);
    }

    /**
     *
     * @param errorN
     */
    public void musicAttemptError(int errorN){
        switch (errorN){
            case 0:
                JOptionPane.showMessageDialog(null,"Price format error");
                break;
            case 1:
                JOptionPane.showMessageDialog(null,"Music name should have 1 - 20 characters");
                break;
            case 2:
                JOptionPane.showMessageDialog(null,"Music price should be between 0€ and 50€");
                break;
            case 3:
                JOptionPane.showMessageDialog(null,"You already have created a music with this name");
        }
    }

    /**
     *
     */
    public void newMusicCreated(){
        JOptionPane.showMessageDialog(null,"New Music Created");
        musicCreatorFrame.updateMusicJTableModel(getCorrentUserMainCollectionMusicCreator().getMusicList());
        musicCreatorFrame.updateFirstStatsPanel(getStatistics());
    }

    /**
     *
     * @param selectedMusic
     */
    public void editMusicDialogCall(Music selectedMusic){
        EditMusic editMusic = new EditMusic(this, musicCreatorFrame, selectedMusic);
        String name = editMusic.getNewName();
        String price = editMusic.getNewPrice();
        Genre.GENRE genre = editMusic.getSelectedGender();
        int state = editMusic.getMusicState();
        logicManager.musicEditionAttempt(selectedMusic,name, price, genre, state);
    }

    /**
     *
     */
    public void musicSuccessfullyEdited(){
        JOptionPane.showMessageDialog(null,"Music Successfully Edited");
    }

    /**
     *
     * @return
     */
    public ArrayList<Double> getStatistics(){
        return logicManager.getStatistics();
    }
}
