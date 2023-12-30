package src.GUIClassesSwing;

import src.RockStar.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import static src.RockStar.Main.updateDataFile;

/**
 *
 */
public class GUIManager {
    private ClientGUI clientFrame;
    private MusicCreatorGUIX musicCreatorFrame;
    private LoginRegistrationGUI loginRegistrationGUI;
    private LogRegFrame loginFrame;
    private LogRegFrame registrationFrame;
    private final RockstarIncManager logicManager;
    private User currentUser;

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
        logicManager.newRandomPlaylist(selectedGenre,nMusics);
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
        currentUser = null;
        updateDataFile();
        run();
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void logoutMCreator()throws IOException, ClassNotFoundException{
        musicCreatorFrame.dispose();
        currentUser = null;
        updateDataFile();
        run();

    }

    /**
     *
     * @param currentUser
     * @param isMCreator
     */
    public void sucessfullLogin(User currentUser, boolean isMCreator){
        this.currentUser = currentUser;
        if(isMCreator){
            musicCreatorFrame = new MusicCreatorGUIX(currentUser.getUsername(), this);
            loginRegistrationGUI.setVisible(false);
            loginFrame.dispose();
            if(registrationFrame != null) registrationFrame.dispose();
        } else {
            clientFrame = new ClientGUI(currentUser.getUsername(),this);
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
    public int randomPlaylistPaidSongsChoose(ArrayList<Music> notFreeMusicSelection, double totalPrice,boolean canBuy) {
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
        clientFrame.updateMusicJTableModel(currentUser.getAllMusic());
        clientFrame.updateBasketJListModel();
        clientFrame.updateBalance();
        clientFrame.updateTotalBascketPrice();
        //clientFrame.revalidate();
        //clientFrame.repaint();
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
        return currentUser.getAllCollections();
    }

    /**
     *
     * @return
     */
    public double getUserBalance(){
        return ((Client)currentUser).getBalance();
    }

    /**
     *
     * @return
     */
    public ArrayList<Music> getUserAllMusic(){
        return currentUser.getAllMusic();
    }

    /**
     *
     * @return
     */
    public Playlist getCorrentUserMainCollectionClient(){
        return new Playlist("Owned Music",(Client) currentUser,currentUser.getAllMusic());
    }

    /**
     *
     * @return
     */
    public Album getCorrentUserMainCollectionMusicCreator(){
        return new Album("Created Music",(MusicCreator) currentUser,currentUser.getAllMusic());
    }

    /**
     *
     * @return
     */
    public ArrayList<Music> getListOfMusicsToBuy(){
        return ((Client)currentUser).getListOfMusicsToBuy();
    }

    /**
     *
     * @param selectedMusic
     * @param selectedPlaylist
     */
    public void removeMusicFromCollection(Music selectedMusic,MusicCollection selectedPlaylist){
        currentUser.removeMusicFromCollection(selectedMusic,selectedPlaylist);
    }

    /**
     *
     * @param selectedMusic
     * @param cl
     */
    public void addMusicToCollection(Music selectedMusic,MusicCollection cl){
        currentUser.addMusicToCollection(selectedMusic,cl);
    }

    /**
     *
     * @param evaluation
     * @param selectedMusic
     */
    public void evaluateMusic(int evaluation, Music selectedMusic){
        selectedMusic.addEvaluation((Client)currentUser, evaluation);
    }

    /**
     *
     * @param playlistName
     */
    public void newCollection(String playlistName){
        currentUser.newCollection(playlistName);
    }

    /**
     *
     */
    public void validationOfAquisition(){
        ((Client)currentUser).validationOfAquisition(getListOfMusicsToBuy());
        ((Client)currentUser).getListOfMusicsToBuy().clear();
    }

    /**
     *
     * @param money
     */
    public void addMoney(double money){
        ((Client)currentUser).addMoney(money);
    }

    /**
     *
     * @param selected
     */
    public void removeMusicCollection(MusicCollection selected){
        currentUser.removeMusicCollection(selected);
    }

    /**
     *
     * @param selectedMusic
     */
    public void newMusicToAllCollection(Music selectedMusic){
        currentUser.newMusicToAllMusicCollection(selectedMusic);
    }

    /**
     *
     * @param selectedMusic
     */
    public void addMusicToMusicToBuy(Music selectedMusic){
        ((Client)currentUser).addMusicToMusicToBuy(selectedMusic);
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
    public ArrayList<Integer> getStatistics(){
        ArrayList<Integer> overallStatistics =  new ArrayList<>();

        overallStatistics.add(logicManager.totalUsers());
        overallStatistics.add(logicManager.totalSongs());
        overallStatistics.add((int)Math.round(logicManager.musicTotalPriceValue()));
        overallStatistics.add((int)Math.round(logicManager.totalSalesValue()));

        ArrayList<Integer> albumCountByGenre = new ArrayList<>();
        for(Genre.GENRE ge : Genre.GENRE.values()){
            albumCountByGenre.add(logicManager.totalAlbumsByGenre(ge));
        }
        albumCountByGenre.add(logicManager.totalAlbumsByGenre(null));

        int totalAlbuns = 0;
        for(Integer i: albumCountByGenre){
            totalAlbuns += i;
        }
        overallStatistics.add(totalAlbuns);
        overallStatistics.addAll(albumCountByGenre);
        return overallStatistics;
    }
}
