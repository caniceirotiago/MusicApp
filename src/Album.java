package src;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Album extends MusicCollection {
    private MusicCreator mainCreator;
    private ArrayList<MusicCreator> otherCreators;

    //construtor de album apenas com 1 criador
    public Album(String name, LocalDateTime creationDate, MusicCreator mainCreator) {
        super(name, creationDate);
        this.mainCreator = mainCreator;
    }

    //contrutor de albuns em conjunto
    public Album(String name, LocalDateTime creationDate, MusicCreator mainCreator, ArrayList<MusicCreator> otherCreators) {
        super(name, creationDate);
        this.mainCreator = mainCreator;
        this.otherCreators = otherCreators;
    }

    @Override
    public void addMusicToCollection() {

    }

    @Override
    public void removeMusicFromCollection() {

    }
}

