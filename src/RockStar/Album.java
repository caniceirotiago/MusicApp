/**
 * Descrição geral da classe e suas finalidades
 * isto guarda-se no main
 * @autor Tiago Caniceiro && Pedro Monteiro
 * versão 1.0
 */
package src.RockStar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *Implementa serializable para poder ser guardado num ficheiro de objetos
 */
public class Album extends MusicCollection implements Serializable {
    private MusicCreator mainCreator;
    private Genre.GENRE mainGenre;
    public Album(){}

    /**
     *Construtor classe
     * @param name
     * @param mainCreator
     */
    public Album(String name, MusicCreator mainCreator) {
        //Creation of empty album
        super(name);
        this.mainCreator = mainCreator;
    }

    /**
     *Construtor classe
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

    public void addMusicToCollection(Music music) {
        musicList.add(music);
        calculateMainGenre();
    }
    public void removeMusicFromCollection(Music music) {
        musicList.remove(music);
        calculateMainGenre();
    }

    /**
     *Método para calcular o genero principal no album
     */
    public void calculateMainGenre(){
        //primeiro passo é associar o género das musicas encontradas no album a um valor int atraves de um hashmap
        HashMap <Genre.GENRE, Integer> genreFrequency = new HashMap<>();
        //para cada musica encontrada na lista da musica
        //adiciona 1 valor à entrada correspondente cada vez que encontra uma musica desse genero
        for (Music mc : musicList){
            genreFrequency.put(mc.getGenre(), genreFrequency.getOrDefault(mc.getGenre(),0)+1);
        }
        //max frequency é utilizado para definir qual o genero que é encontrado com mais frequencia (contador)
        int maxFreq = 0;
        //a "ciclar" pelo hashmap de modo a encontrar o genero com a frequencia maxima
        for (Map.Entry<Genre.GENRE,Integer> entry : genreFrequency.entrySet()){
            if (entry.getValue() > maxFreq){
                maxFreq = entry.getValue();
            }
        }
        //adiciona à lista os generos principais no caso de haver mais do que um
        ArrayList <Genre.GENRE> genreList = new ArrayList<>();
        for (Map.Entry<Genre.GENRE,Integer> entry : genreFrequency.entrySet()){
            if (entry.getValue() == maxFreq){
                genreList.add(entry.getKey());
            }
        }
        //define o genero principal nesse album
        if (genreList.size() == 1){
            mainGenre = genreList.get(0);
        } else mainGenre = null;
    }
}


