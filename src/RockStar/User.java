package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable {
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected ArrayList<Music> allMusic;
    protected ArrayList<MusicCollection> allCollections;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public User(String name, String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.allMusic = new ArrayList<>();
        this.allCollections = new ArrayList<>();
    }
    public abstract void newCollection(String name);
    public abstract void newCollection(ArrayList<Music> listMusic);
    public abstract ArrayList<MusicCollection> seeAllCollection();
    public abstract void addMusicToCollection(Music music, MusicCollection musicCollection);
}
