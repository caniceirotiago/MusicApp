package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class Client extends User implements Serializable {
    private double balance;
    ArrayList<MusicAquisition> listOfAcquisitions;
    ArrayList<BalanceDeposit> listOfBalanceDeposits;
    ArrayList<Music> listOfMusicsToBuy;

    /**
     *
     * @param name
     * @param username
     * @param password
     * @param email
     * @param balance
     */
    public Client(String name, String username, String password, String email, double balance) {
        super(name, username, password, email);
        this.balance = balance;
        this.listOfAcquisitions = new ArrayList<>();
        this.listOfBalanceDeposits = new ArrayList<>();
        this.listOfMusicsToBuy = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Music> getListOfMusicsToBuy() {
        return listOfMusicsToBuy;
    }
    public ArrayList<MusicAquisition> getListOfAcquisitions() {
        return listOfAcquisitions;
    }

    /**
     *
     * @param name
     */
    public void newCollection(String name){
        //Creation of Empty playlist
        Playlist newPlaylist = new Playlist(name, this);
        allCollections.add(newPlaylist);
    }

    /**
     *
     * @param listOfMusic
     */
    public void newCollection(ArrayList<Music> listOfMusic){
        //Creation of collection by random methods
        String genre = listOfMusic.get(0).getGenre().name();
        allCollections.add(new Playlist("Random Playlist - " + genre, this, listOfMusic));
    }

    /**
     *
     * @param music
     */
    public void addMusicToMusicToBuy(Music music){
        if(!listOfMusicsToBuy.contains(music)) listOfMusicsToBuy.add(music);
    }

    /**
     *
     * @param music
     * @param musicCollection
     */
    public void addMusicToCollection(Music music, MusicCollection musicCollection){
        if(allCollections.contains(musicCollection) && music.isActive()) musicCollection.addMusicToCollection(music);
    };

    /**
     *
     * @param music
     */
    public void newMusicToAllMusicCollection(Music music){
        allMusic.add(music);
    }

    /**
     *
     * @param music
     * @param collection
     */
    public void removeMusicFromCollection(Music music, MusicCollection collection){
        collection.removeMusicFromCollection(music);
    };

    /**
     *
     * @param collection
     */
    public void removeMusicCollection(MusicCollection collection){
        allCollections.remove(collection);
    };

    /**
     *
     * @param moneyToAdd
     */
    public void addMoney(double moneyToAdd){
        balance += moneyToAdd;
        listOfBalanceDeposits.add(new BalanceDeposit(moneyToAdd));
    }

    /**
     * 
     * @param musics
     * @return
     */
    public boolean validationOfAquisition(ArrayList<Music> musics){
        double totalPrice = 0;
        for(Music m : musics){
            totalPrice += m.getPrice();
        }
        if(totalPrice <= balance){
            listOfAcquisitions.add(new MusicAquisition(musics));
            balance -= totalPrice;
            allMusic.addAll(musics);
            return true;
        }
        return false; //Se a compra não é efetuada por falta de saldo
    }
}
