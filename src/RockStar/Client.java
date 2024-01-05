package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe cliente que implementa a classe abstrata User
 * Para alem de integrar as funcionalidades da classe User, como ter uma coleção de musica, tem lista de compras,
 * uma carteira em que se consegue colocar dinheiro e a possibilidade de se comprar e adquirir ficheiros de musica.
 *
 */
public class Client extends User implements Serializable {
    private double balance;
    ArrayList<MusicAquisition> listOfAcquisitions;
    ArrayList<BalanceDeposit> listOfBalanceDeposits;
    ArrayList<Music> listOfMusicsToBuy;

    /**
     * Construtor do utilizador Cliente
     * @param name nome do cliente
     * @param username Username do cliente
     * @param password password do cliente
     * @param email email do cliente
     * @param balance saldo do cliente
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
     *Fazer nova coleção
     * @param name
     */
    public void newCollection(String name){
        //Creation of Empty playlist
        Playlist newPlaylist = new Playlist(name, this);
        allCollections.add(newPlaylist);
    }

    /**
     *Nova coleção aleatoria de musica?
     * método que auxilia o construtor de musica aleatoria para criar nova playlist random
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
     *Método para adicionar musica a uma playlist criada
     * @param music
     * @param musicCollection
     */
    public void addMusicToCollection(Music music, MusicCollection musicCollection){
        if(allCollections.contains(musicCollection) && music.isActive()) musicCollection.addMusicToCollection(music);
    };

    /**
     *adicionar musica à lista geral (library que contem a musica geral do cliente)
     * @param music
     */
    public void newMusicToAllMusicCollection(Music music){
        allMusic.add(music);
    }

    /**
     *Remover musica de uma playlist
     * @param music
     * @param collection
     */
    public void removeMusicFromCollection(Music music, MusicCollection collection){
        collection.removeMusicFromCollection(music);
    };

    /**
     *remover coleção/playlist da coleção do utilizador
     * @param collection
     */
    public void removeMusicCollection(MusicCollection collection){
        allCollections.remove(collection);
    };

    /**
     *adicionar dinheiro à carteira do cliente
     * @param moneyToAdd
     */
    public void addMoney(double moneyToAdd){
        balance += moneyToAdd;
        listOfBalanceDeposits.add(new BalanceDeposit(moneyToAdd));
    }

    /**
     *método que permite ao cliente comprar musicas consoante o saldo que tenha no carrinho
     *Se não tiver saldo não permite a compra de musicas
     *@param musics
     *@return
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
