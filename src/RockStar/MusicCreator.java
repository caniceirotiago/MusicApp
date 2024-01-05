package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe MusicCreator que herda parametros e funcionalidades da classe abstrata User.
 * Tem como parametros o pin associado no registo e a receita total da venda das suas musicas.
 */
public class MusicCreator extends User implements Serializable {
    private String pin;
    private double totalValueSales;

    /**
     * Construtor da classe do criador de musica.
     * @param name Nome do criador
     * @param username Username do criador
     * @param password Password do criador
     * @param email email do criador
     * @param pin pin associado a este criador, fornecido aquando registo
     */
    public MusicCreator(String name, String username, String password, String email, String pin) {
        super(name, username, password, email);
        this.pin = pin;
    }
    public String getPin() {
        return pin;
    }

    /**
     * Método para a criação de um album de musicas vazio.
     * @param name nome associado ao album.
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
    @Override
    public void addMusicToCollection(Music music, MusicCollection album) {
        if(allCollections.contains(album)){
            album.addMusicToCollection(music);
            music.setAssociatedAlbum((Album)album);
        }
    }
    public void newMusicToAllMusicCollection(Music music){
        allMusic.add(music);
    }
    public void removeMusicFromCollection(Music music, MusicCollection collection){
        if(allCollections.contains(collection)){
            collection.removeMusicFromCollection(music);
        }
    }
    public void removeMusicCollection(MusicCollection collection){
        for(Music m : collection.getMusicList()){
            m.setAssociatedAlbum(null);
        }
        allCollections.remove(collection);
    }
    public void addRevenueFromMusicSale (double valueToAdd){
        totalValueSales += valueToAdd;
    }
    public double getTotalValueSales() {
        return totalValueSales;
    }
}
