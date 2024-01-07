/**
 * @Authors Tiago Caniceiro & Pedro Monteiro
 * @Version 1.0
 */
package src.RockStar.Model;
import src.RockStar.Genre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe para criação de coleções de musica do tipo "Album" associadas ao utilizador Music Creator
 */
public class Album extends MusicCollection implements Serializable {
    private MusicCreator mainCreator;
    private Genre.GENRE mainGenre;
    public Album(){}

    /**
     * Construtor de um album vazio
     * @param name nome do album
     * @param mainCreator nome do criador de musica associado
     */
    public Album(String name, MusicCreator mainCreator) {
        //Creation of empty album
        super(name);
        this.mainCreator = mainCreator;
    }

    /**
     * Construtor de um album com uma lista de musicas já integradas
     * @param name Nome do album
     * @param musicCreator nome do criador de musica
     * @param musicList lista de musicas a juntar ao album
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
     * Método para calcular o genero principal no album
     * Utiliza um hashmap para associar um género especifico encontrado nas musicas do album e aumentar um valor
     * correspondente a esse genero encontrado.
     * O género cujo valor for mais alto é considerado o género do album.
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


