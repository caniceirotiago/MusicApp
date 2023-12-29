/**
 * Descrição geral da classe e suas finalidades
 * <p>
 * @autor Tiago Caniceiro && Pedro Monteiro
 * versão 1.0
 */
package src.RockStar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *      Nome da classe
 * </p>
 * <p>
 *     Implementa serializable para poder ser guargado num ficheiro de objetos
 * </p>
 *
 */
public class Album extends MusicCollection implements Serializable {

    /**
     * O criador de musica
     * */
    private MusicCreator mainCreator;

    /**
     * O genero que se associa ao album
     * */
    private Genre.GENRE mainGenre;

    /**
     * Construtor vazio da classe
     * */
    public Album(){}

    /**
     *
     * @param name
     * @param mainCreator
     */
    public Album(String name, MusicCreator mainCreator) {
        //Creation of empty album
        super(name);
        this.mainCreator = mainCreator;
    }

    /**
     *
     * @param name
     * @param musicCreator
     * @param musicList
     */
    public Album(String name, MusicCreator musicCreator, ArrayList<Music> musicList) {
        //Creation of album with musics
        super(name, musicList);
        this.mainCreator = musicCreator;
    }
    public MusicCreator getCreator() {
        return mainCreator;
    }
    public Genre.GENRE getMainGenre() {
        return mainGenre;
    }

    /**
     *
     * @param music
     */
    public void addMusicToCollection(Music music) {
        musicList.add(music);
        calculateMainGenre();
    }

    /**
     *
     * @param music
     */
    public void removeMusicFromCollection(Music music) {
        musicList.remove(music);
        calculateMainGenre();
    }

    /**
     *
     */
    public void calculateMainGenre(){
        HashMap <Genre.GENRE, Integer> genreFrequency = new HashMap<>();
        for (Music mc : musicList){
            genreFrequency.put(mc.getGenre(), genreFrequency.getOrDefault(mc.getGenre(),0)+1);
        }
        int maxFreq = 0;
        for (Map.Entry<Genre.GENRE,Integer> entry : genreFrequency.entrySet()){
            if (entry.getValue() > maxFreq){
                maxFreq = entry.getValue();
            }
        }
        ArrayList <Genre.GENRE> genreList = new ArrayList<>();
        for (Map.Entry<Genre.GENRE,Integer> entry : genreFrequency.entrySet()){
            if (entry.getValue() == maxFreq){
                genreList.add(entry.getKey());
            }
        }
        if (genreList.size() == 1){
            mainGenre = genreList.get(0);
        } else mainGenre = null;
    }
}


