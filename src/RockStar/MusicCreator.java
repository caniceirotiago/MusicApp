package src.RockStar;

import java.util.ArrayList;

public class MusicCreator extends User{
    private int pin;

    public MusicCreator(String name, String username, String password, String email, int pin) {
        super(username, password, name, email);
        this.pin = pin;
    }

    @Override
    public void newCollection(String name) {
        //Creating an empty album
        allCollections.add(new Album("random album name",  this));
    }
    public void newCollection(ArrayList<Music> listMusic) {

        //

    }

    @Override
    public ArrayList<MusicCollection> seeAllCollection() {return new ArrayList<>();}

    //poderia mudar o metodo create music para retornar para este metodo um objeto tipo musica?
    @Override
    public void addMusicToCollection(Music music, MusicCollection musicCollection) {
        //sera que isto assim funciona?
        allMusic.add(music);
    }

    public void editMusic(Music music, MusicCollection musicCollection){
        //music.setName();
        //music.setPrice();
    };

    //esta funcao esta bem?
    public void createMusic(Music music, RockstarIncManager.GENRE genre, double price){
        new Music("artist", genre,this, price);
        //adiciona logo a musica à all music?
        allMusic.add(music);
    }
    public void seeStatistics(){};
}
