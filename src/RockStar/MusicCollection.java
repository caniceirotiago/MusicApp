package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *Classe abstrata das coleções de musica
 * serve de base para as playlists do cliente e o album do criador de musica
 */
public abstract class MusicCollection implements Serializable {
    protected String name;
    protected ArrayList<Music> musicList;
    protected LocalDateTime creationDate;
    public MusicCollection() {}

    /**
     * construtor de uma coleção de musicas vazia.
     * @param name Define o nome da coleção.
     */
    public MusicCollection(String name) {
        this.name = name;
        this.musicList = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Construtor de uma coleção de musica com nome e uma lista de musicas associadas.
     * Utilizado na criação de playlists ou albums
     * @param name Nome da playlist.
     * @param musicList Lista de musicas a inserir na coleção
     */
    public MusicCollection(String name, ArrayList<Music> musicList) {
        this.name = name;
        this.musicList = musicList;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * método abstrato para adicionar musica à coleção
     * @param music
     */
    public abstract void addMusicToCollection(Music music);
    public abstract void removeMusicFromCollection(Music music);
    public String getName() {
        return name;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }
    @Override
    public String toString() {
        return name;
    }
}
