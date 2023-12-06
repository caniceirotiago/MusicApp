package src;

import java.time.LocalDateTime;

public class Playlist extends MusicCollection{
    private Boolean isMusicCreator;
    private Client clientCreator;

    public Playlist(String name, LocalDateTime creationDate, Boolean isMusicCreator, Client clientCreator) {
        super(name, creationDate);
        this.isMusicCreator = isMusicCreator;
        this.clientCreator = clientCreator;
    }

    @Override
    public void addMusicToCollection() {

    }

    @Override
    public void removeMusicFromCollection() {

    }
}
