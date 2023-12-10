package src.RockStar;

import java.util.ArrayList;

public class Playlist extends MusicCollection{
    private Boolean isPublic;
    private Client clientCreator;

    public Boolean getPublic() {
        return isPublic;
    }

    public Playlist(String name, Client clientCreator) {
        //Creation of empty playlist
        super(name);
        this.isPublic = true;
        this.clientCreator = clientCreator;
    }
    public Playlist(String name, Client clientCreator, ArrayList<Music> musicList) {
        //Creation of random playlist
        super(name, musicList);
        this.isPublic = true;
        this.clientCreator = clientCreator;
    }

    @Override
    public void addMusicToCollection() {

    }

    @Override
    public void removeMusicFromCollection() {
    }
}
