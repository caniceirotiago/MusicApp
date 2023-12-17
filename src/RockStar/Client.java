package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

public class Client extends User implements Serializable {
    private double balance = 0;
    ArrayList<MusicAquisition> listOfAcquisitions;
    ArrayList<BalanceDeposit> listOfBalanceDeposits;
    ArrayList<Music> listOfMusicsToBuy;

    public Client(String name, String username, String password, String email, double balance) {
        super(name, username, password, email);
        this.balance = balance;
        this.listOfAcquisitions = new ArrayList<>();
        this.listOfBalanceDeposits = new ArrayList<>();
        this.listOfMusicsToBuy = new ArrayList<>();
    }
    public void addMusicToMusicToBuy(Music music){
        if(!listOfMusicsToBuy.contains(music))listOfMusicsToBuy.add(music);
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Music> getListOfMusicsToBuy() {
        return listOfMusicsToBuy;
    }

    public void newCollection(String name){
        //Creation of Empty playlist
        Playlist newPlaylist = new Playlist(name, this);
        allCollections.add(newPlaylist);
    };
    public void newCollection(ArrayList<Music> listOfMusic){
        //Creation of collection by random methods
        String genre = listOfMusic.get(0).getGenre().name();
        allCollections.add(new Playlist("Random Playlist - " + genre, this, listOfMusic));
    };
    public ArrayList<MusicCollection> seeAllCollection(){
        return new ArrayList<>();
    };
    public void addMusicToCollection(Music music, MusicCollection musicCollection){
        musicCollection.addMusicToCollection(music);
    };
    public void newMusicToAllCollection(Music music){
        allMusic.add(music);
    }
    public void removeMusicFromCollection(Music music, MusicCollection collection){
        collection.getMusicList().remove(music);
    };
    public void addMoney(double moneyToAdd){
        balance += moneyToAdd;
        listOfBalanceDeposits.add(new BalanceDeposit(moneyToAdd));
    }

    public void calculatePriceOfMusicToBuy(){}
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
