package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

public class Album extends MusicCollection implements Serializable {
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
    public Album(){
    }
    public Album(String name, MusicCreator musicCreator, ArrayList<Music> musicList) {
        //Creation of random playlist or temporary Gui Playlist
        super(name, musicList);
        this.mainCreator = musicCreator;
    }

    public MusicCreator getMainCreator() {
        return mainCreator;
    }

    public void addMusicToCollection(Music music) {
        musicList.add(music);
    }
    public void removeMusicFromCollection() {

    }

    @Override
    public String toString() {
        return  super.name;
    }
}

