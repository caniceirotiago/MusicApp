package src;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class MusicCollection {
    protected String name;
    protected ArrayList<Music> musicList;
    protected LocalDateTime creationDate;

    public MusicCollection(String name) {
        this.name = name;
        this.musicList = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
    }
    public MusicCollection(String name, ArrayList<Music> musicList) {
        this.name = name;
        this.musicList = musicList;
        this.creationDate = LocalDateTime.now();
    }
    public abstract void addMusicToCollection();
    public abstract void removeMusicFromCollection();
}
