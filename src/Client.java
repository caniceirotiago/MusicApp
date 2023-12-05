package src;

import java.util.ArrayList;

public class Client extends User{
    private double balance = 0;
    ArrayList<MusicAquisition> listOfAquisitions;
    ArrayList<BalanceDeposit> listOfBalanceDeposits;
    ArrayList<Music> listOfMusicsToBuy;

    public Client(String username, String password, String name, String email, double balance) {
        super(username, password, name, email);
        this.balance = balance;
        this.listOfAquisitions = new ArrayList<>();
        this.listOfBalanceDeposits = new ArrayList<>();
        this.listOfMusicsToBuy = new ArrayList<>();
    }

    public void newCollection(String name){

    };
    public ArrayList<MusicCollection> seeAllCollection(){
        return new ArrayList<>();
    };
    public void addMusicToCollection(Music music, MusicCollection musicCollection){

    };
    public void addMoney(){}
    public void classificateMusic(){}
    public void newRandomPlaylist(){} //talvez seja melhor ir para a classe principal
    public void addMusicToAllMusicUserCollection(){}
    public void calculatePriceOfMusicToBuy(){}
    public void validationOfAquisition(){}
}
