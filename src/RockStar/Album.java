package src.RockStar;

import java.util.ArrayList;

public class Album extends MusicCollection {
    private MusicCreator mainCreator;
    private ArrayList<MusicCreator> otherCreators;

    //construtor de album apenas com 1 criador


    public Album(String name, MusicCreator mainCreator) {
        super(name);
        this.mainCreator = mainCreator;
    }

    public Album(String name, ArrayList<Music> musicList, MusicCreator mainCreator) {
        super(name, musicList);
        this.mainCreator = mainCreator;
    }

    @Override
    public void addMusicToCollection() {

    }

    @Override
    public void removeMusicFromCollection() {

    }
}

