package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

public class Search implements Serializable {
    private ArrayList<Music> foundMusics;
    private ArrayList<Music> foundMusicsByArtist;
    private ArrayList<MusicCollection> foundMusicCollections;
    private ArrayList<MusicCreator> foundMusicCreators;

    public ArrayList<Music> getFoundMusics() {
        return foundMusics;
    }

    public ArrayList<Music> getFoundMusicsByArtist() {
        return foundMusicsByArtist;
    }

    public ArrayList<MusicCollection> getFoundMusicCollections() {
        return foundMusicCollections;
    }

    public ArrayList<MusicCreator> getFoundMusicCreators() {
        return foundMusicCreators;
    }

    public Search(ArrayList<Music> foundMusics, ArrayList<Music> foundMusicsByArtist, ArrayList<MusicCollection> foundMusicCollections, ArrayList<MusicCreator> foundMusicCreators) {
        this.foundMusics = foundMusics;
        this.foundMusicsByArtist = foundMusicsByArtist;
        this.foundMusicCollections = foundMusicCollections;
        this.foundMusicCreators = foundMusicCreators;
    }
    public Search(ArrayList<Music> foundMusics, ArrayList<MusicCollection> foundMusicCollections) {
        this.foundMusics = foundMusics;
        this.foundMusicCollections = foundMusicCollections;
    }
    public Search(){}
}
