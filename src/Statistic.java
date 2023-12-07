package src;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Statistic extends RockstarIncManager {
    //isto pode ser mudado, é so codigo preliminar
    private LocalDateTime newStats; //cada vez que se fizer uma analise de estatisticas do site, ele aponta a data
    private ArrayList <Statistic> dadosEstatisticos;

    public Statistic(LocalDateTime newStats) {
        this.newStats = newStats;
    }


    public void numberOfUsers(){
    }
    public void totalMusicsByGenre(RockstarIncManager.GENRE genre){
        //pesquisar musicas por genero
    };
    public void totalMusics(){
        //pesquisa e retorna o numero total de musicas no sistema
    }
}
