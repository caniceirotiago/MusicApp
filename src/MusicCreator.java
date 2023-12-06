package src;

import java.util.ArrayList;

public class MusicCreator extends User{
    private int pin;

    public MusicCreator(String username, String password, String name, String email, int pin) {
        super(username, password, name, email);
        this.pin = pin;
    }

    @Override
    public void newCollection(String name) {

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
