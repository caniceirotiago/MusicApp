package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Album extends MusicCollection implements Serializable {
    private MusicCreator mainCreator;
    private ArrayList<MusicCreator> otherCreators;
    private RockstarIncManager.GENRE mainGenre;


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

    public void calculateMainGenre(){
        HashMap <RockstarIncManager.GENRE, Integer> genreFrequency = new HashMap<>();
        for (Music mc : musicList){
            genreFrequency.put(mc.getGenre(), genreFrequency.get(mc.getGenre())+1);
        }
    }
}

