package src.RockStar;

import java.util.ArrayList;

public class Search {
    private ArrayList<Music> foundMusics;
    private ArrayList<MusicCollection> foundMusicCollections;
    private ArrayList<MusicCreator> foundMusicCreators;

    public Search(ArrayList<Music> foundMusics, ArrayList<MusicCollection> foundMusicCollections, ArrayList<MusicCreator> foundMusicCreators) {
        this.foundMusics = foundMusics;
        this.foundMusicCollections = foundMusicCollections;
        this.foundMusicCreators = foundMusicCreators;
    }
    public Search(ArrayList<Music> foundMusics, ArrayList<MusicCollection> foundMusicCollections) {
        this.foundMusics = foundMusics;
        this.foundMusicCollections = foundMusicCollections;
    }
}
