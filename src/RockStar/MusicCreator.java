package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicCreator extends User implements Serializable {
    private String pin;

    public String getPin() {
        return pin;
    }

    public MusicCreator(String name, String username, String password, String email, String pin) {
        super(name, username, password, email);
        this.pin = pin;
    }

    @Override
    public void newCollection(String name) {
        //Creating an empty album
        allCollections.add(new Album(name,  this));
    }
    public void newCollection(ArrayList<Music> listMusic) {

    }

    @Override
    public ArrayList<MusicCollection> seeAllCollection() {return new ArrayList<>();}

    //poderia mudar o metodo create music para retornar para este metodo um objeto tipo musica?
    @Override
    public void addMusicToCollection(Music music, MusicCollection album) {
        music.setAssociatedAlbum((Album) album);
    }
    public void newMusicToAllCollection(Music music){

    }
    public void removeMusicFromCollection(Music music, MusicCollection collection){

    };
    public void removeMusicCollection(MusicCollection collection){

    };
    public void editMusicTitle(Music music, String newName){
        music.setName(newName);
    };
    public void editMusicPrice (Music music, double newPrice){

        music.setPrice(newPrice);
    }

    public void addCreatedMusic(Music music){
        allMusic.add(music);
    }
    public void seeStatistics(){};
    public void setMusicToInactive(Music music){
        music.setActive(false);
    }
}
