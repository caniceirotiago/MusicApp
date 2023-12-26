package src.RockStar;

import java.io.Serializable;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        calculateMainGenre();
    }
    public void removeMusicFromCollection() {

    }

    @Override
    public String toString() {
        return  super.name;
    }

    public void calculateMainGenre(){
        //este metodo calcula o genero que aparece com mais frequencia aquando a criacao de um album
        //dependendo do genero que esta nas musicas que forem incluidas no album
        HashMap <RockstarIncManager.GENRE, Integer> genreFrequency = new HashMap<>();
        for (Music mc : musicList){
            genreFrequency.put(mc.getGenre(), genreFrequency.get(mc.getGenre())+1);
        }
        int maxFreq = 0;
        for (Map.Entry<RockstarIncManager.GENRE,Integer> entry : genreFrequency.entrySet()){
            if (entry.getValue() > maxFreq){
                maxFreq = entry.getValue();
            }
        }
        ArrayList <RockstarIncManager.GENRE> genreList = new ArrayList<>();
        for (Map.Entry<RockstarIncManager.GENRE,Integer> entry : genreFrequency.entrySet()){
            if (entry.getValue() == maxFreq){
                genreList.add(entry.getKey());
            }
        }
        if (genreList.size() == 1){
            mainGenre = genreList.get(0);
        } else mainGenre = null;
        System.out.println(genreList.get(0));
    }
}


