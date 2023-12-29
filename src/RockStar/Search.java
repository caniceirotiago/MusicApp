package src.RockStar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class Search implements Serializable {
    private ArrayList<Music> foundMusics;
    private ArrayList<Music> foundMusicsByArtist;
    private ArrayList<MusicCollection> foundMusicCollections;
    public ArrayList<Music> getFoundMusics() {
        return foundMusics;
    }
    public ArrayList<Music> getFoundMusicsByArtist() {
        return foundMusicsByArtist;
    }
    public ArrayList<MusicCollection> getFoundMusicCollections() {
        return foundMusicCollections;
    }
    public Search(){}

    /**
     *
     * @param foundMusics
     */
    public Search(ArrayList<Music> foundMusics) {
        this.foundMusics = foundMusics;
    }

    /**
     *
     * @param foundMusics
     * @param foundMusicsByArtist
     * @param foundMusicCollections
     */
    public Search(ArrayList<Music> foundMusics, ArrayList<Music> foundMusicsByArtist, ArrayList<MusicCollection> foundMusicCollections) {
        this.foundMusics = foundMusics;
        this.foundMusicsByArtist = foundMusicsByArtist;
        this.foundMusicCollections = foundMusicCollections;
    }
}
