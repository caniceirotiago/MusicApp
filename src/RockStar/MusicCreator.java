package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class MusicCreator extends User implements Serializable {
    private String pin;
    private double totalValueSales;
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
     *
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
     *
     * @param music
     */
    public void newMusicToAllMusicCollection(Music music){
        allMusic.add(music);
    }

    /**
     *
     * @param music
     */
    public void addCreatedMusic(Music music){
        allMusic.add(music); // apagare este metodo fase final
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
