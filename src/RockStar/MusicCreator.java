package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *Classe de utilizadores criadores de musica
 * extende a classe abstrata utilizador
 */
public class MusicCreator extends User implements Serializable {
    private String pin;
    private double totalValueSales;

    /**
     * Construtor da classe do criador de musica
     * utiliza parametros da classe user da qual herd
     * @param name
     * @param username
     * @param password
     * @param email
     * @param pin
     */
    public MusicCreator(String name, String username, String password, String email, String pin) {
        super(name, username, password, email);
        this.pin = pin;
    }
    public String getPin() {
        return pin;
    }

    /**
     *
     * @param name
     */
    @Override
    public void newCollection(String name) {
        //Creating an empty album
        allCollections.add(new Album(name,  this));
    }

    /**
     *
     * @param listMusic
     */
    public void newCollection(ArrayList<Music> listMusic) {}

    /**
     *adicionar musica ao album do criador
     * @param music
     * @param album
     */
    @Override
    public void addMusicToCollection(Music music, MusicCollection album) {
        if(allCollections.contains(album)){
            album.addMusicToCollection(music);
            music.setAssociatedAlbum((Album)album);
        }
    }

    /**
     *adicionar musica à biiblioteca geral
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
        if(allCollections.contains(collection)){
            collection.removeMusicFromCollection(music);
        }
    }

    /**
     *
     * @param collection
     */
    public void removeMusicCollection(MusicCollection collection){
        allCollections.remove(collection);
    }

    /**
     *
     * @param valueToAdd
     */
    public void addRevenueFromMusicSale (double valueToAdd){
        totalValueSales += valueToAdd;
    }

    /**
     *
     * @return
     */
    public double getTotalValueSales() {
        return totalValueSales;
    }
}
