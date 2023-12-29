package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class Playlist extends MusicCollection implements Serializable {
    private Boolean isPublic;
    private Client clientCreator;
    public Playlist() {}

    /**
     *
     * @param name
     * @param clientCreator
     */
    public Playlist(String name, Client clientCreator) {
        //Creation of empty playlist
        super(name);
        this.isPublic = true;
        this.clientCreator = clientCreator;
    }

    /**
     *
     * @param name
     * @param clientCreator
     * @param musicList
     */
    public Playlist(String name, Client clientCreator, ArrayList<Music> musicList) {
        //Creation of random playlist or temporary Gui Playlist
        super(name, musicList);
        this.isPublic = true;
        this.clientCreator = clientCreator;
    }
    public Client getClientCreator() {
        return clientCreator;
    }

    public Boolean getPublicState() {
        return isPublic;
    }

    public void setPublicState(Boolean aPublic) {
        isPublic = aPublic;
    }
    public void addMusicToCollection(Music music) {
        musicList.add(music);
    }
    public void removeMusicFromCollection(Music music) {
        musicList.remove(music);
    }
}
