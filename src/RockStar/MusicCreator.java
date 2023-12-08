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
        //Creating an empty
    }
    public void newCollection(ArrayList<Music> listMusic) {
        //Creating random or other
    }

    @Override
    public ArrayList<MusicCollection> seeAllCollection() {
        return null;
    }

    @Override
    public void addMusicToCollection(Music music, MusicCollection musicCollection) {

    }
    public void createNewMusic(){};
    public void editMusic(){};
    public void seeStatistics(){};
}
