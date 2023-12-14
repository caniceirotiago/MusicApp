package src.RockStar;

import java.util.ArrayList;

public class MusicCreator extends User{
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
        allCollections.add(new Album("random album name",  this));
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

    public void editMusic(Music music, MusicCollection musicCollection){
        //music.setName();
        //music.setPrice();
    };

    public void addCreatedMusic(Music music){
        allMusic.add(music);
    }
    public void seeStatistics(){};
}
