package src;

import java.util.ArrayList;

public abstract class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected ArrayList<Music> allMusic;
    protected ArrayList<MusicCollection> allCollections;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.allMusic = new ArrayList<>();
        this.allCollections = new ArrayList<>();
    }
    public abstract void newCollection(String name);
    public abstract ArrayList<MusicCollection> seeAllCollection();
    public abstract void addMusicToCollection(Music music, MusicCollection musicCollection);
}
