package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 */
public abstract class MusicCollection implements Serializable {
    protected String name;
    protected ArrayList<Music> musicList;
    protected LocalDateTime creationDate;
    public MusicCollection() {}

    /**
     *
     * @param name
     */
    public MusicCollection(String name) {
        this.name = name;
        this.musicList = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
    }

    /**
     *
     * @param name
     * @param musicList
     */
    public MusicCollection(String name, ArrayList<Music> musicList) {
        this.name = name;
        this.musicList = musicList;
        this.creationDate = LocalDateTime.now();
    }

    /**
     *
     * @param music
     */
    public abstract void addMusicToCollection(Music music);

    /**
     *
     * @param music
     */
    public abstract void removeMusicFromCollection(Music music);
    public String getName() {
        return name;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }
    @Override
    public String toString() {
        return name;
    }
}
